package com.example.testui.untilities.formatter;

import com.example.testui.model.AcademyYear;

public class AcademyYearFormatter {

    public static AcademyYear format(AcademyYear academyYear) {
        if (academyYear == null) {
            academyYear = new AcademyYear();
        }

        if (academyYear.getYear_name() == null || academyYear.getYear_name().trim().isEmpty()) {
            academyYear.setYear_name(" - ");
        }

        return academyYear;
    }
}
