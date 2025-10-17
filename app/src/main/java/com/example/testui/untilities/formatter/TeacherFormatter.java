package com.example.testui.untilities.formatter;

import com.example.testui.model.Teacher;

public class TeacherFormatter {
    public static Teacher format(Teacher teacher) {
        if (teacher == null) {
            teacher = new Teacher();
        }

        if (teacher.getTeacher_code() == null) teacher.setTeacher_code("-");
        if (teacher.getDegree() == null) teacher.setDegree("-");
        if (teacher.getPosition() == null) teacher.setPosition("-");
        teacher.setUser(UserFormatter.format(teacher.getUser()));

        return teacher;
    }
}
