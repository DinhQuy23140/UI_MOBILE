package com.example.testui.model;

import java.util.List;

public class Supervisor {
    String id;
    String teacher_id;
    String max_students;
    String project_term_id;
    Teacher teacher;
    String created_at;
    String updated_at;
    ProjectTerm projectTerm;
    List<AssignmentSupervisor> assignment_supervisors;
    List<CouncilsMember> council_members;
    List<CouncilProject> council_projects;
    List<CommentLog> commentLogs;
    List<ProposedTopic> proposeTopics;

    public Supervisor() {
    }

    public Supervisor(String id, String max_students, String project_term_id, Teacher teacher, String teacher_id) {
        this.id = id;
        this.max_students = max_students;
        this.project_term_id = project_term_id;
        this.teacher = teacher;
        this.teacher_id = teacher_id;
    }

    public List<AssignmentSupervisor> getAssignment_supervisors() {
        return assignment_supervisors;
    }

    public void setAssignment_supervisors(List<AssignmentSupervisor> assignment_supervisors) {
        this.assignment_supervisors = assignment_supervisors;
    }

    public List<CommentLog> getCommentLogs() {
        return commentLogs;
    }

    public void setCommentLogs(List<CommentLog> commentLogs) {
        this.commentLogs = commentLogs;
    }

    public List<CouncilsMember> getCouncil_members() {
        return council_members;
    }

    public void setCouncil_members(List<CouncilsMember> council_members) {
        this.council_members = council_members;
    }

    public List<ProposedTopic> getProposeTopics() {
        return proposeTopics;
    }

    public void setProposeTopics(List<ProposedTopic> proposeTopics) {
        this.proposeTopics = proposeTopics;
    }

    public List<CouncilProject> getCouncil_projects() {
        return council_projects;
    }

    public void setCouncil_projects(List<CouncilProject> council_projects) {
        this.council_projects = council_projects;
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
