package com.example.testui.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.repository.SinhVienRepository;
import com.example.testui.model.Supervisor;
import com.example.testui.model.Teacher;

import java.util.List;

public class GVHDViewModel extends ViewModel {
    SinhVienRepository loginRepository;
    MutableLiveData<List<Teacher>> listTeacher = new MutableLiveData<>();
    MutableLiveData<List<Supervisor>> listSupervisor = new MutableLiveData<>();
    public GVHDViewModel(Context context) {
        loginRepository = new SinhVienRepository(context);
    }

    public MutableLiveData<List<Teacher>> getListTeacher() {
        return listTeacher;
    }

    public void getGVHD() {
        loginRepository.getGVHD();
        listSupervisor = loginRepository.getListSupervisor();
    }

    public MutableLiveData<List<Supervisor>> getListSupervisor() {
        return listSupervisor;
    }
}
