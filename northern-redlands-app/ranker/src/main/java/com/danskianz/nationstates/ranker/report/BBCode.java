package com.danskianz.nationstates.ranker.report;

/**
 * Does BBCode things.
 * 
 * @author Daniel Paul Anzaldo <anye.west@gmail.com>
 */
public class BBCode {
    private static String tag(String tagName, boolean isOpening) {
        return (isOpening) ? "[" + tagName + "]" : "[/" + tagName + "]";
    }
    
    public static String enclose(String tagName, String text) {
        return tag(tagName, true) + text + tag(tagName, false);
    }
    
    public static String table(String text) {
        return enclose("table", text);
    }
    
    public static String tableRow(String text) {
        return enclose("tr", text);
    }
    
    public static String tableData(String text) {
        return enclose("td", text);
    }
    
    public static String nation(String text) {
        return enclose("nation", text);
    }
    
    public static String region(String text) {
        return enclose("region", text);
    }
    
    public static String bold(String text) {
        return enclose("b", text);
    }
}
