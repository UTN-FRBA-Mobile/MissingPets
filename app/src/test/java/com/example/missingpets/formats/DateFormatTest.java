package com.example.missingpets.formats;

import static org.junit.Assert.*;

import org.junit.Test;

public class DateFormatTest {

    @Test
    public void ddmmyyyToyyyymmdd() {
        String datestring = "24-11-2021";
        String expectedResult = "2021-11-24";
        String result = DateFormat.ddmmyyyyToyyyymmdd(datestring);

        assertEquals(result, expectedResult);
    }

    @Test
    public void yyyymmddToddmmyyyy() {
        String datestring = "2021-11-24";
        String expectedResult = "24-11-2021";
        String result = DateFormat.yyyymmddToddmmyyyy(datestring);

        assertEquals(result, expectedResult);
    }
}