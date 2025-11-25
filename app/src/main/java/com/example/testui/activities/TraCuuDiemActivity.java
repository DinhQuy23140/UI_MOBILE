package com.example.testui.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.ViewModel.TraCuuDiemViewModel;
import com.example.testui.ViewModelFactory.TraCuuDiemViewModelFactory;
import com.example.testui.adapter.BaseGVHDAdapter;
import com.example.testui.adapter.CouncilMemberScoreAdapter;
import com.example.testui.adapter.ScoreSupervisorAdapter;
import com.example.testui.databinding.ActivityTraCuuDiemBinding;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.Assignment;
import com.example.testui.model.AssignmentSupervisor;
import com.example.testui.model.Council;
import com.example.testui.model.CouncilProject;
import com.example.testui.model.CouncilProjectDefence;
import com.example.testui.model.CouncilsMember;
import com.example.testui.model.Project;
import com.example.testui.model.Status;
import com.example.testui.model.Supervisor;
import com.example.testui.model.Teacher;
import com.example.testui.model.User;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.AssignmentFormatter;
import com.example.testui.untilities.formatter.CouncilFormatter;
import com.example.testui.untilities.formatter.CouncilMemberFormatter;
import com.example.testui.untilities.formatter.CouncilProjectFormatter;
import com.example.testui.untilities.formatter.ProjectFormatter;
import com.example.testui.untilities.formatter.SupervisorFormatter;
import com.example.testui.untilities.formatter.TeacherFormatter;
import com.example.testui.untilities.formatter.UserFormatter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TraCuuDiemActivity extends AppCompatActivity {

    ActivityTraCuuDiemBinding binding;
    String strAssignment = "";
    Assignment assignment;
    Project project;
    Council council;
    CouncilProject councilProject;
    BaseGVHDAdapter baseGVHDAdapter;
    TraCuuDiemViewModel traCuuDiemViewModel;
    CouncilMemberScoreAdapter councilMemberScoreAdapter;
    ScoreSupervisorAdapter scoreSupervisorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityTraCuuDiemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        setupRecyclerView();
        loadData();
    }

    void init() {
        traCuuDiemViewModel = new ViewModelProvider(this, new TraCuuDiemViewModelFactory(this)).get(TraCuuDiemViewModel.class);
        strAssignment = getIntent().getStringExtra(Constants.KEY_ASSIGNMENT);
        assignment = AssignmentFormatter.format(new Gson().fromJson(strAssignment, Assignment.class));
        project = ProjectFormatter.format(assignment.getProject());
        councilProject = CouncilProjectFormatter.format(assignment.getCouncil_project());
        council = CouncilFormatter.format(councilProject.getCouncil());
    }

    void setupRecyclerView() {
        baseGVHDAdapter = new BaseGVHDAdapter(this, new ArrayList<>(), new OnClickItem() {
            @Override
            public void onClickItem(int position) {

            }
        });

        binding.rvSupevisor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvSupevisor.setAdapter(baseGVHDAdapter);

        scoreSupervisorAdapter = new ScoreSupervisorAdapter(this, new ArrayList<>());
        binding.rvScoreSupervisor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvScoreSupervisor.setAdapter(scoreSupervisorAdapter);

        councilMemberScoreAdapter = new CouncilMemberScoreAdapter(this, new ArrayList<>(), new OnClickItem() {
            @Override
            public void onClickItem(int position) {

            }
        });
        binding.rvMemberCouncils.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvMemberCouncils.setAdapter(councilMemberScoreAdapter);
    }

    void loadData() {
        binding.tvProjectName.setText(project.getName());
        List<AssignmentSupervisor> listAssignmentSupervisor = assignment.getAssignment_supervisors();
        if (listAssignmentSupervisor != null && !listAssignmentSupervisor.isEmpty()) {
            baseGVHDAdapter.updateData(traCuuDiemViewModel.getSupervisor(listAssignmentSupervisor));
        }

        binding.txtProjectDescription.setText(project.getDescription());

        scoreSupervisorAdapter.updateData(listAssignmentSupervisor);

        councilProject = CouncilProjectFormatter.format(assignment.getCouncil_project());
        CouncilsMember council_memberReview = CouncilMemberFormatter.format(councilProject.getCouncil_member());
        Supervisor supervisorReview = SupervisorFormatter.format(council_memberReview.getSupervisor());
        Teacher teacherReview = TeacherFormatter.format(supervisorReview.getTeacher());
        User user = UserFormatter.format(teacherReview.getUser());
        binding.txtAdvisorName.setText(user.getFullname());
        binding.txtAdvisorScore.setText(councilProject.getReview_score());
        binding.txtAdvisorComment.setText(councilProject.getComments());
        binding.txtAdvisorScoreDate.setText(councilProject.getUpdated_at());

        List<CouncilProjectDefence> listCouncilMember = councilProject.getCouncil_project_defences();
        binding.tvCouncilCount.setText(listCouncilMember.size() + " thành viên");
        if (listCouncilMember != null && !listCouncilMember.isEmpty()) {
            councilMemberScoreAdapter.updateData(listCouncilMember);
        }

        double totalScore = traCuuDiemViewModel.totalScore(assignment);
        Status status = traCuuDiemViewModel.statusScore(assignment);
        binding.txtTotalScore.setText(String.valueOf(totalScore));
        binding.txtStatus.setText(status.getStrStatus());
        binding.txtStatus.setBackground(getDrawable(status.getBackgroundColor()));
    }
}