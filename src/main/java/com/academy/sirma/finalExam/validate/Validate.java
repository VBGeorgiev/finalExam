package com.academy.sirma.finalExam.validate;

import com.academy.sirma.finalExam.utility.Constants;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Validate {

    public static boolean isNumber(String num) {
        if(!num.matches("[0-9]*")) {
            return false;
        }

        return true;
    }

    public static LocalDate[] getDates(String[] strDates) {
//        dateArray[2] = null means that date parsing failed
//        dateArray[2] = dateFrom means that date parsing is ok
        LocalDate[] dateArray = new LocalDate[3];
        LocalDate dateFrom = parseDate(strDates[0]);
        if(dateFrom == null) {
            System.out.println("dateFrom could not be parsed");
            dateArray[0] = null;
            dateArray[2] = null;
            return dateArray;
        } else {
            dateArray[0] = dateFrom;
            System.out.println("Parsed date is: " + dateFrom);
        }

        LocalDate dateTo = parseDate(strDates[1]);
        if(dateTo == null && !strDates[1].equals("NULL")) {
            System.out.println("dateTo could not be parsed");
            dateArray[1] = null;
            dateArray[2] = null;
            return dateArray;
        } else {
            dateArray[1] = dateTo;
            dateArray[2] = dateArray[0];
            System.out.println("Parsed date is: " + dateTo);
        }

        return dateArray;
    }

    public static LocalDate parseDate(String strDate) {
        LocalDate tempDate = null;
        int formatArrayLength = Constants.DateFormatSupport.length;
        for(int i = 0; i < formatArrayLength; i++) {
            String tempFormat = Constants.DateFormatSupport[i];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(tempFormat, Locale.getDefault());
            try{
                tempDate = LocalDate.parse(strDate, formatter);
                System.out.println("This format (" + tempFormat + ") fits the input" );
                return tempDate;
            } catch(DateTimeParseException e) {
                System.out.println("This format (" + tempFormat + ") does not fit the input" );
            }

        }

        return tempDate;
    }

}
