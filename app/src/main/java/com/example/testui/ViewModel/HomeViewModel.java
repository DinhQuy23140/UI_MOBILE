package com.example.testui.ViewModel;

import android.content.Context;
import android.graphics.Color;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.R;
import com.example.testui.model.Assignment;
import com.example.testui.model.ProgressLog;
import com.example.testui.model.StageTimeline;
import com.example.testui.repository.SinhVienRepository;
import com.example.testui.model.Student;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeViewModel extends ViewModel {
    MutableLiveData<Student> getStudent = new MutableLiveData<>();
    MutableLiveData<String> studentId = new MutableLiveData<>();
    MutableLiveData<Assignment> recentAssignment = new MutableLiveData<>();
    private SinhVienRepository sinhVienRepository;
    public HomeViewModel(Context context) {
        this.sinhVienRepository = new SinhVienRepository(context);
    }

    public MutableLiveData<Student> getGetStudent() {
        return getStudent;
    }

    public String getStudentId() {
        return sinhVienRepository.getStudentId();
    }

    public void getStudentById() {
        sinhVienRepository.getStudentById();
        getStudent = sinhVienRepository.getStudent();
    }

    public void loadRecentAssignment() {
        sinhVienRepository.getRecentAssignment(getStudentId());
        recentAssignment = sinhVienRepository.getRecentAssignment();
    }

    public MutableLiveData<Assignment> getRecentAssignment() {
        return recentAssignment;
    }

    public int getProcess() {
        float count = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        Assignment assignment = recentAssignment.getValue();
        List<StageTimeline> listStage = assignment.getProject_term().getStage_timelines();
        for (StageTimeline stageTimeline : listStage) {
            LocalDate startDate = LocalDate.parse(stageTimeline.getStart_date());
            LocalDate endDate = LocalDate.parse(stageTimeline.getEnd_date());
            if(today.isAfter(startDate) && today.isBefore(endDate)) {
                count+= 0.5;
            }
            if(today.isAfter(endDate)) {
                count++;
            }
        }
        return (int) (count/8 * 100);
    }

    public int getRangeDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();

        Assignment assignment = recentAssignment.getValue();
        if (assignment == null || assignment.getProject_term() == null) return 0;

        LocalDate endDate = LocalDate.parse(assignment.getProject_term().getEnd_date(), formatter);

        long days = ChronoUnit.DAYS.between(today, endDate);
        return (int) Math.max(days, 0); // nếu âm => trả 0
    }

    public Map<Integer, Integer> getBackGroundStatusAssignment() {
        Map<Integer, Integer> backgroundStatus = new HashMap<>();
        Assignment assignment = recentAssignment.getValue();
        switch (assignment.getStatus()) {
            case "pending":
                backgroundStatus.put(R.color.timeline_line, Color.WHITE);
                break;
            case "cancel":
                backgroundStatus.put(R.color.red_color, Color.WHITE);
                break;
            case "stopped":
                backgroundStatus.put(R.color.accentColor, Color.WHITE);
                break;
            case "actived":
                backgroundStatus.put(R.color.subject_chip_stroke_secondary, Color.WHITE);
                break;
            default:
                backgroundStatus.put(R.color.timeline_line, Color.WHITE);
                break;
        }
        return backgroundStatus;
    }
}
