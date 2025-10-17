package com.example.testui.untilities.formatter;

import com.example.testui.model.Faculties;

public class FacultiesFormatter {
    public static Faculties format(Faculties faculties) {
        if (faculties == null) {
            faculties = new Faculties();
        }
        if (faculties.getCode() == null) faculties.setCode("-");
        if (faculties.getName() == null) faculties.setName("-");
        if (faculties.getDescription() == null) faculties.setDescription("-");
        return faculties;
    }
}
