package com.example.testui.model;

import java.util.List;

public class AcademyYear {
    private String id;
    private String year_name;
    List<ProjectTerm> project_terms;

    public AcademyYear() {
    }

    public AcademyYear(String id, String year_name) {
        this.id = id;
        this.year_name = year_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear_name() {
        return year_name;
    }

    public void setYear_name(String year_name) {
        this.year_name = year_name;
    }
}
