package com.example.testui.model;

public class AssignmentSupervisor {
    String id;
    String assignment_id;
    String supervisor_id;
    String role;
    String status;
    String score_report;
    String comments;
    Supervisor supervisor;
    Assignment assignment;

    public AssignmentSupervisor() {
    }

    public AssignmentSupervisor(String assignment_id, String id, String role, String status, Supervisor supervisor, String supervisor_id) {
        this.assignment_id = assignment_id;
        this.id = id;
        this.role = role;
        this.status = status;
        this.supervisor = supervisor;
        this.supervisor_id = supervisor_id;
    }

    public AssignmentSupervisor(String assignment_id, String supervisor_id, String role, String status) {
        this.assignment_id = assignment_id;
        this.supervisor_id = supervisor_id;
        this.role = role;
        this.status = status;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getScore_report() {
        return score_report;
    }

    public void setScore_report(String score_report) {
        this.score_report = score_report;
    }

    public String getAssignment_id() {
        return assignment_id;
    }

    public void setAssignment_id(String assignment_id) {
        this.assignment_id = assignment_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public String getSupervisor_id() {
        return supervisor_id;
    }

    public void setSupervisor_id(String supervisor_id) {
        this.supervisor_id = supervisor_id;
    }
}
