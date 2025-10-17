package com.example.testui.model;

public class Department {
    String id, code, name, description, faculty_id;
    Faculties faculties;
    public Department() {
    }

    public Department(String id, String code, String description, String faculty_id, String name) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.faculty_id = faculty_id;
        this.name = name;
    }

    public Faculties getFaculties() {
        return faculties;
    }

    public void setFaculties(Faculties faculties) {
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
