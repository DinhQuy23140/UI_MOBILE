package com.example.testui.untilities.formatter;

import com.example.testui.model.Student;

public class StudentFormatter {
    public static Student format(Student student) {
        if (student == null) {
            student = new Student();
        }

        if (student.getStudent_code() == null) student.setStudent_code("-");
        if (student.getClass_code() == null) student.setClass_code("-");
        if (student.getCourse_year() == null) student.setCourse_year("-");
        return student;
    }
}
