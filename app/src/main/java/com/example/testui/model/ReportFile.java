package com.example.testui.model;

public class ReportFile {
    String id, project_id, file_name, file_url, file_type, type_report, created_at, updated_at, status;

    public ReportFile(String id, String file_name, String file_type, String file_url, String project_id, String type_report, String status) {
        this.id = id;
        this.file_name = file_name;
        this.file_type = file_type;
        this.file_url = file_url;
        this.project_id = project_id;
        this.type_report = type_report;
        this.status = status;
    }

    public ReportFile(String file_name, String file_type, String file_url, String project_id, String type_report, String status) {
        this.file_name = file_name;
        this.file_type = file_type;
        this.file_url = file_url;
        this.type_report = type_report;
        this.project_id = project_id;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFile_name() {
        return file_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getType_report() {
        return type_report;
    }

    public void setType_report(String type_report) {
        this.type_report = type_report;
    }
}
