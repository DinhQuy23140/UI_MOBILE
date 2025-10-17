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

        // Format danh sách progress_logs
        List<ProgressLog> formattedLogs = new ArrayList<>();
        if (project.getProgress_logs() != null && !project.getProgress_logs().isEmpty()) {
            for (ProgressLog log : project.getProgress_logs()) {
                formattedLogs.add(ProgressLogFormatter.format(log));
            }
        }

        project.setProgress_logs(formattedLogs);
        return project;
    }
}

