package com.example.testui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
import com.example.testui.databinding.ActivityTraCuuHoiDongBinding;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.Assignment;
import com.example.testui.model.Council;
import com.example.testui.model.CouncilProject;
import com.example.testui.model.CouncilsMember;
import com.example.testui.model.Status;
import com.example.testui.model.Supervisor;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.DateFormatter;
import com.google.gson.Gson;

import java.util.List;

public class TraCuuHoiDongActivity extends AppCompatActivity {
    Intent intent;
    String strAssignment = "";
    Assignment assignment;
    Gson gson;
    ActivityTraCuuHoiDongBinding binding;
    CouncilsMemberAdapter councilsMemberAdapter;
    BaseGVHDAdapter baseGVHDAdapter;
    TraCuuHoiDongViewModel traCuuHoiDongViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityTraCuuHoiDongBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        loadData();
        setupRecyclerView();
    }

    void init() {
        intent = getIntent();
        gson = new Gson();
        traCuuHoiDongViewModel = new TraCuuHoiDongViewModelFactory(this).create(TraCuuHoiDongViewModel.class);
        strAssignment = intent.getStringExtra(Constants.KEY_ASSIGNMENT);
        assignment = gson.fromJson(strAssignment, Assignment.class);
        Log.d("Assignment_id", gson.toJson(assignment.getCouncil_project().getCouncil().getDepartment()));
    }

    @SuppressLint("SetTextI18n")
    void loadData() {
        Council council = assignment.getCouncil_project().getCouncil();
        binding.txtCouncilName.setText(council.getName());
        binding.txtCouncilId.setText(council.getId());
        binding.txtMajor.setText(council.getDepartment().getName());
        binding.txtCouncilDescription.setText(council.getDescription());
        List<CouncilsMember> listCouncilMember = council.getCouncil_members();
        binding.tvMemberCount.setText(listCouncilMember.size() + " thành viên");

        CouncilsMember counterArgumentMember = assignment.getCouncil_project().getCouncil_member();
        binding.tvPbName.setText(counterArgumentMember.getSupervisor().getTeacher().getUser().getFullname());
        Status status = traCuuHoiDongViewModel.getRole(counterArgumentMember.getRole());
        binding.tvPbRole.setText(status.getStrStatus());

        CouncilProject councilProject = assignment.getCouncil_project();
        binding.tvPbRoom.setText(councilProject.getRoom());
        binding.tvPbTime.setText(councilProject.getTime() + " " + DateFormatter.formatDate(councilProject.getDate()));

        binding.tvBvRoom.setText(council.getAddress());
        binding.tvBvTime.setText(DateFormatter.formatDate(council.getDate()));

        binding.txtTopicName.setText(assignment.getProject().getName());
        binding.txtTopicDescription.setText(assignment.getProject().getDescription());

    }

    void setupRecyclerView() {
        binding.rvCouncilMembers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<Supervisor> listSupervisor = traCuuHoiDongViewModel.convertListSupervisor(assignment.getCouncil_project().getCouncil().getCouncil_members());
        councilsMemberAdapter = new CouncilsMemberAdapter(this, assignment.getCouncil_project().getCouncil().getCouncil_members(), position -> {

        });
        binding.rvCouncilMembers.setAdapter(councilsMemberAdapter);

        List<Supervisor> listBaseSupervisor = traCuuHoiDongViewModel.convertListBaseSupervisor(assignment.getAssignment_supervisors());
        binding.rvSupervisor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        baseGVHDAdapter = new BaseGVHDAdapter(this, listBaseSupervisor, new OnClickItem() {
            @Override
            public void onClickItem(int position) {

            }
        });
        binding.rvSupervisor.setAdapter(baseGVHDAdapter);
    }
}