package com.example.testui.model;

public class CommentLog {
    String id, progress_log_id, supervisor_id, content;
    String created_at;
    Supervisor supervisor;
    ProgressLog progressLog;

    public ProgressLog getProgressLog() {
        return progressLog;
    }

    public void setProgressLog(ProgressLog progressLog) {
        this.progressLog = progressLog;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProgress_log_id() {
        return progress_log_id;
    }

    public void setProgress_log_id(String progress_log_id) {
        this.progress_log_id = progress_log_id;
    }

    public String getSupervisor_id() {
        return supervisor_id;
    }

    public void setSupervisor_id(String supervisor_id) {
        this.supervisor_id = supervisor_id;
    }
}
