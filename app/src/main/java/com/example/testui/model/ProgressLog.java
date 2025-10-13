package com.example.testui.model;

import com.example.testui.R;

import java.util.Date;
import java.util.List;

public class ProgressLog {
    private String id;                         // ID của log
    private String process_id;                  // ID tiến trình mà log này thuộc về
    private String title;                      // Tiêu đề log
    private String description;                // Mô tả công việc

    private String start_date_time;                // Ngày giờ bắt đầu
    private String end_date_time;                  // Ngày giờ kết thúc

    private List<Attachment> attachments;      // Danh sách tệp đính kèm

    private String instructor_comment;          // Nhận xét của giảng viên
    private String student_status;    // Trạng thái từ sinh viên
    private String instructor_status; // Trạng thái đánh giá từ giảng viên
    private int backgroundInstructor, backgroundStudent;

    public ProgressLog(String id, String process_id, String title, String description, String start_date_time, String end_date_time, List<Attachment> attachments, String instructor_comment, String student_status, String instructor_status) {
        this.id = id;
        this.process_id = process_id;
        this.title = title;
        this.description = description;
        this.start_date_time = start_date_time;
        this.end_date_time = end_date_time;
        this.attachments = attachments;
        this.instructor_comment = instructor_comment;
        this.student_status = student_status;
        this.instructor_status = instructor_status;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnd_date_time() {
        return end_date_time;
    }

    public void setEnd_date_time(String end_date_time) {
        this.end_date_time = end_date_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstructor_comment() {
        return instructor_comment;
    }

    public void setInstructor_comment(String instructor_comment) {
        if (instructor_comment.equals("null")) {
            this.instructor_comment = "";
        } else {
            this.instructor_comment = instructor_comment;
        }
    }

    public String getInstructor_status() {
        return instructor_status;
    }

    public void setInstructor_status(String instructor_status) {
        this.instructor_status = instructor_status;
    }

    public String getProcess_id() {
        return process_id;
    }

    public void setProcess_id(String process_id) {
        this.process_id = process_id;
    }

    public String getStart_date_time() {
        return start_date_time;
    }

    public void setStart_date_time(String start_date_time) {
        this.start_date_time = start_date_time;
    }

    public String getStudent_status() {
        return student_status;
    }

    public void setStudent_status(String student_status) {
        this.student_status = student_status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateRange() {
        return start_date_time + " - " + end_date_time;
    }

    public String getFeedback() {
        return "Status: " + instructor_status;
    }

    public String getStatus() {
        return "Trang thái: " + student_status;
    }

    public int getBackgroundInstructor() {
        return backgroundInstructor;
    }

    public void setBackgroundInstructor(int backgroundInstructor) {
        this.backgroundInstructor = backgroundInstructor;
    }

    public int getBackgroundStudent() {
        return backgroundStudent;
    }

    public void setBackgroundStudent(int backgroundStudent) {
        this.backgroundStudent = backgroundStudent;
    }

    public String instructorStatusToString() {
        String status = "";
        switch (instructor_status) {
            case "pending":
                status = "Chưa duyệt";
                backgroundInstructor = R.drawable.bg_circle_pending;
                break;
            case "approved":
                status = "Đạt";
                backgroundInstructor = R.drawable.bg_status_completed;
                break;
            case "need_editing":
                status = "Cần chỉnh sửa";
                backgroundInstructor = R.drawable.bg_icon_primary;
                break;
            case "not_achieved":
                status = "Chưa đạt";
                backgroundInstructor = R.drawable.bg_icon_success;
                break;
            default:
                status = "Chưa duyệt";
                backgroundInstructor = R.drawable.bg_circle_pending;
                break;
        }
        return status;
    }

    public String studentStatusToString() {
        String status = "";
        switch (student_status) {
            case "in_progress":
                status = "Đang thực hiện";
                backgroundStudent = R.drawable.bg_circle_pending;
                break;
            case "completed":
                status = "Hoàn thành";
                backgroundStudent = R.drawable.bg_status_completed;
                break;
            case "not_completed":
                status = "Chưa hoàn thành";
                backgroundStudent = R.drawable.bg_badge_modern;
                break;
            default:
                status = "Chưa bắt đầu";
                backgroundStudent = R.drawable.bg_icon_primary;
                break;
        }
        return status;
    }
}
