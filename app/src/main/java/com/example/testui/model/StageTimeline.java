package com.example.testui.model;

public class StageTimeline {
    String id, project_id, number_of_rounds, start_date, end_date, description, status;

    public StageTimeline() {
    }

    public StageTimeline(String id, String number_of_rounds, String project_id, String start_date, String end_date, String status, String description) {
        this.id = id;
        this.number_of_rounds = number_of_rounds;
        this.project_id = project_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber_of_rounds() {
        return number_of_rounds;
    }

    public void setNumber_of_rounds(String number_of_rounds) {
        this.number_of_rounds = number_of_rounds;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
