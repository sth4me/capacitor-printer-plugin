package com.sth4me.capacitor.printer;

import java.util.List;

public class USBPrinterLineEntry {
    private String lineText;
    private List<String> lineStyleList;
    private List<String> lineCommandList;

    public USBPrinterLineEntry() {}

    public String getLineText() {
        return lineText;
    }

    public void setLineText(String lineText) {
        this.lineText = lineText;
    }

    public List<String> getLineStyleList() {
        return lineStyleList;
    }

    public void setLineStyleList(List<String> lineStyleList) {
        this.lineStyleList = lineStyleList;
    }

    public List<String> getLineCommandList() {
        return lineCommandList;
    }

    public void setLineCommandList(List<String> lineCommandList) {
        this.lineCommandList = lineCommandList;
    }
}