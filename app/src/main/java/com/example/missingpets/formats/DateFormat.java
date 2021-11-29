package com.example.missingpets.formats;



public class DateFormat {

    public static String yyyymmddToddmmyyyy(String datestring){ //ej 2021-11-15   21-11-15
        if(datestring.substring(5, 6).equals("-") || datestring.substring(3, 4).equals("-") ) {
            String delimit = "-";
            String[] date = datestring.split(delimit);
            return date[2]+delimit+date[1]+delimit+date[0];
        } else {
            return datestring;
        }
    }

    public static String ddmmyyyyToyyyymmdd(String datestring) {  //ej 2-11-2021   02-11-2021
        if(datestring.substring(1, 2).equals("-") || datestring.substring(2, 3).equals("-") ) {
            String delimit = "-";
            String[] date = datestring.split(delimit);
            return date[2]+delimit+date[1]+delimit+date[0];
        } else {
            return datestring;
        }
    }

  /*  public static String NovamasyyyymmddToddmmyyyy(String datestring) {
        if(datestring.substring(4, 5).equals("-")) {
            return datestring.substring(8, 10) + "-" + datestring.substring(5, 7) + "-" + datestring.substring(0, 4);
        } else {
            return datestring;
        }
    }
    public static String NovamassddmmyyyyToyyyymmdd(String datestring) {
        if(datestring.substring(2, 3).equals("-")) {
            return datestring.substring(6, 10) + "-" + datestring.substring(3, 5) + "-" + datestring.substring(0, 2);
        } else {
            return datestring;
        }
    } */
}
