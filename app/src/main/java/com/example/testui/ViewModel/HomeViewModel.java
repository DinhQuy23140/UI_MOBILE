package com.example.testui.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.repository.SinhVienRepository;
import com.example.testui.model.Student;

public class HomeViewModel extends ViewModel {
    MutableLiveData<Student> getStudent = new MutableLiveData<>();
    MutableLiveData<String> studentId = new MutableLiveData<>();
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
}
