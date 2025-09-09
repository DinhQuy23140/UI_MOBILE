package com.example.testui.model;

import java.util.List;

public class Process {
    private String id;                  // ID mốc tiến độ
    private String title;               // Tiêu đề mốc tiến độ
    private String description;         // Mô tả mốc tiến độ
    private String subjectId;           // ID môn học liên quan
    private String studentId;           // ID sinh viên thực hiện
    private long deadline;              // Hạn chót hoàn thành (timestamp)
    private List<ProgressLog> logs;     // Danh sách các log tiến độ


}
