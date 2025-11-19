package com.example.testui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testui.R;
import com.example.testui.ViewModel.HomeViewModel;
import com.example.testui.ViewModelFactory.HomeViewModelFactory;
import com.example.testui.activities.UpdateInforPersonActivity;
import com.example.testui.activities.ChiTietDoAnActivity;
import com.example.testui.activities.DanhSachDotDoAnActivity;
import com.example.testui.activities.RegisterProjectTermActivity;
import com.example.testui.activities.TimeLineActivity;
import com.example.testui.databinding.ActivityHomeBinding;
import com.example.testui.databinding.FragmentHomeBinding;
import com.example.testui.model.Assignment;
import com.example.testui.model.Project;
import com.example.testui.model.ProjectTerm;
import com.example.testui.model.Status;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.AssignmentFormatter;
import com.example.testui.untilities.formatter.DateFormatter;
import com.example.testui.untilities.formatter.ProjectFormatter;
import com.example.testui.untilities.formatter.ProjectTermFormatter;
import com.example.testui.untilities.formatter.StudentFormatter;
import com.google.gson.Gson;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    HomeViewModel homeViewModel;
    ActivityHomeBinding homeBinding;
    FragmentHomeBinding fragmentHomeBinding;
    String strProjectTerm = "", studentId = "";
    Gson gson;
    Assignment assignment;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        homeViewModel.getStudentById();
        homeViewModel.loadRecentAssignment();
        loadInfStudent();
        loadRecentAssignment();
        studentId = homeViewModel.getStudentId();
        setupClick();
    }

    void init() {
        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        homeViewModel = new ViewModelProvider(this, new HomeViewModelFactory(requireContext())).get(HomeViewModel.class);
        gson = new Gson();
    }

    void setupClick() {
        fragmentHomeBinding.layoutSinhVien.setOnClickListener(click -> {
            Intent intent = new Intent(getContext(), UpdateInforPersonActivity.class);
            intent.putExtra(Constants.KEY_ID_STUDENT, studentId);
            startActivity(intent);
        });

        fragmentHomeBinding.layoutGvhd.setOnClickListener(gvhd -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new GiangVienHuongDanFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        fragmentHomeBinding.layoutDoan.setOnClickListener(click -> {
            Intent intent = new Intent(getContext(), DanhSachDotDoAnActivity.class);
            intent.putExtra(Constants.KEY_ID_STUDENT, studentId);
            startActivity(intent);
        });

        fragmentHomeBinding.layoutTopic.setOnClickListener(topicDetail -> {
            Intent intent = new Intent(getContext(), ChiTietDoAnActivity.class);
            intent.putExtra(Constants.KEY_ASSIGNMENT, gson.toJson(assignment));
            intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
            startActivity(intent);
        });

        fragmentHomeBinding.tvAssignmentDetail.setOnClickListener(assignmentDetail -> {
            Intent intent = new Intent(getContext(), TimeLineActivity.class);
            intent.putExtra(Constants.KEY_PROJECT_TERM, strProjectTerm);
            intent.putExtra(Constants.KEY_ASSIGNMENT, gson.toJson(assignment));
            startActivity(intent);
        });

        fragmentHomeBinding.layoutDangKiDoAn.setOnClickListener(dangKiDoAn -> {
            Intent intent = new Intent(getContext(), RegisterProjectTermActivity.class);
            intent.putExtra(Constants.KEY_ID_STUDENT, studentId);
            startActivity(intent);
        });
    }

    void loadInfStudent() {
        homeViewModel.getGetStudent().observe(getViewLifecycleOwner(), student -> {
            if (student != null) {
                student = StudentFormatter.format(student);
                String studentName = student.getUser().getFullname() + " - Lớp: " + student.getClass_code();
                fragmentHomeBinding.tvStudentName.setText(studentName);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    void loadRecentAssignment() {
        homeViewModel.getRecentAssignment().observe(getViewLifecycleOwner(), result -> {
            if (result.getId() != null) {
                Log.d("Result", gson.toJson(result));
                //de tai
                assignment = AssignmentFormatter.format(result);
                strProjectTerm = gson.toJson(result.getProject_term());
                Project project = ProjectFormatter.format(result.getProject());
                fragmentHomeBinding.tvTenDeTai.setText("Đề tài: " + project.getName());
                fragmentHomeBinding.tvMaDeTai.setText(project.getId());

                Map<Integer, Integer> colorStatus = homeViewModel.getBackGroundStatusAssignment();
                int backgroundColor = colorStatus.keySet().iterator().next();
                int textColor = colorStatus.get(backgroundColor);
                fragmentHomeBinding.chipTrangThai.setChipBackgroundColor(
                        ContextCompat.getColorStateList(requireContext(), backgroundColor)
                );
                fragmentHomeBinding.chipTrangThai.setTextColor(textColor);
                Status status = AssignmentFormatter.statusFormat(assignment.getStatus());
                fragmentHomeBinding.chipTrangThai.setText(status.getStrStatus());
                fragmentHomeBinding.tvLoaiDeTai.setText("Chưa có");

                //dot do an
                ProjectTerm projectTerm = ProjectTermFormatter.format(result.getProject_term());
                fragmentHomeBinding.tvStageProjectTerm.setText("Đợt " + projectTerm.getStage() + "/" + projectTerm.getAcademy_year().getYear_name());

                String formattedDateStart = DateFormatter.formatDate(projectTerm.getStart_date());
                fragmentHomeBinding.tvNgayBatDau.setText(formattedDateStart);

                String formattedDateRegister = DateFormatter.formatDate(assignment.getCreated_at());
                fragmentHomeBinding.tvNgayDangKy.setText(formattedDateRegister);

                String formattedDateEnd = DateFormatter.formatDate(projectTerm.getEnd_date());
                fragmentHomeBinding.tvNgayKetThuc.setText(formattedDateEnd);

                int progress = homeViewModel.getProcess();
                fragmentHomeBinding.progressBar.setProgress(progress);
                fragmentHomeBinding.tvProgress.setText(progress + "%");
                fragmentHomeBinding.tvCountDate.setText("Còn " + homeViewModel.getRangeDate() + " ngày");
                fragmentHomeBinding.layoutRecentAssignment.setVisibility(View.VISIBLE);
            } else {
                fragmentHomeBinding.layoutRecentAssignment.setVisibility(View.GONE);
            }
        });
    }
}