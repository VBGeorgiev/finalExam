package com.academy.sirma.finalExam.utility;

import java.time.LocalDate;

public class NullDate {
    public static LocalDate convertToCurrentDate(LocalDate date) {
        if(date == null) {
            date = LocalDate.now();
        }
        return date;
    }
}
