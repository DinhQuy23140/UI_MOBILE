package com.example.testui.model;

import java.util.List;

public class Subject {
    String id, code, name, description, number_of_credits, department_id;

    Department department;
    List<LecturerSubjects> lecturer_subjects;

    public Subject() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
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

    public List<LecturerSubjects> getLecturer_subjects() {
        return lecturer_subjects;
    }

    public void setLecturer_subjects(List<LecturerSubjects> lecturer_subjects) {
        this.lecturer_subjects = lecturer_subjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber_of_credits() {
        return number_of_credits;
    }

    public void setNumber_of_credits(String number_of_credits) {
        this.number_of_credits = number_of_credits;
    }
}
