package com.example.testui.untilities.formatter;

import com.example.testui.R;
import com.example.testui.model.Status;

public class AssignmentFormatter {
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
}
