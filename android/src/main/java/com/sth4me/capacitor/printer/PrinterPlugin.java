package com.sth4me.capacitor.printer;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import android.util.Base64;

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