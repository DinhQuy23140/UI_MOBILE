package com.example.testui.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.model.Assignment;
import com.example.testui.model.ReportFile;
import com.example.testui.repository.AssignmentRepository;
import com.example.testui.repository.ReportFileRepository;
import com.example.testui.repository.SinhVienRepository;

import java.util.List;

public class NopBaoCaoViewModel extends ViewModel {
    Context context;
    AssignmentRepository assignmentRepository;
    SinhVienRepository sinhVienRepository;
    ReportFileRepository reportFileRepository;

    public NopBaoCaoViewModel(Context context) {
        this.context = context;
        assignmentRepository = new AssignmentRepository();
        sinhVienRepository = new SinhVienRepository(context);
        reportFileRepository = new ReportFileRepository(context);
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

    public MutableLiveData<ReportFile> getReportFileMutableLiveData() {
        return reportFileRepository.getReportFileMutableLiveData();
    }

    public void getListReportFileByProjectId(String projectId, String typeReport) {
        reportFileRepository.getListReportFileByProjectId(projectId, typeReport);
    }

    public MutableLiveData<List<ReportFile>> getListReportFileByProjectIdMutableLiveData() {
        return reportFileRepository.getListReportFileByProjectIdMutableLiveData();
    }

}
