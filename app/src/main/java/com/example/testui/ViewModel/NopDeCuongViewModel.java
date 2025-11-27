package com.example.testui.ViewModel;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.R;
import com.example.testui.model.Assignment;
import com.example.testui.model.Project;
import com.example.testui.model.ReportFile;
import com.example.testui.model.Status;
import com.example.testui.repository.AssignmentRepository;
import com.example.testui.repository.ReportFileRepository;
import com.example.testui.repository.SinhVienRepository;
import com.example.testui.untilities.formatter.ProjectFormatter;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class NopDeCuongViewModel extends ViewModel {
    Context context;
    AssignmentRepository assignmentRepository;
    SinhVienRepository sinhVienRepository;
    ReportFileRepository reportFileRepository;
    MutableLiveData<Boolean> isCreateSuccess = new MutableLiveData<>();
    MutableLiveData<Assignment> assignmentWithOutlineFileMutableLiveData = new MutableLiveData<>();
    public NopDeCuongViewModel(Context context) {
        this.context = context;
        assignmentRepository = new AssignmentRepository();
        sinhVienRepository = new SinhVienRepository(context);
        reportFileRepository = new ReportFileRepository(context);
        assignmentWithOutlineFileMutableLiveData = assignmentRepository.getAssignmentWithOutline();
    }

    public void getAssignmentByStudentIdAndTermId(String studentId, String termId) {
        assignmentRepository.loadAssignmentByStudentIdAndTermId(studentId, termId);
    }

    public MutableLiveData<Assignment> getAssignmentMutableLiveData() {
        return assignmentRepository.getAssignmentByStudentIdAndTermId();
    }

    public String getStudentId(){
        return sinhVienRepository.getStudentId();
    }

    public void uploadReportFile(ReportFile reportFile) {
        reportFileRepository.uploadReportFile(reportFile);
    }

    public MutableLiveData<Boolean> getIsCreateSuccess() {
        return reportFileRepository.getIsCreateSuccess();
    }

    public MutableLiveData<ReportFile> getReportFileMutableLiveData() {
        return reportFileRepository.getReportFileMutableLiveData();
    }

    public void getListReportFileByProjectId(String projectId, String typeReport) {
        reportFileRepository.getListReportFileByProjectId(projectId, typeReport);
    }

    public MutableLiveData<List<ReportFile>> getListReportFileByProjectIdMutableLiveData() {
        return reportFileRepository.getListReportFileByProjectIdMutableLiveData();
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

    public String safeFileName(String fileName) {
        return fileName.replaceAll("\\s+", "_").toLowerCase().replaceAll("[^a-zA-Z0-9._-]", "");
    }

    public void loadAssignmentWithOutlineFile(String studentId, String termId) {
        assignmentRepository.loadAssignmentWithOutlineFileByStudentIdAndProjectTermId(studentId, termId);
    }

    public MutableLiveData<Assignment> getAssignmentWithOutlineFileMutableLiveData() {
        return assignmentWithOutlineFileMutableLiveData;
    }

    public Status loadStatusOutline(Assignment assignment) {
        Project project = ProjectFormatter.format(assignment.getProject());
        List<ReportFile> reportFileList = project.getReport_files();
        Log.d("ListReportFIle", new Gson().toJson(assignment.getProject()));
        if (reportFileList != null && !reportFileList.isEmpty()) {
            return new Status(R.drawable.rounded_button_green, "Đã nộp");
        } else {
            return new Status(R.drawable.bg_status_pending, "Chưa nộp");
        }
    }
}
