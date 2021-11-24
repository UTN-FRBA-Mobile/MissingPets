package com.example.missingpets.formats;

import static org.junit.Assert.*;

import org.junit.Test;

public class DateFormatTest {

    @Test
    public void yyyymmddToddmmyyy() {
        String datestring = "24-11-2021";
        String expectedResult = "2021-11-24";
        String result = DateFormat.ddmmyyyToyyyymmdd(datestring);

        assertEquals(result, expectedResult);
    }

    @Test
    public void ddmmyyyToyyyymmdd() {
        String datestring = "2021-11-24";
        String expectedResult = "24-11-2021";
        String result = DateFormat.yyyymmddToddmmyyy(datestring);

        assertEquals(result, expectedResult);
    }
}