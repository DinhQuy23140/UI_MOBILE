package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.ViewModel.TraCuuPhanBienViewModel;
import com.example.testui.ViewModelFactory.TraCuuPhanBienViewModelFactory;
import com.example.testui.adapter.BaseGVHDAdapter;
import com.example.testui.adapter.CouncilsMemberAdapter;
import com.example.testui.databinding.ActivityTraCuuPhanBienBinding;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.Assignment;
import com.example.testui.model.AssignmentSupervisor;
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
    ActivityTraCuuPhanBienBinding binding;
    Intent intent;
    String strAssignemnt;
    Assignment assignment;
    BaseGVHDAdapter baseGVHDAdapter;
    CouncilsMemberAdapter councilsMemberAdapter;
    Gson gson;
    Project project;
    TraCuuPhanBienViewModel traCuuPhanBienViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityTraCuuPhanBienBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        setupClick();
        setupRecyclerView();
        loadData();
    }

    void init() {
        intent = getIntent();
        gson = new Gson();
        strAssignemnt = intent.getStringExtra(Constants.KEY_ASSIGNMENT);
        assignment = AssignmentFormatter.format(gson.fromJson(strAssignemnt, Assignment.class));
        traCuuPhanBienViewModel = new TraCuuPhanBienViewModelFactory(this).create(TraCuuPhanBienViewModel.class);
    }

    @SuppressLint("SetTextI18n")
    void loadData() {
        project = ProjectFormatter.format(assignment.getProject());
        binding.tvProjectName.setText(project.getName());
        List<Supervisor> listAssignmentSupervisor = traCuuPhanBienViewModel.convertListBaseSupervisor(assignment.getAssignment_supervisors());
        baseGVHDAdapter.updateData(listAssignmentSupervisor);
        binding.txtProjectDescription.setText(project.getDescription());

        CouncilProject councilProject = CouncilProjectFormatter.format(assignment.getCouncil_project());
        CouncilsMember counterArgumentMember = CouncilsMemberFormatter.format(councilProject.getCouncil_member());
        Supervisor supervisor = SupervisorFormatter.format(counterArgumentMember.getSupervisor());
        Teacher teacher = TeacherFormatter.format(supervisor.getTeacher());
        User user = UserFormatter.format(teacher.getUser());
        binding.tvPbName.setText(user.getFullname());
        Status status = traCuuPhanBienViewModel.getRole(counterArgumentMember.getRole());
        binding.tvPbRole.setText(status.getStrStatus());
//        binding.txtReviewScore.setText(assignment.getCouncil_project().getReview_score());

        binding.txtReviewScore.setText(councilProject.getReview_score());
        binding.txtReviewScoreDate.setText(DateFormatter.formatDate(councilProject.getUpdated_at()));

        Council council = CouncilFormatter.format(councilProject.getCouncil());
        binding.txtCouncilName.setText(council.getName());
        binding.txtCouncilId.setText(council.getId());
        Department department = DepartmentFormatter.format(council.getDepartment());
        binding.txtMajor.setText(department.getName());
        binding.txtCouncilDescription.setText(council.getDescription());

        List<CouncilsMember> listCouncilMember = council.getCouncil_members();
        int count = listCouncilMember != null ? listCouncilMember.size() : 0;
        binding.tvMemberCount.setText(count + " thành viên");
        if (count > 0 ) {
            councilsMemberAdapter.updateData(listCouncilMember);
        }
    }

    void setupRecyclerView() {
        binding.rvSupervisor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        baseGVHDAdapter = new BaseGVHDAdapter(this, new ArrayList<>(), position -> {

        });
        binding.rvSupervisor.setAdapter(baseGVHDAdapter);

        binding.rvCouncilMembers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        councilsMemberAdapter = new CouncilsMemberAdapter(this, new ArrayList<>(), new OnClickItem() {
            @Override
            public void onClickItem(int position) {

            }
        });
        binding.rvCouncilMembers.setAdapter(councilsMemberAdapter);
    }

    void setupClick() {

    }

}