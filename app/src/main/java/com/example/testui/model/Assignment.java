package com.example.testui.model;

import androidx.annotation.NonNull;

import com.example.testui.R;

import java.util.List;

public class Assignment {
    String id;
    String student_id;
    String project_id;
    String project_term_id;
    String status;
    String counter_argument_id;
    String counter_argument_status;
    String counter_argument_comment;
    Student student;
    List<AssignmentSupervisor> assignment_supervisors;
    Project project;
    ProjectTerm project_term;
    CouncilProject council_project;
    String created_at, updated_at;

    public Assignment() {
    }

    public Assignment(List<AssignmentSupervisor> assignment_supervisors, String counter_argument_id, String counter_argument_status, String created_at, String id, Project project, String project_id, ProjectTerm project_term, String project_term_id, String status, Student student, String student_id, String updated_at) {
        this.assignment_supervisors = assignment_supervisors;
        this.counter_argument_id = counter_argument_id;
        this.counter_argument_status = counter_argument_status;
        this.created_at = created_at;
        this.id = id;
        this.project = project;
        this.project_id = project_id;
        this.project_term = project_term;
        this.project_term_id = project_term_id;
        this.status = status;
        this.student = student;
        this.student_id = student_id;
        this.updated_at = updated_at;
    }

    public String getCounter_argument_comment() {
        return counter_argument_comment;
    }

    public void setCounter_argument_comment(String counter_argument_comment) {
        this.counter_argument_comment = counter_argument_comment;
    }

    public CouncilProject getCouncil_project() {
        return council_project;
    }

    public void setCouncil_project(CouncilProject council_project) {
        this.council_project = council_project;
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

    public String getCounter_argument_id() {
        return counter_argument_id;
    }

    public void setCounter_argument_id(String counter_argument_id) {
        this.counter_argument_id = counter_argument_id;
    }

    public String getCounter_argument_status() {
        return counter_argument_status;
    }

    public void setCounter_argument_status(String counter_argument_status) {
        this.counter_argument_status = counter_argument_status;
    }

    public ProjectTerm getProject_term() {
        return project_term;
    }

    public void setProject_term(ProjectTerm project_term) {
        this.project_term = project_term;
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
