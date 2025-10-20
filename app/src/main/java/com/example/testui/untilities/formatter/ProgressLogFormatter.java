package com.example.testui.untilities.formatter;

import com.example.testui.model.Attachment;
import com.example.testui.model.ProgressLog;

import java.util.ArrayList;
import java.util.List;

public class ProgressLogFormatter {
    public static ProgressLog format(ProgressLog progressLog) {
        if (progressLog == null) {
            progressLog = new ProgressLog();
        }
        if (progressLog.getTitle() == null) progressLog.setTitle("-");
        if (progressLog.getDescription() == null) progressLog.setDescription("-");
        if (progressLog.getStart_date_time() == null) progressLog.setStart_date_time("-");
        if (progressLog.getEnd_date_time() == null) progressLog.setEnd_date_time("-");

        if (progressLog.getInstructor_comment() == null) progressLog.setInstructor_comment("-");
        if (progressLog.getStudent_status() == null) progressLog.setStudent_status("pending");
        if (progressLog.getInstructor_status() == null) progressLog.setInstructor_status("pending");

        if (progressLog.getStudent_status() == null) progressLog.setStudent_status("-");
        if (progressLog.getInstructor_status() == null) progressLog.setInstructor_status("-");
        return progressLog;
    }
}
