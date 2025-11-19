package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.ViewModel.TraCuuBaoVeViewModel;
import com.example.testui.ViewModelFactory.TraCuuBaoVeViewModelFactory;
import com.example.testui.adapter.BaseGVHDAdapter;
import com.example.testui.adapter.CouncilMemberScoreAdapter;
import com.example.testui.adapter.CouncilsMemberAdapter;
import com.example.testui.databinding.ActivityTraCuuBaoVeBinding;
import com.example.testui.databinding.AddItemLogBinding;
import com.example.testui.databinding.DialogNopBaoCaoBinding;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.Assignment;
import com.example.testui.model.Council;
import com.example.testui.model.CouncilProject;
import com.example.testui.model.CouncilProjectDefence;
import com.example.testui.model.CouncilsMember;
import com.example.testui.model.Department;
import com.example.testui.model.Project;
import com.example.testui.model.Supervisor;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.AssignmentFormatter;
import com.example.testui.untilities.formatter.CouncilFormatter;
import com.example.testui.untilities.formatter.CouncilProjectFormatter;
import com.example.testui.untilities.formatter.DateFormatter;
import com.example.testui.untilities.formatter.DepartmentFormatter;
import com.example.testui.untilities.formatter.ProjectFormatter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TraCuuBaoVeActivity extends AppCompatActivity {
    ActivityTraCuuBaoVeBinding binding;
    Gson gson;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    Assignment assignment;
    String strAssignment;
    Intent intent;
    DialogNopBaoCaoBinding dialogNopBaoCaoBinding;
    CouncilMemberScoreAdapter councilMemberScoreAdapter;
    CouncilsMemberAdapter councilsMemberAdapter;
    BaseGVHDAdapter baseGVHDAdapter;
    TraCuuBaoVeViewModel traCuuBaoVeViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityTraCuuBaoVeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        createDialog();
        setupRecyclerView();
        loadData();
        setupClick();
    }

    void init() {
        gson = new Gson();
        intent = getIntent();
        strAssignment = intent.getStringExtra(Constants.KEY_ASSIGNMENT);
        assignment = AssignmentFormatter.format(gson.fromJson(strAssignment, Assignment.class));
        traCuuBaoVeViewModel = new TraCuuBaoVeViewModelFactory(this).create(TraCuuBaoVeViewModel.class);
    }

    void setupRecyclerView() {
        binding.rvSupervisor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        baseGVHDAdapter = new BaseGVHDAdapter(this, new ArrayList<>(), position -> {

        });
        binding.rvSupervisor.setAdapter(baseGVHDAdapter);

        binding.rvCouncilMemberScore.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        councilMemberScoreAdapter = new CouncilMemberScoreAdapter(this, new ArrayList<>(), new OnClickItem() {
            @Override
            public void onClickItem(int position) {

            }
        });
        binding.rvCouncilMemberScore.setAdapter(councilMemberScoreAdapter);

        binding.rvCouncilMember.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        councilsMemberAdapter = new CouncilsMemberAdapter(this, new ArrayList<>(), new OnClickItem() {
            @Override
            public void onClickItem(int position) {

            }
        });
        binding.rvCouncilMember.setAdapter(councilsMemberAdapter);
    }

    @SuppressLint("SetTextI18n")
    void loadData() {
        Project project = ProjectFormatter.format(assignment.getProject());
        binding.tvProjectName.setText(project.getName());
        binding.txtProjectDescription.setText(project.getDescription());
        CouncilProject councilProject = CouncilProjectFormatter.format(assignment.getCouncil_project());
        Council council = CouncilFormatter.format(councilProject.getCouncil());
        binding.txtCouncilName.setText(council.getName());
        binding.txtCouncilId.setText(council.getId());
        Department department = DepartmentFormatter.format(council.getDepartment());
        binding.txtMajor.setText(department.getName());
        binding.txtCouncilDescription.setText(council.getDescription());
        binding.tvBvRoom.setText(council.getAddress());
        binding.tvBvTime.setText(DateFormatter.formatDate(council.getDate()));
        traCuuBaoVeViewModel.councilDisplay.observe(this, model -> {
            Council resultCouncil = CouncilFormatter.format(model);
            binding.txtCouncilName.setText(resultCouncil.getName());
            binding.txtCouncilId.setText(resultCouncil.getId());
            Department departmentResult = DepartmentFormatter.format(resultCouncil.getDepartment());
            binding.txtMajor.setText(departmentResult.getName());
            binding.txtCouncilDescription.setText(resultCouncil.getDescription());
            binding.tvBvRoom.setText(resultCouncil.getAddress());
            binding.tvBvTime.setText(resultCouncil.getDate());
        });

        List<Supervisor> listBaseSupervisor = traCuuBaoVeViewModel.convertListBaseSupervisor(assignment.getAssignment_supervisors());
        baseGVHDAdapter.updateData(listBaseSupervisor);

        List<CouncilProjectDefence> listCouncilProjectDefence = councilProject.getCouncil_project_defences() != null ?
                councilProject.getCouncil_project_defences() : new ArrayList<>();
        if (listCouncilProjectDefence.isEmpty()) {
            binding.tvEmptyCouncilProjectDefences.setVisibility(View.VISIBLE);
        } else {
            councilMemberScoreAdapter.updateData(listCouncilProjectDefence);
        }

        List<CouncilsMember> listCouncilMember = council.getCouncil_members();
        int count = listCouncilMember != null ? listCouncilMember.size() : 0;
        binding.tvMemberCount.setText(count + " thành viên");
        if (count == 0) {
            binding.tvEmptyCouncilMember.setVisibility(View.VISIBLE);
        } else {
            councilsMemberAdapter.updateData(listCouncilMember);
        }
    }

    void createDialog() {
        builder = new AlertDialog.Builder(TraCuuBaoVeActivity.this, R.style.FullScreenDialogTheme);
        dialogNopBaoCaoBinding = DialogNopBaoCaoBinding.inflate(getLayoutInflater());
        builder.setView(dialogNopBaoCaoBinding.getRoot());
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
    }

    void setupClick() {
        binding.btnSubmitReportCard.setOnClickListener(v -> {
            dialog.show();
            if (dialog.getWindow() != null) {
                dialog.getWindow().setLayout(
                        (int) (getResources().getDisplayMetrics().widthPixels * 0.95),
                        (int) (getResources().getDisplayMetrics().heightPixels * 0.9)
                );
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
        });

        dialogNopBaoCaoBinding.btnSubmit.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialogNopBaoCaoBinding.btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }
}