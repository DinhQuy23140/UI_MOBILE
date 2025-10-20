package com.example.testui.untilities.formatter;

import com.example.testui.model.ProgressLog;
import com.example.testui.model.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectFormatter {

    public static Project format(Project project) {
        if (project == null) {
            project = new Project();
        }

        // Làm sạch các chuỗi text
        if (project.getName() == null || project.getName().trim().isEmpty()) {
            project.setName("-");
        }

        if (project.getDescription() == null || project.getDescription().trim().isEmpty()) {
            project.setDescription("-");
        }

        return project;
    }
}

