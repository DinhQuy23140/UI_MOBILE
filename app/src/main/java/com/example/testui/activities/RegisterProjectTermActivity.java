package com.example.testui.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testui.R;
import com.example.testui.ViewModel.RegisterProjectTermViewModel;
import com.example.testui.ViewModelFactory.RegisterProjectTermViewModelFactory;
import com.example.testui.adapter.RegisterProjectTermAdapter;
import com.example.testui.databinding.ActivityRegisterProjectTermBinding;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.ProjectTerm;
import com.example.testui.model.RegisterProjectTerm;
import com.example.testui.untilities.Constants;

import java.util.ArrayList;
import java.util.List;

public class RegisterProjectTermActivity extends AppCompatActivity {

    ActivityRegisterProjectTermBinding binding;
    RegisterProjectTermViewModel registerProjectTermViewModel;
    RegisterProjectTermAdapter adapter;
    String studentId = "";
    List<ProjectTerm> listProjectTerm = new ArrayList<>();
    List<ProjectTerm> displayListProjectTerm = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRegisterProjectTermBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        setupRecyclerView();
        observerData();
        loadData();
        event();
    }

    void init() {
        registerProjectTermViewModel = new ViewModelProvider(this, new RegisterProjectTermViewModelFactory(this)).get(RegisterProjectTermViewModel.class);
        studentId = getIntent().getStringExtra(Constants.KEY_ID_STUDENT);
    }

    void loadData(){
        registerProjectTermViewModel.loadListNewProjectTerm(studentId);
    }

    void setupRecyclerView() {
        binding.rcvDotDoAn.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new RegisterProjectTermAdapter(this, new ArrayList<>(), new OnClickItem() {
            @Override
            public void onClickItem(int position) {
                ProjectTerm projectTerm = listProjectTerm.get(position);
                RegisterProjectTerm registerProjectTerm = new RegisterProjectTerm(projectTerm.getId(), studentId, "pending");
                registerProjectTermViewModel.registerProjectTerm(registerProjectTerm);
                loadData();
            }
        });
        binding.rcvDotDoAn.setAdapter(adapter);
    }

    void observerData() {
        registerProjectTermViewModel.getListNewProjectTerm().observe(this, list -> {
            if (list != null && !list.isEmpty()) {
                listProjectTerm = list;
                binding.tvCount.setText(String.valueOf(list.size()));
                adapter.updateData(list);
                binding.layoutDotDoAn.setVisibility(View.VISIBLE);
                binding.rcvDotDoAn.setVisibility(View.VISIBLE);
                binding.emptyStateView.setVisibility(View.GONE);
                binding.progressBarIc.setVisibility(View.GONE);
            } else {
                binding.layoutDotDoAn.setVisibility(View.GONE);
                binding.progressBarIc.setVisibility(View.GONE);
                binding.rcvDotDoAn.setVisibility(View.GONE);
                binding.emptyStateView.setVisibility(View.VISIBLE);
            }
        });

        registerProjectTermViewModel.getIsRegisterProjectTerm().observe(this, isRegister -> {
            if (isRegister) {
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

     void event() {
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
     }

     private void filterList(String keyWord) {
        List<ProjectTerm> filterList = new ArrayList<>();
        if (keyWord.isEmpty()) {
            filterList.addAll(listProjectTerm);
        } else {
            for (ProjectTerm projectTerm : listProjectTerm) {
                if (projectTerm.keySearch().toLowerCase().contains(keyWord.toLowerCase())) {
                    filterList.add(projectTerm);
                }
            }
        }
        displayListProjectTerm.clear();
        displayListProjectTerm.addAll(filterList);
        adapter.updateData(displayListProjectTerm);
        binding.tvCount.setText(String.valueOf(displayListProjectTerm.size()));
     }
}