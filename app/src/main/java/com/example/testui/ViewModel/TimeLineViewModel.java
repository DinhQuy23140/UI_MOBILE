package com.example.testui.ViewModel;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.R;
import com.example.testui.model.Assignment;
import com.example.testui.model.AssignmentSupervisor;
import com.example.testui.model.StageTimeline;
import com.example.testui.model.Status;
import com.example.testui.model.Supervisor;
import com.example.testui.repository.AssignmentRepository;
import com.example.testui.repository.SinhVienRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TimeLineViewModel extends ViewModel {
    AssignmentRepository assignmentRepository;
    SinhVienRepository sinhVienRepository;

    public TimeLineViewModel(Context context) {
        this.assignmentRepository = new AssignmentRepository();
        this.sinhVienRepository = new SinhVienRepository(context);
    }

    public void loadAssignmentByStudentIdAndTermId(String studentId, String termId){
        assignmentRepository.loadAssignmentByStudentIdAndTermId(studentId, termId);
    }

    public MutableLiveData<Assignment> getAssignmentByStudentIdAndTermId(){
        return assignmentRepository.getAssignmentByStudentIdAndTermId();
    }

    public String getStudentId() {
        return sinhVienRepository.getStudentId();
    }

    public List<Supervisor> getSupervisor(List<AssignmentSupervisor> listAssignmentSupervisor) {
        List<Supervisor> listSupervisor = new ArrayList<>();
        for (AssignmentSupervisor assignmentSupervisor : listAssignmentSupervisor) {
            listSupervisor.add(assignmentSupervisor.getSupervisor());
        }
        return listSupervisor;
    }

    public Status loadStatusAssignment(String status){
        switch (status) {
            case "pending":
                return new Status(R.drawable.bg_task_icon, "Đang chờ");
            case "actived":
                return new Status(R.drawable.bg_stats_success, "Đang thực hiện");
            case "cancelled":
                return new Status(R.drawable.bg_status_pending, "Đã hoãn đồ án");
            case "stopped":
                return new Status(R.drawable.bg_icon_success, "Dừng đồ án");
            default:
                return new Status(R.drawable.bg_task_icon, "Đang chờ");
        }
    }

    public Status loadStatusStage(StageTimeline stage) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Parse chuỗi thành LocalDate
        LocalDate startDate = LocalDate.parse(stage.getStart_date(), inputFormatter);
        LocalDate endDate = LocalDate.parse(stage.getEnd_date(), inputFormatter);
        LocalDate now = LocalDate.now();

        if ((startDate.isBefore(now) || startDate.isEqual(now)) &&
                (endDate.isAfter(now) || endDate.isEqual(now))) {
            // Giai đoạn đang diễn ra
            return new Status(R.drawable.bg_circle_inprogress, "Đang diễn ra");
        } else if (endDate.isBefore(now)) {
            // Đã kết thúc
            return new Status(R.drawable.bg_circle_completed, "Hoàn thành");
        } else {
            // Chưa bắt đầu
            return new Status(R.drawable.bg_circle_pending, "Chưa diễn ra");
        }
    }
}
