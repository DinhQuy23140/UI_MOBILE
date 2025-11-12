package com.example.testui.untilities.formatter;

import android.graphics.Color;

import com.example.testui.R;
import com.example.testui.model.AcademyYear;
import com.example.testui.model.ProjectTerm;
import com.example.testui.model.StageTimeline;
import com.example.testui.model.Status;

import java.security.PublicKey;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProjectTermFormatter {
    public static ProjectTerm format(ProjectTerm projectTerm) {
        if (projectTerm == null) {
            projectTerm = new ProjectTerm();
        }

        if (projectTerm.getStage() == null) projectTerm.setStage("-");
//        if (projectTerm.getStatus() == null) projectTerm.setStatus("pending");
        if (projectTerm.getDescription() == null) projectTerm.setDescription("Chưa có");
        if (projectTerm.getStart_date() == null) projectTerm.setStart_date("-");
        if (projectTerm.getEnd_date() == null) projectTerm.setEnd_date("-");

        return  projectTerm;
    }

    public static Status getStatus(ProjectTerm projectTerm) {
        LocalDate now = LocalDate.now();
        LocalDate startDate = LocalDate.parse(projectTerm.getStart_date());
        LocalDate endDate = LocalDate.parse(projectTerm.getEnd_date());
        if (now.isBefore(startDate)) {
            return new Status(R.drawable.bg_status_pending, "Chưa bắt đầu");
        } else if (now.isAfter(endDate)) {
            return new Status(R.drawable.bg_status_completed, "Đã kết thúc");
        } else {
            return new Status(R.drawable.bg_task_icon, "Đang diễn ra");
        }
    }
}
