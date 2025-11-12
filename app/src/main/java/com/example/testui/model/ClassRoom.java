package com.example.testui.model;

public class ClassRoom {
    String id, class_code, class_name, number_students, admission_year, cohort, description, department_id, major_id;
    Department department;
    Marjor marjor;
    public ClassRoom(String id, String class_code, String class_name, String number_students) {
        this.id = id;
        this.class_code = class_code;
        this.class_name = class_name;
        this.number_students = number_students;
    }

    public String getAdmission_year() {
        return admission_year;
    }

    public void setAdmission_year(String admission_year) {
        this.admission_year = admission_year;
    }

    public String getCohort() {
        return cohort;
    }

    public void setCohort(String cohort) {
        this.cohort = cohort;
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

    public String getMajor_id() {
        return major_id;
    }

    public void setMajor_id(String major_id) {
        this.major_id = major_id;
    }

    public Marjor getMarjor() {
        return marjor;
    }

    public void setMarjor(Marjor marjor) {
        this.marjor = marjor;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber_students() {
        return number_students;
    }

    public void setNumber_students(String number_students) {
        this.number_students = number_students;
    }
}
