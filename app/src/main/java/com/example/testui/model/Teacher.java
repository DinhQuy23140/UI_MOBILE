package com.example.testui.model;

public class Teacher {
    private String id;
    private String user_id;
    private String teacher_code;
    private String degree;
    private String department_id;
    private String position;
    private User user;

    public Teacher() {
    }

    public Teacher(String degree, String department_id, String id, String position, String teacher_code, User user, String user_id) {
        this.degree = degree;
        this.department_id = department_id;
        this.id = id;
        this.position = position;
        this.teacher_code = teacher_code;
        this.user = user;
        this.user_id = user_id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTeacher_code() {
        return teacher_code;
    }

    public void setTeacher_code(String teacher_code) {
        this.teacher_code = teacher_code;
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
