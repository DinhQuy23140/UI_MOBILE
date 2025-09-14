package com.example.testui.model;

public class Marjor {
    String id, code, name, description, faculty_id;

    public Marjor(String id, String name, String code, String description, String faculty_id) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.faculty_id = faculty_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(String faculty_id) {
        this.faculty_id = faculty_id;
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
}
