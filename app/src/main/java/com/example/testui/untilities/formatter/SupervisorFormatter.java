package com.example.testui.untilities.formatter;

import com.example.testui.model.Supervisor;

public class SupervisorFormatter {
    public static Supervisor format(Supervisor supervisor) {
        if (supervisor == null){
            supervisor = new Supervisor();
        }

        if (supervisor.getMax_students() == null) supervisor.setMax_students("0");
        if (supervisor.getStatus() == null) supervisor.setStatus("pending");
        if (supervisor.getExpertise() == null) supervisor.setExpertise("-");
        supervisor.setTeacher(TeacherFormatter.format(supervisor.getTeacher()));
        supervisor.setProjectTerm(ProjectTermFormatter.format(supervisor.getProjectTerm()));
        return supervisor;
    }
}
