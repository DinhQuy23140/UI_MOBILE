package com.example.testui.model;

public class RegisterProjectTerm {
    String student_id, project_term_id, status;

    public RegisterProjectTerm(String project_term_id, String student_id, String status) {
        this.project_term_id = project_term_id;
        this.student_id = student_id;
        this.status = status;
    }

    public String getProject_term_id() {
        return project_term_id;
    }

    public void setProject_term_id(String project_term_id) {
        this.project_term_id = project_term_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }
}
