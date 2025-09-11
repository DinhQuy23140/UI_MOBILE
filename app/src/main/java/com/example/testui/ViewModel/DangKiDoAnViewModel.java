package com.example.testui.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.model.Assignment;
import com.example.testui.repository.AssignmentRepository;
import com.example.testui.repository.SinhVienRepository;

public class DangKiDoAnViewModel extends ViewModel {

    Context context;
    AssignmentRepository assignmentRepository;
    SinhVienRepository sinhVienRepository;

    public DangKiDoAnViewModel(Context context) {
        this.context = context;
        assignmentRepository = new AssignmentRepository();
        sinhVienRepository = new SinhVienRepository(context);
    }

    public void getAssignmentByStudentIdAndTermId(String studentId, String termId){
        assignmentRepository.getAssignmentByStudentIdAndTermId(studentId, termId);
    }

    public MutableLiveData<Assignment> getAssignmentMutableLiveData() {
        return assignmentRepository.getAssignmentMutableLiveData();
    }

    public String getStudentId() {
        return sinhVienRepository.getStudentId();
    }
}
