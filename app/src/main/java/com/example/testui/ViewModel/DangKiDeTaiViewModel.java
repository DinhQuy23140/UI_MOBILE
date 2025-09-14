package com.example.testui.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.model.Assignment;
import com.example.testui.model.Project;
import com.example.testui.repository.AssignmentRepository;
import com.example.testui.repository.ProjectRepository;

import java.util.Map;

public class DangKiDeTaiViewModel extends ViewModel {
    Context context;
    ProjectRepository projectRepository;
    AssignmentRepository assignmentRepository;

    public DangKiDeTaiViewModel(Context context) {
        this.context = context;
        projectRepository = new ProjectRepository();
        assignmentRepository = new AssignmentRepository();
    }

    public void createProject(Map<String, String> projectBody) {
        projectRepository.createProject(projectBody);
    }

    public MutableLiveData<Project> getResponseCreateProject() {
        return projectRepository.getProjectMutableLiveData();
    }

    public void updateProjectIdAssignmentByAssIdAndProId(String assignmentId, String projectId) {
        assignmentRepository.updateProjectIdAssignmentByAssIdAndProId(assignmentId, projectId);
    }

    public MutableLiveData<Assignment> getResponseUpdateAssignment() {
        return assignmentRepository.getAssignmenUpdatetMutableLiveData();
    }
}
