package com.example.testui.model;

public class UserResearch {
    String id, user_id, research_id;
    User user;
    Research research;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Research getResearch() {
        return research;
    }

    public void setResearch(Research research) {
        this.research = research;
    }

    public String getResearch_id() {
        return research_id;
    }

    public void setResearch_id(String research_id) {
        this.research_id = research_id;
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
