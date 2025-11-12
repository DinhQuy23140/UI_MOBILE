package com.example.testui.model;

import java.util.List;

public class Teacher {
    private String id;
    private String user_id;
    private String teacher_code;
    private String degree;
    private String department_id;
    private String position;
    private User user;
    private Department department;
    List<Supervisor> supervisor;
    List<DepartmentRole> departmentRoles;
    List<LecturerSubjects> lecturerSubjects;

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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<DepartmentRole> getDepartmentRoles() {
        return departmentRoles;
    }

    public void setDepartmentRoles(List<DepartmentRole> departmentRoles) {
        this.departmentRoles = departmentRoles;
    }

    public List<LecturerSubjects> getLecturerSubjects() {
        return lecturerSubjects;
    }

    public void setLecturerSubjects(List<LecturerSubjects> lecturerSubjects) {
        this.lecturerSubjects = lecturerSubjects;
    }

    public List<Supervisor> getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(List<Supervisor> supervisor) {
        this.supervisor = supervisor;
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
