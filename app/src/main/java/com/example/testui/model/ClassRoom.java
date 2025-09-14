package com.example.testui.model;

public class ClassRoom {
    String id, class_code, class_name, number_students, cohort_id;

    public ClassRoom(String id, String class_code, String class_name, String cohort_id, String number_students) {
        this.id = id;
        this.class_code = class_code;
        this.class_name = class_name;
        this.cohort_id = cohort_id;
        this.number_students = number_students;
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

    public String getCohort_id() {
        return cohort_id;
    }

    public void setCohort_id(String cohort_id) {
        this.cohort_id = cohort_id;
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
