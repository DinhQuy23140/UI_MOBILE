package com.example.testui.model;

import java.util.List;

public class Assignment {
    String id;
    String batch_student_id;
    String project_id;
    String status;
    String role;
    BatchStudent batch_student;
    List<Supervisor> supervisors;
    Project project;

    public Assignment(BatchStudent batch_student, String batch_student_id, String id, Project project, String project_id, String role, String status, List<Supervisor> supervisors) {
        this.batch_student = batch_student;
        this.batch_student_id = batch_student_id;
        this.id = id;
        this.project = project;
        this.project_id = project_id;
        this.role = role;
        this.status = status;
        this.supervisors = supervisors;
    }

    public BatchStudent getBatch_student() {
        return batch_student;
    }

    public void setBatch_student(BatchStudent batch_student) {
        this.batch_student = batch_student;
    }

    public String getBatch_student_id() {
        return batch_student_id;
    }

    public void setBatch_student_id(String batch_student_id) {
        this.batch_student_id = batch_student_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Supervisor> getSupervisors() {
        return supervisors;
    }

    public void setSupervisors(List<Supervisor> supervisors) {
        this.supervisors = supervisors;
    }
}
