package com.example.testui.untilities.formatter;

import com.example.testui.model.AssignmentSupervisor;

public class AssignmentSupervisorFormatter {
    public static AssignmentSupervisor format(AssignmentSupervisor assignmentSupervisor) {
        if (assignmentSupervisor == null) {
            assignmentSupervisor = new AssignmentSupervisor();
        }

        if (assignmentSupervisor.getRole() == null) assignmentSupervisor.setRole("main");
        assignmentSupervisor.setSupervisor(SupervisorFormatter.format(assignmentSupervisor.getSupervisor()));

        return  assignmentSupervisor;
    }
}
