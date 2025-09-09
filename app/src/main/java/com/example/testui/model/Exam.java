package com.example.testui.model;

import java.util.List;

public class Exam {
    private String id;
    private String type;
    List<Question> listQuestion;

    public Exam(String id, List<Question> listQuestion, String type) {
        this.id = id;
        this.listQuestion = listQuestion;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Question> getListQuestion() {
        return listQuestion;
    }

    public void setListQuestion(List<Question> listQuestion) {
        this.listQuestion = listQuestion;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
