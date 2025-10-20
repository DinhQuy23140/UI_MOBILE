package com.example.testui.untilities.formatter;

import com.example.testui.model.AcademyYear;
import com.example.testui.model.ProjectTerm;
import com.example.testui.model.StageTimeline;

import java.util.ArrayList;
import java.util.List;

public class ProjectTermFormatter {
    public static ProjectTerm format(ProjectTerm projectTerm) {
        if (projectTerm == null) {
            projectTerm = new ProjectTerm();
        }

        if (projectTerm.getStage() == null) projectTerm.setStage("-");
        if (projectTerm.getStatus() == null) projectTerm.setStatus("pending");
        if (projectTerm.getDescription() == null) projectTerm.setDescription("Chưa có");
        if (projectTerm.getStart_date() == null) projectTerm.setStart_date("-");
        if (projectTerm.getEnd_date() == null) projectTerm.setEnd_date("-");

        return  projectTerm;
    }
}
