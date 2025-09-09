package com.example.testui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testui.R;
import com.example.testui.activities.TimeLineActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DotDoAnFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DotDoAnFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    CardView cvItemDotDoAn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DotDoAnFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DotDoAnFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DotDoAnFragment newInstance(String param1, String param2) {
        DotDoAnFragment fragment = new DotDoAnFragment();
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
        return inflater.inflate(R.layout.fragment_dot_do_an, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cvItemDotDoAn = view.findViewById(R.id.item_doan);
        cvItemDotDoAn.setOnClickListener(click -> {
            Intent intent = new Intent(getContext(), TimeLineActivity.class);
            startActivity(intent);
        });
    }
}