package com.example.missingpets.formats;



public class DateFormat {

    public static String yyyymmddToddmmyyyy(String datestring) {
        if(datestring.substring(4, 5).equals("-")) {
            return datestring.substring(8, 10) + "-" + datestring.substring(5, 7) + "-" + datestring.substring(0, 4);
        } else {
            return datestring;
        }
    }
    public static String ddmmyyyyToyyyymmdd(String datestring) {
        if(datestring.substring(2, 3).equals("-")) {
            return datestring.substring(6, 10) + "-" + datestring.substring(3, 5) + "-" + datestring.substring(0, 2);
        } else {
            return datestring;
        }
    }
}
