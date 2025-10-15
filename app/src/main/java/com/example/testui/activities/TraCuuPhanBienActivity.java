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
import com.example.testui.model.CouncilsMember;
import com.example.testui.model.Status;
import com.example.testui.model.Supervisor;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.DateFormatter;
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
        assignment = gson.fromJson(strAssignemnt, Assignment.class);
        traCuuPhanBienViewModel = new TraCuuPhanBienViewModelFactory(this).create(TraCuuPhanBienViewModel.class);
    }

    @SuppressLint("SetTextI18n")
    void loadData() {
        binding.tvProjectName.setText(assignment.getProject().getName());
        List<Supervisor> listAssignmentSupervisor = traCuuPhanBienViewModel.convertListBaseSupervisor(assignment.getAssignment_supervisors());
        baseGVHDAdapter.updateData(listAssignmentSupervisor);
        binding.txtProjectDescription.setText(assignment.getProject().getDescription());

        CouncilsMember counterArgumentMember = assignment.getCouncil_project().getCouncil_member();
        binding.tvPbName.setText(counterArgumentMember.getSupervisor().getTeacher().getUser().getFullname());
        Status status = traCuuPhanBienViewModel.getRole(counterArgumentMember.getRole());
        binding.tvPbRole.setText(status.getStrStatus());
//        binding.txtReviewScore.setText(assignment.getCouncil_project().getReview_score());

        binding.txtReviewScore.setText(assignment.getCouncil_project().getReview_score());
        binding.txtReviewScoreDate.setText(DateFormatter.formatDate(assignment.getCouncil_project().getUpdated_at()));

        Council council = assignment.getCouncil_project().getCouncil();
        binding.txtCouncilName.setText(council.getName());
        binding.txtCouncilId.setText(council.getId());
        binding.txtMajor.setText(council.getDepartment().getName());
        binding.txtCouncilDescription.setText(council.getDescription());

        binding.tvMemberCount.setText(council.getCouncil_members().size() + " thành viên");
        List<CouncilsMember> listCouncilMember = council.getCouncil_members();
        councilsMemberAdapter.updateData(listCouncilMember);
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