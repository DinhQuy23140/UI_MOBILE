package com.example.testui.model;

import java.util.List;

public class Project {
    String id;
    String name;
    String description;
    List<ProgressLog> progress_logs;
    Assignment assignment;
    List<ReportFile> report_files;

    public Project() {
    }

    public Project(String id, String name, String description, List<ProgressLog> progress_logs) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.progress_logs = progress_logs;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public List<ReportFile> getReport_files() {
        return report_files;
    }

    public void setReport_files(List<ReportFile> report_files) {
        this.report_files = report_files;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProgressLog> getProgress_logs() {
        return progress_logs;
    }

    public void setProgress_logs(List<ProgressLog> progress_logs) {
        this.progress_logs = progress_logs;
    }
}
