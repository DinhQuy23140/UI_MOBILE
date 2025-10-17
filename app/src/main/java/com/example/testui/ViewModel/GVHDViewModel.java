package com.example.testui.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.model.Assignment;
import com.example.testui.repository.AssignmentRepository;
import com.example.testui.repository.SinhVienRepository;
import com.example.testui.model.Supervisor;
import com.example.testui.model.Teacher;

import java.util.List;

public class GVHDViewModel extends ViewModel {
    SinhVienRepository loginRepository;
    MutableLiveData<List<Teacher>> listTeacher = new MutableLiveData<>();
    MutableLiveData<List<Supervisor>> listSupervisor = new MutableLiveData<>();
    MutableLiveData<List<Assignment>> listAssignment = new MutableLiveData<>();
    AssignmentRepository assignmentRepository;
    public GVHDViewModel(Context context) {
        loginRepository = new SinhVienRepository(context);
        assignmentRepository = new AssignmentRepository();
    }

    public MutableLiveData<List<Teacher>> getListTeacher() {
        return listTeacher;
    }

    public void getGVHD() {
        loginRepository.getGVHD();
        listSupervisor = loginRepository.getListSupervisor();
    }

    public void loadAssignmentsByTeacherId(String teacherId) {
        assignmentRepository.getAssignmentsByTeacherId(teacherId);
        listAssignment = assignmentRepository.getListAssignment();
    }

    public MutableLiveData<List<Supervisor>> getListSupervisor() {
        return listSupervisor;
    }

    public MutableLiveData<List<Assignment>> getListAssignment() {
        return listAssignment;
    }
}
