package com.example.testui.model;

import java.util.List;

public class Assignment {
    String id;
    String student_id;
    String project_id;
    String project_term_id;
    String status;
    String role;
    Student student;
    List<AssignmentSupervisor> assignment_supervisors;
    Project project;

    public Assignment(List<AssignmentSupervisor> assignment_supervisors, String id, Project project, String project_id, String project_term_id, String role, String status, Student student, String student_id) {
        this.assignment_supervisors = assignment_supervisors;
        this.id = id;
        this.project = project;
        this.project_id = project_id;
        this.project_term_id = project_term_id;
        this.role = role;
        this.status = status;
        this.student = student;
        this.student_id = student_id;
    }

    public List<AssignmentSupervisor> getAssignment_supervisors() {
        return assignment_supervisors;
    }

    public void setAssignment_supervisors(List<AssignmentSupervisor> assignment_supervisors) {
        this.assignment_supervisors = assignment_supervisors;
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

    public String getProject_term_id() {
        return project_term_id;
    }

    public void setProject_term_id(String project_term_id) {
        this.project_term_id = project_term_id;
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
