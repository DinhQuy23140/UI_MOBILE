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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.testui.R;
import com.example.testui.ViewModel.HomeViewModel;
import com.example.testui.ViewModelFactory.HomeViewModelFactory;
import com.example.testui.activities.CapNhapThongTinActivity;
import com.example.testui.activities.ChiTietDoAnActivity;
import com.example.testui.activities.DanhSachDotDoAnActivity;
import com.example.testui.activities.TimeLineActivity;
import com.example.testui.databinding.ActivityHomeBinding;
import com.example.testui.databinding.FragmentHomeBinding;
import com.example.testui.untilities.Constants;
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
    String projectTermId = "", studentId = "";
    Gson gson;

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
            Intent intent = new Intent(getContext(), CapNhapThongTinActivity.class);
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

        fragmentHomeBinding.lnProgress.setOnClickListener(click -> {
            Intent intent = new Intent(getContext(), ChiTietDoAnActivity.class);
            startActivity(intent);
        });

        fragmentHomeBinding.layoutTopic.setOnClickListener(topicDetail -> {
            Intent intent = new Intent(getContext(), ChiTietDoAnActivity.class);
            startActivity(intent);
        });

        fragmentHomeBinding.tvAssignmentDetail.setOnClickListener(assignmentDetail -> {
            Intent intent = new Intent(getContext(), TimeLineActivity.class);
            intent.putExtra(Constants.KEY_PROJECT_TERM, projectTermId);
            startActivity(intent);
        });
    }

    void loadInfStudent() {
        homeViewModel.getStudentById();
        homeViewModel.getGetStudent().observe(getViewLifecycleOwner(), student -> {
            if (student != null) {
                String studentName = student.getUser().getFullname() + " - " + student.getClass_code();
                fragmentHomeBinding.tvStudentName.setText(studentName);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    void loadRecentAssignment() {
        homeViewModel.loadRecentAssignment();
        homeViewModel.getRecentAssignment().observe(getViewLifecycleOwner(), assignment -> {
            if (assignment != null) {
                //de tai
                projectTermId = gson.toJson(assignment.getProject_term());
                fragmentHomeBinding.tvProjectStatus.setText(assignment.getStatus());
                fragmentHomeBinding.tvTenDeTai.setText(assignment.getProject().getName());
                fragmentHomeBinding.tvMaDeTai.setText(assignment.getProject().getId());

                Map<Integer, Integer> colorStatus = homeViewModel.getBackGroundStatusAssignment();
                int backgroundColor = colorStatus.keySet().iterator().next();
                int textColor = colorStatus.get(backgroundColor);
                fragmentHomeBinding.chipTrangThai.setChipBackgroundColor(
                        ContextCompat.getColorStateList(requireContext(), backgroundColor)
                );
                fragmentHomeBinding.chipTrangThai.setTextColor(textColor);
                fragmentHomeBinding.chipTrangThai.setText(assignment.toString());
                fragmentHomeBinding.tvLoaiDeTai.setText("Chưa có");

                //dot do an
                fragmentHomeBinding.tvStageProjectTerm.setText("Đợt " + assignment.getProject_term().getStage() + "/" + assignment.getProject_term().getAcademy_year().getYear_name());
                fragmentHomeBinding.tvNgayBatDau.setText(assignment.getProject_term().getStart_date());
                fragmentHomeBinding.tvNgayDangKy.setText(assignment.getCreated_at());
                fragmentHomeBinding.tvNgayKetThuc.setText(assignment.getProject_term().getEnd_date());
                int progress = homeViewModel.getProcess();
                fragmentHomeBinding.progressBar.setProgress(progress);
                fragmentHomeBinding.tvProgress.setText(progress + "%");
                fragmentHomeBinding.tvRangeDate.setText("Còn " + homeViewModel.getRangeDate() + " ngày");
            } else {
                fragmentHomeBinding.layoutRecentAssignment.setVisibility(View.GONE);
            }
        });
    }
}