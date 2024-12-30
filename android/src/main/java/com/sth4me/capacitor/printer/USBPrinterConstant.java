package com.sth4me.capacitor.printer;

import java.util.HashMap;
import java.util.Map;

public class USBPrinterConstant {

    public static Map<String, byte[]> STYLE_LIST;
    public static Map<String, byte[]> COMMAND_LIST;
    public static String LN = "LINE_BREAK";
    public static String RESET = "RESET";

    static {
        STYLE_LIST = new HashMap<>();
        COMMAND_LIST = new HashMap<>();

        // text horizontal alignment
        STYLE_LIST.put("ALIGN_LEFT", new byte[] {(byte) 27, (byte) 97, (byte) 0});
        STYLE_LIST.put("ALIGN_CENTER", new byte[] {(byte) 27, (byte) 97, (byte) 1});
        STYLE_LIST.put("ALIGN_JUSTIFIED", new byte[] {(byte) 27, (byte) 97, (byte) 1});
        STYLE_LIST.put("ALIGN_RIGHT", new byte[] {(byte) 27, (byte) 97, (byte) 2});

        STYLE_LIST.put("WIDE", new byte[] {(byte) 27, (byte) 33, (byte) 4});
        STYLE_LIST.put("EMPHASIZED", new byte[] {(byte) 27, (byte) 33, (byte) 8});
        STYLE_LIST.put("STRETCHED_WIDTH", new byte[] {(byte) 27, (byte) 33, (byte) 16});
        STYLE_LIST.put("STRETCHED_HEIGHT", new byte[] {(byte) 27, (byte) 33, (byte) 32});
        STYLE_LIST.put("UNDERLINED", new byte[] {(byte) 27, (byte) 33, (byte) 128});

        STYLE_LIST.put("BOLD_FONT",new byte[] {(byte) 27, (byte) 69, (byte) 1});
        STYLE_LIST.put("BOLD_FONT_OFF",new byte[] {(byte) 27, (byte) 69, (byte) 0});
        STYLE_LIST.put("CHARSET_CHINA",new byte[] {(byte) 27, (byte) 82, (byte) 15});

        // reset the styling commands that are already set
        COMMAND_LIST.put("RESET", new byte[] {(byte) 27, (byte) 64});
        COMMAND_LIST.put("PARTIAL_CUT", new byte[] {(byte) 27, (byte) 105});
        COMMAND_LIST.put("FULL_CUT", new byte[] {(byte) 27, (byte) 109});
        COMMAND_LIST.put("LINE_BREAK", new byte[] {(byte) 10});
    }
}