package com.academy.sirma.finalExam.validate;

import com.academy.sirma.finalExam.dto.EmployeeReferenceDto;
import com.academy.sirma.finalExam.utility.Constants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Validate {
    public static boolean isValidReference(String[] inputRef) {
        if(inputRef.length != 4) {
            return false;
        }
        if(!inputRef[0].matches("[0-9]*") ||
        !inputRef[1].matches("[0-9]*")) {
            return false;
        }

        if(!isFormatValid(inputRef[2]) || !isFormatValid(inputRef[3])) {
            return false;
        }

        return true;
    }

    public static boolean isFormatValid(String strDate) {
        if(strDate == null || strDate.equals("NULL")
                || strDate.equals("null") || strDate.equals("")) {
            return true;
        }
        int formatArrayLength = Constants.DateFormatSupport.length;
        for(int i = 0; i < formatArrayLength; i++) {
            String tempFormat = Constants.DateFormatSupport[i];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(tempFormat);
            try{
                LocalDate tempDate = LocalDate.parse(strDate, formatter);
                return true;
            } catch(DateTimeParseException e) {
                System.out.println("This format " + tempFormat + " does not fit the input" );
                System.out.println(e);
            }
        }

        return false;
    }

}
