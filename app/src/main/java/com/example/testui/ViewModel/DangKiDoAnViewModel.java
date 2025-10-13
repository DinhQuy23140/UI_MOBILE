package com.example.testui.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.model.Assignment;
import com.example.testui.model.Student;
import com.example.testui.repository.AssignmentRepository;
import com.example.testui.repository.SinhVienRepository;

public class DangKiDoAnViewModel extends ViewModel {

    Context context;
    AssignmentRepository assignmentRepository;
    SinhVienRepository sinhVienRepository;
    MutableLiveData<Student> infStudent = new MutableLiveData<>();
    MutableLiveData<Assignment> currentAssignment = new MutableLiveData<>();

    public DangKiDoAnViewModel(Context context) {
        this.context = context;
        assignmentRepository = new AssignmentRepository();
        sinhVienRepository = new SinhVienRepository(context);
    }

    public void getAssignmentByStudentIdAndTermId(String studentId, String termId){
        assignmentRepository.loadAssignmentByStudentIdAndTermId(studentId, termId);
    }

    public MutableLiveData<Assignment> getAssignmentMutableLiveData() {
        return assignmentRepository.getAssignmentByStudentIdAndTermId();
    }

    public String getStudentId() {
        return sinhVienRepository.getStudentId();
    }

    public void loadInfStudent() {
        sinhVienRepository.getStudentById();
    }

    public MutableLiveData<Student> getStudentById() {
        return sinhVienRepository.getStudent();
    }
}
