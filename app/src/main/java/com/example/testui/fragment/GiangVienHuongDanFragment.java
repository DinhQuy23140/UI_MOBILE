package com.example.testui.fragment;

import static android.app.ProgressDialog.show;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testui.ViewModel.GVHDViewModel;
import com.example.testui.ViewModelFactory.GVHDViewModelFactory;
import com.example.testui.activities.SupervisorActivity;
import com.example.testui.adapter.GVHDAdapter;
import com.example.testui.databinding.FragmentGiangVienHuongDanBinding;
import com.example.testui.model.Supervisor;
import com.example.testui.untilities.Constants;
import com.google.gson.Gson;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GiangVienHuongDanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GiangVienHuongDanFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FragmentGiangVienHuongDanBinding binding;
    GVHDViewModel gvhdViewModel;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GiangVienHuongDanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GiangVienHuongDanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GiangVienHuongDanFragment newInstance(String param1, String param2) {
        GiangVienHuongDanFragment fragment = new GiangVienHuongDanFragment();
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
//        return inflater.inflate(R.layout.fragment_giang_vien_huong_dan, container, false);
        binding = FragmentGiangVienHuongDanBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gvhdViewModel = new ViewModelProvider(this, new GVHDViewModelFactory(getContext())).get(GVHDViewModel.class);
        gvhdViewModel.getGVHD();
        gvhdViewModel.getListSupervisor().observe(getViewLifecycleOwner(), result -> {
            List<Supervisor> listSupervisor = result;
            binding.tvCountSupervisor.setText(String.valueOf(listSupervisor.size()));
            Log.d("Supervisor", result.size() + "");
            GVHDAdapter gvhdAdapter = new GVHDAdapter(requireContext(), listSupervisor, position -> {
                Intent intent = new Intent(getContext(), SupervisorActivity.class);
                Gson gson = new Gson();
                Supervisor supervisor = listSupervisor.get(position);
                String strGVHD = gson.toJson(supervisor);
                intent.putExtra(Constants.KEY_SUPERVISOR, strGVHD);
                startActivity(intent);
            });
            binding.rvGiangVien.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            binding.rvGiangVien.setAdapter(gvhdAdapter);
        });
    }
}