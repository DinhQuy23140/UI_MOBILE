package com.example.testui.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.model.ProjectTerm;
import com.example.testui.repository.DotDoAnRepository;

import java.util.List;

public class DotDoAnViewModel extends ViewModel {

    Context context;
    DotDoAnRepository dotDoAnRepository;
    MutableLiveData<List<ProjectTerm>> listDotDoAn;
    public DotDoAnViewModel(Context context) {
        this.context = context;
        this.dotDoAnRepository = new DotDoAnRepository();
    }

    public void getListDoAnByStudentId(String studentId) {
        dotDoAnRepository.getListDoAnByStudentId(studentId);
        listDotDoAn = dotDoAnRepository.getListDotDoAn();
    }

    public MutableLiveData<List<ProjectTerm>> getListDotDoAn() {
        return listDotDoAn;
    }
}
