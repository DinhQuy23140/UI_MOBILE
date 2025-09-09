package com.example.testui.model;

public class Student {
    private String id;
    private String user_id;
    private String student_code;
    private String class_code;
    private String major_id;
    private String department_id;
    private String course_year;
    private User user;

    public Student(String class_code, String course_year, String department_id, String id, String major_id, String student_code, User user, String user_id) {
        this.class_code = class_code;
        this.course_year = course_year;
        this.department_id = department_id;
        this.id = id;
        this.major_id = major_id;
        this.student_code = student_code;
        this.user = user;
        this.user_id = user_id;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    public String getCourse_year() {
        return course_year;
    }

    public void setCourse_year(String course_year) {
        this.course_year = course_year;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMajor_id() {
        return major_id;
    }

    public void setMajor_id(String major_id) {
        this.major_id = major_id;
    }

    public String getStudent_code() {
        return student_code;
    }

    public void setStudent_code(String student_code) {
        this.student_code = student_code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
