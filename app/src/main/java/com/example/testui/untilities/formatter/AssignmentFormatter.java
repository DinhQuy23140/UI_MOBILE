package com.example.testui.untilities.formatter;

import androidx.annotation.NonNull;

import com.example.testui.R;
import com.example.testui.model.Assignment;
import com.example.testui.model.AssignmentSupervisor;
import com.example.testui.model.Status;

import java.util.ArrayList;
import java.util.List;

public class AssignmentFormatter {

    public static Assignment format(Assignment assignment) {
        if (assignment == null) {
            assignment = new Assignment();
        }

        if (assignment.getStatus() == null) assignment.setStatus("pending");
        if (assignment.getRole() == null) assignment.setRole("main");
        return assignment;
    }

    public static Status statusFormat(String status) {
        switch(status) {
            case "pending":
                return new Status(R.color.timeline_date, "Đang chờ");
            case "cancel":
                return new Status(R.color.red_color, "Đã hủy");
            case "stopped":
                return new Status(R.color.subject_chip_stroke_tertiary, "Đã dừng");
            case "actived":
                return new Status(R.color.subject_chip_text_secondary, "Đang thực hiện");
            default:
                return new Status(R.color.timeline_date, "Đang chờ");
        }
    }

    public Status convertStatus(String status) {
        String text;
        int background;

        switch (status) {
            case "pending":
                text = "Đang chờ";
                background = R.drawable.bg_circle_pending;
                break;
            case "cancel":
                text = "Đã hủy";
                background = R.drawable.bg_badge_modern;
                break;
            case "stopped":
                text = "Đã dừng";
                background = R.drawable.bg_icon_success;
                break;
            case "actived":
                text = "Đang thực hiện";
                background = R.drawable.bg_rating_good;
                break;
            default:
                text = "Đang chờ";
                background = R.drawable.bg_status_pending;
                break;
        }

        return new Status(background, text);
    }
}
