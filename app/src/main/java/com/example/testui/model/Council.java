package com.example.testui.model;

import java.util.List;

public class Council {
    String id, name, project_term_id, description, date, address, status, code, department_id, time, created_at, updated_at;
    Department department;
    List<CouncilsMember> council_members;
    ProjectTerm project_term;
    List<CouncilProject> council_projects;

    public Council() {
    }

    public Council(String id, String address, String date, String description, String name, String project_term_id, String status) {
        this.id = id;
        this.address = address;
        this.date = date;
        this.description = description;
        this.name = name;
        this.project_term_id = project_term_id;
        this.status = status;
    }

    public List<CouncilProject> getCouncil_projects() {
        return council_projects;
    }

    public void setCouncil_projects(List<CouncilProject> council_projects) {
        this.council_projects = council_projects;
    }

    public ProjectTerm getProject_term() {
        return project_term;
    }

    public void setProject_term(ProjectTerm project_term) {
        this.project_term = project_term;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<CouncilsMember> getCouncil_members() {
        return council_members;
    }

    public void setCouncil_members(List<CouncilsMember> council_members) {
        this.council_members = council_members;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
