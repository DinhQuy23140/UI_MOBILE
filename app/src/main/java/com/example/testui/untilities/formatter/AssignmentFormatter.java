package com.example.testui.untilities.formatter;

import com.example.testui.R;
import com.example.testui.model.Assignment;
import com.example.testui.model.AssignmentSupervisor;
import com.example.testui.model.Status;

import java.util.ArrayList;
import java.util.List;

public class AssignmentFormatter {

    public static Assignment format(Assignment assignment) {
        if (assignment == null) {
            assignment = new Assignment();
        }

        if (assignment.getStatus() == null) assignment.setStatus("pending");
        if (assignment.getRole() == null) assignment.setRole("main");
        assignment.setStudent(StudentFormatter.format(assignment.getStudent()));
        List<AssignmentSupervisor> listAssignmentSupervisor = new ArrayList<>();
        if (assignment.getAssignment_supervisors() != null) {
            for (AssignmentSupervisor assignmentSupervisor : assignment.getAssignment_supervisors()) {
                assignmentSupervisor.setSupervisor(SupervisorFormatter.format(assignmentSupervisor.getSupervisor()));
                listAssignmentSupervisor.add(AssignmentSupervisorFormatter.format(assignmentSupervisor));
            }
        }
        assignment.setAssignment_supervisors(listAssignmentSupervisor);

        assignment.setProject(ProjectFormatter.format(assignment.getProject()));
        assignment.setProject_term(ProjectTermFormatter.format(assignment.getProject_term()));
        assignment.setCouncil_project(CouncilProjectFormatter.format(assignment.getCouncil_project()));
        return assignment;
    }

    public static Status statusFormat(String status) {
        switch(status) {
            case "pending":
                return new Status(R.color.timeline_date, "Đang chờ");
            case "cancel":
                return new Status(R.color.red_color, "Đã hủy");
            case "stopped":
                return new Status(R.color.subject_chip_stroke_tertiary, "Đã dừng");
            case "actived":
                return new Status(R.color.subject_chip_text_secondary, "Đang thực hiện");
            default:
                return new Status(R.color.timeline_date, "Đang chờ");
        }
    }
}
