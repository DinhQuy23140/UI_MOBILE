package com.example.testui.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.model.Assignment;
import com.example.testui.model.AssignmentSupervisor;
import com.example.testui.model.Supervisor;
import com.example.testui.repository.AssignmentSupervisorRepository;
import com.example.testui.repository.GiangVienRepository;
import com.example.testui.repository.SinhVienRepository;

import java.util.List;

public class DangKiGVHDViewModel extends ViewModel {

    GiangVienRepository giangVienRepository;
    SinhVienRepository sinhVienRepository;
    AssignmentSupervisorRepository assignmentSupervisorRepository;
    MutableLiveData<Assignment> assignmentMutableLiveData = new MutableLiveData<>();

    public DangKiGVHDViewModel(Context context) {
        giangVienRepository = new GiangVienRepository();
        sinhVienRepository = new SinhVienRepository(context);
        assignmentSupervisorRepository = new AssignmentSupervisorRepository();
    }

    public void getAllSupervisorByProjectTermId(String projectTermId){
        giangVienRepository.getAllSupervisorByProjectTermId(projectTermId);
    }

    public MutableLiveData<List<Supervisor>> getListSupervisor() {
        return giangVienRepository.getListSupervisor();
    }

    public String getStudentId() {
        return sinhVienRepository.getStudentId();
    }

    public void getAssignmentByStudentIdAndTermId(String studentId, String termId) {
        assignmentSupervisorRepository.getAssigmentByStudentIdAndTermId(studentId, termId);
    }

    public MutableLiveData<Assignment> getAssignmentMutableLiveData() {
        return assignmentSupervisorRepository.getAssignmentMutableLiveData();
    }

    public void createAssignmentSupervisor(AssignmentSupervisor assignmentSupervisor){
        assignmentSupervisorRepository.createAssignmentSupervisor(assignmentSupervisor);
    }

    public MutableLiveData<Boolean> getIsCreateSuccess() {
        return assignmentSupervisorRepository.getIsCreateSuccess();
    }
}
