package com.sth4me.capacitor.printer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class USBPrinter {
    private final Context context;
    private final String actionString;
    private final UsbManager manager;
    private final List<UsbDevice> deviceList;
    private UsbInterface usbInterface;
    private UsbEndpoint usbEndpoint;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private UsbDeviceConnection connection;


    public USBPrinter(Context context) {
        this.context = context;
        this.actionString = this.context.getPackageName() + ".USB_PERMISSION";
        this.manager =  (UsbManager) this.context.getSystemService(Context.USB_SERVICE);
        this.deviceList = new ArrayList<>();
    }

    public List<Map> getPrinterList() {
        List<Map> printerList = new ArrayList<>();
        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this.context, 0, new
                Intent(actionString), PendingIntent.FLAG_IMMUTABLE);

        HashMap<String, UsbDevice> deviceList = this.manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

        while (deviceIterator.hasNext()) {
            UsbDevice usbDevice = deviceIterator.next();

            if(isAPrinter(usbDevice)) {
                manager.requestPermission(usbDevice, mPermissionIntent);
                Map printerInfo = new HashMap();
                printerInfo.put("productId", usbDevice.getProductId());
                printerInfo.put("productName", usbDevice.getProductName());
                printerInfo.put("connected", false);
                printerList.add(printerInfo);
                this.deviceList.add(usbDevice);
            }
        }

        return printerList;
    }

    private boolean isAPrinter(UsbDevice usbDevice) {
        for(int i =0; i < usbDevice.getInterfaceCount(); i += 1) {
            UsbInterface usbInterface = usbDevice.getInterface(i);
            for (int j = 0; j < usbInterface.getEndpointCount(); j++) {
                UsbEndpoint usbEndpoint = usbInterface.getEndpoint(i);
                return UsbConstants.USB_ENDPOINT_XFER_BULK == usbEndpoint.getType() && UsbConstants.USB_DIR_OUT == usbEndpoint.getDirection();
            }
        }
        return false;
    }

    public boolean connectToPrinter(int productId) throws Exception {
        UsbDevice selectedDevice = null;
        Log.d("USBPrinter", "Attempting to connect to printer with product ID: " + productId);
        
        for(UsbDevice device: this.deviceList) {
            if(productId == device.getProductId()) {
                selectedDevice = device;
                Log.d("USBPrinter", "Found matching device: " + device.getProductName());
                setUsbInterfaceAndEndpoint(selectedDevice);
                break;
            }
        }

        if(selectedDevice == null) {
            Log.e("USBPrinter", "Device with product ID " + productId + " not found");
            throw new Exception("Device with product id " + productId + " is not found.");
        }

        try {
            this.connection = this.manager.openDevice(selectedDevice);
            boolean connected = (this.connection != null);
            Log.d("USBPrinter", "Connection " + (connected ? "successful" : "failed"));
            return connected;
        } catch(Exception e) {
            Log.e("USBPrinter", "Failed to connect: " + e.getMessage());
            this.connection = null;
            throw new Exception("Failed to establish connection to device " + productId + " due to " + e.getMessage());
        }
    }

    private void setUsbInterfaceAndEndpoint(UsbDevice usbDevice) {
        Log.d("USBPrinter", "Setting up USB interface and endpoint");
        for(int i = 0; i < usbDevice.getInterfaceCount(); i += 1) {
            UsbInterface usbInterface = usbDevice.getInterface(i);
            for (int j = 0; j < usbInterface.getEndpointCount(); j++) {
                UsbEndpoint usbEndpoint = usbInterface.getEndpoint(i);
                if(UsbConstants.USB_ENDPOINT_XFER_BULK == usbEndpoint.getType() && 
                   UsbConstants.USB_DIR_OUT == usbEndpoint.getDirection()) {
                    Log.d("USBPrinter", "Found compatible interface and endpoint");
                    this.usbInterface = usbInterface;
                    this.usbEndpoint = usbEndpoint;
                    return;
                }
            }
        }
        Log.e("USBPrinter", "No compatible interface and endpoint found");
    }


    public void print(String printObject, int lineFeed) throws Exception {
        if(this.connection == null) {
            throw new Exception("Currently not connected to a device.");
        } else if(this.usbInterface == null) {
            throw new Exception("Usb interface is not properly set.");
        }
        List<USBPrinterLineEntry> printObjectList = this.objectMapper.readValue(printObject, new TypeReference<>() {});

        this.connection.claimInterface(this.usbInterface, true);
        byte[] LN = USBPrinterConstant.COMMAND_LIST.get(USBPrinterConstant.LN);
        byte[] RESET = USBPrinterConstant.COMMAND_LIST.get(USBPrinterConstant.RESET);
        byte[] CHARSET_CHINA = USBPrinterConstant.COMMAND_LIST.get("CHARSET_CHINA");

        for(USBPrinterLineEntry lineEntry: printObjectList) {
            connection.bulkTransfer(usbEndpoint, RESET, RESET.length, 10000);
            connection.bulkTransfer(usbEndpoint, CHARSET_CHINA.length , 10000);
            if(lineEntry.getLineStyleList() != null) {
                for(String style: lineEntry.getLineStyleList()) {
                    byte[] styleValue = USBPrinterConstant.STYLE_LIST.get(style);
                    if(styleValue != null) {
                        connection.bulkTransfer(usbEndpoint, styleValue, styleValue.length, 10000);
                    }
                }
            }

            if(lineEntry.getLineText() != null) {
                String printData = lineEntry.getLineText();
                String[] splitData = printData.split("\\n");

                for (String print: splitData) {
                    this.connection.bulkTransfer(this.usbEndpoint, print.getBytes(), print.getBytes().length, 10000);
                    this.connection.bulkTransfer(this.usbEndpoint, LN, LN.length, 10000);
                }
            }

            if(lineEntry.getLineCommandList() != null) {
                for(String command: lineEntry.getLineCommandList()) {
                    byte[] commandValue = USBPrinterConstant.COMMAND_LIST.get(command);
                    if(commandValue != null) {
                        connection.bulkTransfer(usbEndpoint, commandValue, commandValue.length, 10000);
                    }
                }
            }
        }

        // line feed to push the prints beyond the printer cover
        for(int i = 0; i < lineFeed; i+=1) {
            this.connection.bulkTransfer(this.usbEndpoint, LN, LN.length, 10000);
        }

        this.connection.releaseInterface(this.usbInterface);
    }
}