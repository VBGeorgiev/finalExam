package com.academy.sirma.finalExam.utility;

import java.time.LocalDate;

public class Convert {
    public static LocalDate NullDateToCurrentDate(LocalDate date) {
        if(date == null) {
            date = LocalDate.now();
        }
        return date;
    }
}
