package com.example.testui.ViewModel;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.R;
import com.example.testui.model.Assignment;
import com.example.testui.model.AssignmentSupervisor;
import com.example.testui.model.PostponeProjectTerm;
import com.example.testui.model.PostponeProjectTermFile;
import com.example.testui.model.StageTimeline;
import com.example.testui.model.Status;
import com.example.testui.model.Supervisor;
import com.example.testui.repository.AssignmentRepository;
import com.example.testui.repository.SinhVienRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TimeLineViewModel extends ViewModel {
    AssignmentRepository assignmentRepository;
    SinhVienRepository sinhVienRepository;
    MutableLiveData<PostponeProjectTerm> postponeProjectTermMutableLiveData;
    MutableLiveData<PostponeProjectTermFile> postponeProjectTermFileMutableLiveData;
    MutableLiveData<Boolean> isCancelPostponeProjectTerm;
    Context context;

    public TimeLineViewModel(Context context) {
        this.context = context;
        this.assignmentRepository = new AssignmentRepository();
        this.sinhVienRepository = new SinhVienRepository(context);
        postponeProjectTermMutableLiveData = assignmentRepository.getPostponeProjectTermMutableLiveData();
        postponeProjectTermFileMutableLiveData = assignmentRepository.getPostponeProjectTermFileMutableLiveData();
        isCancelPostponeProjectTerm = assignmentRepository.getIsCancelPostponeProjectTerm();
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

    public String safeFileName(String fileName) {
        return fileName.replaceAll("\\s+", "_").toLowerCase().replaceAll("[^a-zA-Z0-9._-]", "");
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex >= 0) {
                        result = cursor.getString(nameIndex);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public File getFileFromUri(Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        File tempFile = File.createTempFile("upload", ".tmp", context.getCacheDir());
        FileOutputStream out = new FileOutputStream(tempFile);

        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.close();
        inputStream.close();
        return tempFile;
    }

    public void submitPostponeProjectTerm(PostponeProjectTerm postponeProjectTerm) {
        assignmentRepository.submitPostponeProjectTerm(postponeProjectTerm);
    }

    public MutableLiveData<PostponeProjectTerm> getPostponeProjectTermMutableLiveData() {
        return postponeProjectTermMutableLiveData;
    }

    public void uploadPostponeFile(PostponeProjectTermFile postponeProjectTermFile) {
        assignmentRepository.uploadPostponeFile(postponeProjectTermFile);
    }

    public void cancelPostponeProjectTerm(String postpone_project_term) {
        assignmentRepository.cancelPostponeProjectTerm(postpone_project_term);
    }

    public MutableLiveData<Boolean> getIsCancelPostponeProjectTerm() {
        return isCancelPostponeProjectTerm;
    }
}
