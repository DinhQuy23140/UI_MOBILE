package com.example.testui.model;

import com.example.testui.R;

public class Status {
    String strStatus;
    int backgroundColor;
    int textColor = R.color.white;

    public Status(int backgroundColor, String strStatus, int textColor) {
        this.backgroundColor = backgroundColor;
        this.strStatus = strStatus;
        this.textColor = textColor;
    }

    public Status(int backgroundColor, String strStatus) {
        this.backgroundColor = backgroundColor;
        this.strStatus = strStatus;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getStrStatus() {
        return strStatus;
    }

    public void setStrStatus(String strStatus) {
        this.strStatus = strStatus;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
