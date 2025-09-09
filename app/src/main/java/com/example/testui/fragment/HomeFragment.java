package com.example.testui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.testui.databinding.ActivityHomeBinding;
import com.example.testui.untilities.Constants;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        homeViewModel = new ViewModelProvider(this, new HomeViewModelFactory(requireContext())).get(HomeViewModel.class);
        String studentId = homeViewModel.getStudentId();

        LinearLayout layoutSinhVien = view.findViewById(R.id.layout_sinh_vien);
        layoutSinhVien.setOnClickListener(click -> {
            Intent intent = new Intent(getContext(), CapNhapThongTinActivity.class);
            intent.putExtra(Constants.KEY_ID_STUDENT, studentId);
            startActivity(intent);
        });

        LinearLayout layout_gvhd = view.findViewById(R.id.layout_gvhd);
        layout_gvhd.setOnClickListener(gvhd -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new GiangVienHuongDanFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        LinearLayout layoutDoAn = view.findViewById(R.id.layout_doan);
        layoutDoAn.setOnClickListener(click -> {
            Intent intent = new Intent(getContext(), DanhSachDotDoAnActivity.class);
            intent.putExtra(Constants.KEY_ID_STUDENT, studentId);
            startActivity(intent);
        });

        LinearLayout ln_progress = view.findViewById(R.id.ln_progress);
        ln_progress.setOnClickListener(click -> {
            Intent intent = new Intent(getContext(), ChiTietDoAnActivity.class);
            startActivity(intent);
        });
    }
}