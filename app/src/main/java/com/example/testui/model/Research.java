package com.example.testui.model;

import java.util.List;

public class Research {
    String id, name, description;

    public List<UserResearch> userResearches;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserResearch> getUserResearches() {
        return userResearches;
    }

    public void setUserResearches(List<UserResearch> userResearches) {
        this.userResearches = userResearches;
    }
}
