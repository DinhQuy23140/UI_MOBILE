package com.example.testui.model;

public enum StudentLogStatus {
    NOT_STARTED,     // Chưa bắt đầu
    IN_PROGRESS,     // Đang thực hiện
    INCOMPLETE,      // Chưa hoàn thành
    COMPLETED;        // Đã hoàn thành

    public String getDisplayName() {
        switch (this) {
            case NOT_STARTED:
                return "Chưa bắt đầu";
            case IN_PROGRESS:
                return "Đang thực hiện";
            case INCOMPLETE:
                return "Chưa hoàn thành";
            case COMPLETED:
                return "Đã hoàn thành";
            default:
                return "";
        }
    }
}
