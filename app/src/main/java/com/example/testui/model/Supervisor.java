package com.example.testui.model;

public class Supervisor {
    String id;
    String teacher_id;
    String max_students;
    String project_term_id;
    String expertise;
    Teacher teacher;
    String status;
    String created_at;
    String updated_at;
    ProjectTerm projectTerm;

    public Supervisor() {
    }

    public Supervisor(String id, String expertise, String max_students, String project_term_id, String status, Teacher teacher, String teacher_id) {
        this.id = id;
        this.expertise = expertise;
        this.max_students = max_students;
        this.project_term_id = project_term_id;
        this.status = status;
        this.teacher = teacher;
        this.teacher_id = teacher_id;
    }

    public ProjectTerm getProjectTerm() {
        return projectTerm;
    }

    public void setProjectTerm(ProjectTerm projectTerm) {
        this.projectTerm = projectTerm;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMax_students() {
        return max_students;
    }

    public void setMax_students(String max_students) {
        this.max_students = max_students;
    }

    public String getProject_term_id() {
        return project_term_id;
    }

    public void setProject_term_id(String project_term_id) {
        this.project_term_id = project_term_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    @Override
    public String toString() {
        if (teacher != null && teacher.getUser() != null) {
            return teacher.getUser().getFullname(); // tên giảng viên
        }
        return super.toString();
    }

}
