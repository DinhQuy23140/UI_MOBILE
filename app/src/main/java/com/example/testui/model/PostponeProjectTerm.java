package com.example.testui.model;

import java.util.List;

public class PostponeProjectTerm {
    String id, project_term_id, assignment_id, status, note;
    ProjectTerm projectTerm;
    Assignment assignment;
    List<PostponeProjectTermFile> postponeProjectTermFiles;

    public PostponeProjectTerm() {
    }

    public PostponeProjectTerm(String project_term_id, String assignment_id, String status, String note) {
        this.project_term_id = project_term_id;
        this.assignment_id = assignment_id;
        this.status = status;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
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

    public List<PostponeProjectTermFile> getPostponeProjectTermFiles() {
        return postponeProjectTermFiles;
    }

    public void setPostponeProjectTermFiles(List<PostponeProjectTermFile> postponeProjectTermFiles) {
        this.postponeProjectTermFiles = postponeProjectTermFiles;
    }

    public String getProject_term_id() {
        return project_term_id;
    }

    public void setProject_term_id(String project_term_id) {
        this.project_term_id = project_term_id;
    }

    public ProjectTerm getProjectTerm() {
        return projectTerm;
    }

    public void setProjectTerm(ProjectTerm projectTerm) {
        this.projectTerm = projectTerm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
