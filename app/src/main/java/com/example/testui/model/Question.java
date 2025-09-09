package com.example.testui.model;

public class Question {
    private String id;
    private String idExam;
    private String contentQuestion;
    private int A, B, C, D, correctAnswer, yourAnswer;
    private String[] imageQuestion, contentAnswer;

    public Question(String id, String idExam, String[] imageQuestion, int a, int b, int c, int d, int correctAnswer, String[] contentAnswer, String contentQuestion) {
        this.id = id;
        this.idExam = idExam;
        this.imageQuestion = imageQuestion;
        A = a;
        B = b;
        C = c;
        D = d;
        this.correctAnswer = correctAnswer;
        this.yourAnswer = -1;
        this.contentAnswer = contentAnswer;
        this.contentQuestion = contentQuestion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdExam() {
        return idExam;
    }

    public void setIdExam(String idExam) {
        this.idExam = idExam;
    }

    public String getContentQuestion() {
        return contentQuestion;
    }

    public void setContentQuestion(String contentQuestion) {
        this.contentQuestion = contentQuestion;
    }

    public int getA() {
        return A;
    }

    public void setA(int a) {
        A = a;
    }

    public int getB() {
        return B;
    }

    public void setB(int b) {
        B = b;
    }

    public int getC() {
        return C;
    }

    public void setC(int c) {
        C = c;
    }

    public int getD() {
        return D;
    }

    public void setD(int d) {
        D = d;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getYourAnswer() {
        return yourAnswer;
    }

    public void setYourAnswer(int yourAnswer) {
        this.yourAnswer = yourAnswer;
    }
}
