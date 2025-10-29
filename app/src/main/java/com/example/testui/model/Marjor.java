package com.example.testui.model;

public class Marjor {
    String id, code, name, description;
    Department department;

    public Marjor() {
    }

    public Marjor(String id, String name, String code, String description, Department department) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.department = department;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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
