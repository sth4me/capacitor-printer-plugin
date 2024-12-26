package com.sth4me.capacitor.printer;

import android.util.Log;

public class PrinterPlugin {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }
}
