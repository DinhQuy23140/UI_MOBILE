package com.example.testui.model;

public class Marjor {
    String id, code, name, description;
    Faculties faculties;

    public Marjor() {
    }

    public Marjor(String id, String name, String code, String description, Faculties faculties) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.faculties = faculties;
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

    public Faculties getFaculties() {
        return faculties;
    }

    public void setFaculties(Faculties faculties) {
        this.faculties = faculties;
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
