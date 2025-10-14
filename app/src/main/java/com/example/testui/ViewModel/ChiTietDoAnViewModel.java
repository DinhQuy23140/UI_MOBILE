package com.example.testui.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.model.Assignment;
import com.example.testui.repository.AssignmentRepository;
import com.example.testui.repository.SinhVienRepository;

public class ChiTietDoAnViewModel extends ViewModel {

    Context context;
    MutableLiveData<Assignment> assignmentByStudentIdAndTermId = new MutableLiveData<>();
    SinhVienRepository sinhVienRepository;
    AssignmentRepository assignmentRepository;

    public ChiTietDoAnViewModel(Context context) {
        this.context = context;
        sinhVienRepository = new SinhVienRepository(context);
        assignmentRepository = new AssignmentRepository();
    }

    public void loadAssignmentByStudentIdAndTermId(String studentId, String termId){
        assignmentRepository.loadAssignmentByStudentIdAndTermId(studentId, termId);
        assignmentByStudentIdAndTermId = assignmentRepository.getAssignmentByStudentIdAndTermId();
    }

    public MutableLiveData<Assignment> getAssignmentByStudentIdAndTermId() {
        return assignmentByStudentIdAndTermId;
    }

    public String getIdStudent() {
        return sinhVienRepository.getStudentId();
    }
}
