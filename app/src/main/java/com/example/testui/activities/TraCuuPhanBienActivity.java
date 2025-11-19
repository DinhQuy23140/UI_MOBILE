package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.ViewModel.TraCuuHoiDongViewModel;
import com.example.testui.ViewModelFactory.TraCuuHoiDongViewModelFactory;
import com.example.testui.adapter.BaseGVHDAdapter;
import com.example.testui.adapter.CouncilsMemberAdapter;
import com.example.testui.databinding.ActivityTraCuuPhanBien2Binding;
import com.example.testui.databinding.ActivityTraCuuPhanBienBinding;
import com.example.testui.model.Assignment;
import com.example.testui.model.Council;
import com.example.testui.model.CouncilProject;
import com.example.testui.model.CouncilsMember;
import com.example.testui.model.Department;
import com.example.testui.model.Project;
import com.example.testui.model.Status;
import com.example.testui.model.Supervisor;
import com.example.testui.model.Teacher;
import com.example.testui.model.User;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.AssignmentFormatter;
import com.example.testui.untilities.formatter.CouncilFormatter;
import com.example.testui.untilities.formatter.CouncilProjectFormatter;
import com.example.testui.untilities.formatter.CouncilsMemberFormatter;
import com.example.testui.untilities.formatter.DateFormatter;
import com.example.testui.untilities.formatter.DepartmentFormatter;
import com.example.testui.untilities.formatter.ProjectFormatter;
import com.example.testui.untilities.formatter.SupervisorFormatter;
import com.example.testui.untilities.formatter.TeacherFormatter;
import com.example.testui.untilities.formatter.UserFormatter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TraCuuPhanBienActivity extends AppCompatActivity {

    Intent intent;
    Gson gson;
    TraCuuHoiDongViewModel traCuuHoiDongViewModel;
    String strAssignment = "";
    Assignment assignment;
    Council council;
    ActivityTraCuuPhanBien2Binding binding;
    List<CouncilsMember> listCouncilMember;
    CouncilsMemberAdapter councilsMemberAdapter;
    List<Supervisor> listBaseSupervisor;
    BaseGVHDAdapter baseGVHDAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityTraCuuPhanBien2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        setupRecyclerView();
        loadData();
        loadDataRecyclerView();
    }

    void init() {
        intent = getIntent();
        gson = new Gson();
        traCuuHoiDongViewModel = new TraCuuHoiDongViewModelFactory(this).create(TraCuuHoiDongViewModel.class);
        strAssignment = intent.getStringExtra(Constants.KEY_ASSIGNMENT);
        assignment = AssignmentFormatter.format(gson.fromJson(strAssignment, Assignment.class));
    }

    @SuppressLint("SetTextI18n")
    void loadData() {
        CouncilProject councilProject = CouncilProjectFormatter.format(assignment.getCouncil_project());
        council = CouncilFormatter.format(councilProject.getCouncil());
        binding.txtCouncilName.setText(council.getName());
        binding.txtCouncilId.setText(council.getId());
        Department department = DepartmentFormatter.format(council.getDepartment());
        binding.txtMajor.setText(department.getName());
        binding.txtCouncilDescription.setText(council.getDescription());
        listCouncilMember = council.getCouncil_members();
        String text = (listCouncilMember != null ? listCouncilMember.size() : 0) + " thành viên";
        binding.tvMemberCount.setText(text);

        CouncilsMember counterArgumentMember = CouncilsMemberFormatter.format(councilProject.getCouncil_member());
        Supervisor supervisor = SupervisorFormatter.format(counterArgumentMember.getSupervisor());
        Teacher teacher = TeacherFormatter.format(supervisor.getTeacher());
        User user = UserFormatter.format(teacher.getUser());
        binding.tvPbName.setText(user.getFullname());
        Status status = traCuuHoiDongViewModel.getRole(counterArgumentMember.getRole());
        binding.tvPbRole.setText(status.getStrStatus());

        binding.tvPbRoom.setText(councilProject.getRoom());
        binding.tvPbTime.setText(councilProject.getTime() + " " + DateFormatter.formatDate(councilProject.getDate()));

        Project project = ProjectFormatter.format(assignment.getProject());
        binding.txtTopicName.setText(project.getName());
        binding.txtTopicDescription.setText(project.getDescription());

    }

    void setupRecyclerView() {
        binding.rvCouncilMembers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        councilsMemberAdapter = new CouncilsMemberAdapter(this, new ArrayList<>(), position -> {

        });
        binding.rvCouncilMembers.setAdapter(councilsMemberAdapter);

        listBaseSupervisor = traCuuHoiDongViewModel.convertListBaseSupervisor(assignment.getAssignment_supervisors());
        binding.rvSupervisor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        baseGVHDAdapter = new BaseGVHDAdapter(this, listBaseSupervisor, position -> {

        });
        binding.rvSupervisor.setAdapter(baseGVHDAdapter);
    }

    void loadDataRecyclerView() {
        if (listCouncilMember != null && !listCouncilMember.isEmpty()) {
            councilsMemberAdapter.updateData(listCouncilMember);
            binding.rvCouncilMembers.setVisibility(View.VISIBLE);
            binding.tvEmptyCouncilMember.setVisibility(View.GONE);
        } else {
            binding.rvCouncilMembers.setVisibility(View.GONE);
            binding.tvEmptyCouncilMember.setVisibility(View.VISIBLE);
        }

        if (!listBaseSupervisor.isEmpty()) {
            baseGVHDAdapter.updateData(listBaseSupervisor);
            binding.rvSupervisor.setVisibility(View.VISIBLE);
            binding.tvEmptySupervisor.setVisibility(View.GONE);
        } else {
            binding.rvSupervisor.setVisibility(View.GONE);
            binding.tvEmptySupervisor.setVisibility(View.VISIBLE);
        }
    }
}