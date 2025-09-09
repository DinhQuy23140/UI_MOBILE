package com.example.testui.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.model.Assignment;
import com.example.testui.repository.SinhVienRepository;

public class ChiTietDoAnViewModel extends ViewModel {

    Context context;
    MutableLiveData<Assignment> assignmentMutableLiveData = new MutableLiveData<>();
    SinhVienRepository sinhVienRepository;

    public ChiTietDoAnViewModel(Context context) {
        this.context = context;
        sinhVienRepository = new SinhVienRepository(context);
    }

    public void getChiTietDoAn(String id){
        sinhVienRepository.getAssignmentByStudentId(id);
        assignmentMutableLiveData = sinhVienRepository.getAssignmentByIdStudent();
    }

    public MutableLiveData<Assignment> getAssignmentMutableLiveData() {
        return assignmentMutableLiveData;
    }

    public String getIdStudent() {
        return sinhVienRepository.getUserId();
    }
}
