package com.example.testui.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.model.Assignment;
import com.example.testui.model.Project;
import com.example.testui.model.ProposedTopic;
import com.example.testui.model.Student;
import com.example.testui.repository.AssignmentRepository;
import com.example.testui.repository.ProjectRepository;
import com.example.testui.repository.SinhVienRepository;

import java.util.List;
import java.util.Map;

public class DangKiDeTaiViewModel extends ViewModel {
    Context context;
    ProjectRepository projectRepository;
    AssignmentRepository assignmentRepository;
    SinhVienRepository sinhVienRepository;
    MutableLiveData<Student> studentMutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<ProposedTopic>> listProposedTopic = new MutableLiveData<>();
    MutableLiveData<Boolean> isSuccessCreate;
    ProposedTopicRepository proposedTopicRepository;

    public DangKiDeTaiViewModel(Context context) {
        this.context = context;
        projectRepository = new ProjectRepository();
        assignmentRepository = new AssignmentRepository();
        sinhVienRepository = new SinhVienRepository(context);
        proposedTopicRepository = new ProposedTopicRepository();
        isSuccessCreate = projectRepository.getIsSuccessCreate();
        listProposedTopic = proposedTopicRepository.getListProposedTopic();
    }

    public void createProject(String assignmentId, Map<String, String> projectBody) {
        projectRepository.createProject(assignmentId, projectBody);
    }

    public MutableLiveData<Boolean> getIsSuccessCreate() {
        return isSuccessCreate;
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

    public void loadStudentByStudentId() {
        sinhVienRepository.getStudentById();
        studentMutableLiveData = sinhVienRepository.getStudent();
    }

    public MutableLiveData<Student> getStudent() {
        return studentMutableLiveData;
    }

    public void loadProposedTopicByAssignmentId(String assignmentId) {
        proposedTopicRepository.getProposedTopicByAssignmentId(assignmentId);
    }

    public MutableLiveData<List<ProposedTopic>> getListProposedTopic() {
        return listProposedTopic;
    }
}
