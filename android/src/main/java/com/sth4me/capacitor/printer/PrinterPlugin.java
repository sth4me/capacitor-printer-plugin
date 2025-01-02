package com.sth4me.capacitor.printer;


import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import org.json.JSONArray;


@CapacitorPlugin(name = "Printer")
public class PrinterPlugin extends Plugin {

    private USBPrinter implementation;


    @Override
    public void load() {
        implementation = new USBPrinter(getContext());
    }

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");
        call.resolve(new JSObject().put("value", value));
    }

    @PluginMethod
    public void getPrinterList(PluginCall call) {
        JSObject jsObject = new JSObject();
        JSONArray jsonArray = new JSONArray(implementation.getPrinterList());
        jsObject.put("printerList", jsonArray);
        call.resolve(jsObject);
    }

    @PluginMethod
    public void connectToPrinter(PluginCall call) {
        Integer productId = call.getInt("productId");
        if (productId == null ) {
            call.reject("Must provide a product id");
            return;
        }

        try {
            JSObject jsObject = new JSObject();
            jsObject.put("connected", implementation.connectToPrinter(productId));
            call.resolve(jsObject);
        } catch (Exception e) {
            call.reject(e.getMessage());
        }
    }

    @PluginMethod
    public void getEncoding(PluginCall call) {
        try {
            JSObject jsObject = new JSObject();
            jsObject.put("encoding", implementation.getEncoding());
            call.resolve(jsObject);
        } catch (Exception e) {
            call.reject(e.getMessage());
        }
    }

    @PluginMethod
    public void print(PluginCall call) {
        String printObject = call.getString("printObject");
        if (printObject == null || printObject.isEmpty()) {
            call.reject("Must provide any content");
            return;
        }
        Integer lineFeed = call.getInt("lineFeed", 0);
        try {
            assert (lineFeed != null);
            implementation.print(printObject, lineFeed);
            call.resolve();
        } catch (Exception e) {
            call.reject(e.getMessage());
        }
    }

}