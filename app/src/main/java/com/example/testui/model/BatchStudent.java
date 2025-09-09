package com.example.testui.model;

import java.util.List;

public class BatchStudent {
    private String id;
    private String student_id;
    private String project_term_id;
    private String status;
    private Student student;

    public BatchStudent(String id, String project_term_id, String status, Student student, String student_id) {
        this.id = id;
        this.project_term_id = project_term_id;
        this.status = status;
        this.student = student;
        this.student_id = student_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }
}
