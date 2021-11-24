package com.example.missingpets.formats;



public class DateFormat {

    public static String yyyymmddToddmmyyy(String datestring) {
        return datestring.substring(8, 10) + "-" + datestring.substring(5, 7) + "-" + datestring.substring(0, 4);
    }
    public static String ddmmyyyToyyyymmdd(String datestring) {
        return datestring.substring(6, 10) + "-" + datestring.substring(3, 5) + "-" + datestring.substring(0, 2);
    }
}
