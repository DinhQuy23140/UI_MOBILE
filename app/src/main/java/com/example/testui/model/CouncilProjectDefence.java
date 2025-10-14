package com.example.testui.model;

public class CouncilProjectDefence {
    String id, council_project_id, council_member_id, score, comments, created_at, updated_at;
    CouncilProject council_project;
    CouncilsMember council_member;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public CouncilsMember getCouncil_member() {
        return council_member;
    }

    public void setCouncil_member(CouncilsMember council_member) {
        this.council_member = council_member;
    }

    public String getCouncil_member_id() {
        return council_member_id;
    }

    public void setCouncil_member_id(String council_member_id) {
        this.council_member_id = council_member_id;
    }

    public CouncilProject getCouncil_project() {
        return council_project;
    }

    public void setCouncil_project(CouncilProject council_project) {
        this.council_project = council_project;
    }

    public String getCouncil_project_id() {
        return council_project_id;
    }

    public void setCouncil_project_id(String council_project_id) {
        this.council_project_id = council_project_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
