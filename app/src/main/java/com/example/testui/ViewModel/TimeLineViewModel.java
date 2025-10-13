package com.example.testui.ViewModel;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.model.Assignment;
import com.example.testui.model.AssignmentSupervisor;
import com.example.testui.model.Supervisor;
import com.example.testui.repository.AssignmentRepository;
import com.example.testui.repository.SinhVienRepository;

import java.util.ArrayList;
import java.util.List;

public class TimeLineViewModel extends ViewModel {
    AssignmentRepository assignmentRepository;
    SinhVienRepository sinhVienRepository;

    public TimeLineViewModel(Context context) {
        this.assignmentRepository = new AssignmentRepository();
        this.sinhVienRepository = new SinhVienRepository(context);
    }

    public void loadAssignmentByStudentIdAndTermId(String studentId, String termId){
        assignmentRepository.loadAssignmentByStudentIdAndTermId(studentId, termId);
    }

    public MutableLiveData<Assignment> getAssignmentByStudentIdAndTermId(){
        return assignmentRepository.getAssignmentByStudentIdAndTermId();
    }

    public String getStudentId() {
        return sinhVienRepository.getStudentId();
    }

    public List<Supervisor> getSupervisor(List<AssignmentSupervisor> listAssignmentSupervisor) {
        List<Supervisor> listSupervisor = new ArrayList<>();
        for (AssignmentSupervisor assignmentSupervisor : listAssignmentSupervisor) {
            listSupervisor.add(assignmentSupervisor.getSupervisor());
        }
        return listSupervisor;
    }
}
