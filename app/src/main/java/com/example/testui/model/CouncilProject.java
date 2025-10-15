package com.example.testui.model;

import java.util.List;

public class CouncilProject {
    String id, council_id, assignment_id, council_member_id, room, date, time, review_score, created_at, updated_at;
    Council council;
    Assignment assignment;
    CouncilsMember council_member;
    List<CouncilProjectDefence> council_project_defences;

    public List<CouncilProjectDefence> getCouncil_project_defences() {
        return council_project_defences;
    }

    public void setCouncil_project_defences(List<CouncilProjectDefence> council_project_defences) {
        this.council_project_defences = council_project_defences;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Council getCouncil() {
        return council;
    }

    public void setCouncil(Council council) {
        this.council = council;
    }

    public CouncilsMember getCouncil_member() {
        return council_member;
    }

    public void setCouncil_member(CouncilsMember council_member) {
        this.council_member = council_member;
    }

    public String getAssignment_id() {
        return assignment_id;
    }

    public void setAssignment_id(String assignment_id) {
        this.assignment_id = assignment_id;
    }

    public String getCouncil_id() {
        return council_id;
    }

    public void setCouncil_id(String council_id) {
        this.council_id = council_id;
    }

    public String getCouncil_member_id() {
        return council_member_id;
    }

    public void setCouncil_member_id(String council_member_id) {
        this.council_member_id = council_member_id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReview_score() {
        return review_score;
    }

    public void setReview_score(String review_score) {
        this.review_score = review_score;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
