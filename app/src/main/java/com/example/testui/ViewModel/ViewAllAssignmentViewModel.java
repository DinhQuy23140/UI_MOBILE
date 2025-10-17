package com.example.testui.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.model.Assignment;
import com.example.testui.repository.AssignmentRepository;

import java.util.List;

public class ViewAllAssignmentViewModel extends ViewModel {
    AssignmentRepository assignmentRepository;
    MutableLiveData<List<Assignment>> listAssignment = new MutableLiveData<>();
    public ViewAllAssignmentViewModel() {
        assignmentRepository = new AssignmentRepository();
    }


    public void loadAssignments(String teacherId) {
        assignmentRepository.getAssignmentsByTeacherId(teacherId);
        listAssignment = assignmentRepository.getListAssignment();
    }

    public MutableLiveData<List<Assignment>> getListAssignment() {
        return listAssignment;
    }
}
