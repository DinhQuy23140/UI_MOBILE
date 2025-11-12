package com.example.testui.model;

public class ProposedTopic {
    String id, supervisor_id, title, description, proposed_at;

    Supervisor supervisor;

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

    public String getProposed_at() {
        return proposed_at;
    }

    public void setProposed_at(String proposed_at) {
        this.proposed_at = proposed_at;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public String getSupervisor_id() {
        return supervisor_id;
    }

    public void setSupervisor_id(String supervisor_id) {
        this.supervisor_id = supervisor_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
