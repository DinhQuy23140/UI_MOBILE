package com.example.testui.model;

public enum InstructorLogStatus {
    PASSED,         // Đạt
    FAILED,         // Chưa đạt
    NEEDS_REVISION;  // Cần chỉnh sửa

    public String getDisplayName() {
        switch (this) {
            case PASSED:
                return "Đạt";
            case FAILED:
                return "Chưa đạt";
            case NEEDS_REVISION:
                return "Cần chỉnh sửa";
            default:
                return "";
        }
    }
}
