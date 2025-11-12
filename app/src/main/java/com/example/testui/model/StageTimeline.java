package com.example.testui.model;

public class StageTimeline {
    String id, number_of_rounds, start_date, end_date, description, project_term_id;
    ProjectTerm projectTerm;

    public StageTimeline() {
    }

    public StageTimeline(String id, String number_of_rounds, String start_date, String end_date, String description) {
        this.id = id;
        this.number_of_rounds = number_of_rounds;
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
    }

    public ProjectTerm getProjectTerm() {
        return projectTerm;
    }

    public void setProjectTerm(ProjectTerm projectTerm) {
        this.projectTerm = projectTerm;
    }

    public String getProject_term_id() {
        return project_term_id;
    }

    public void setProject_term_id(String project_term_id) {
        this.project_term_id = project_term_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
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
}
