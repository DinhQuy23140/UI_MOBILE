package com.example.testui.ViewModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.model.ProjectTerm;
import com.example.testui.model.RegisterProjectTerm;
import com.example.testui.repository.ProjectTermRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RegisterProjectTermViewModel extends ViewModel {
    Context context;
    ProjectTermRepository projectTermRepository;
    MutableLiveData<List<ProjectTerm>> listNewProjectTerm = new MutableLiveData<>();
    MutableLiveData<List<RegisterProjectTerm>> listRegisterProjectTerm = new MutableLiveData<>();
    MutableLiveData<Boolean> isRegisterProjectTerm;

    public RegisterProjectTermViewModel(Context context) {
        this.context = context;
        this.projectTermRepository = new ProjectTermRepository(context);
        isRegisterProjectTerm = projectTermRepository.getIsRegisterProjectTerm();
    }

    @SuppressLint("CheckResult")
    public void loadListNewProjectTerm(String studentId) {
        projectTermRepository.loadListNewProjectTerm(studentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> {
                            List<ProjectTerm> projectTerms = list.first;
                            List<RegisterProjectTerm> registered = list.second;

                            Set<String> registeredIds = new HashSet<>();
                            for (RegisterProjectTerm register : registered) {
                                registeredIds.add(register.getProject_term_id());
                            }

                            List<ProjectTerm> items = new ArrayList<>();
                            for (ProjectTerm projectTerm : projectTerms) {
                                boolean isRegistered = registeredIds.contains(projectTerm.getId());
                                projectTerm.setRegistered(isRegistered);
                                items.add(projectTerm);
                            }
                            listRegisterProjectTerm.setValue(list.second);
                            listNewProjectTerm.setValue(items);

                        },
                        error -> {
                            error.printStackTrace();
                        }
                );
    }

    public MutableLiveData<List<ProjectTerm>> getListNewProjectTerm() {
        return listNewProjectTerm;
    }

    public MutableLiveData<List<RegisterProjectTerm>> getListRegisterProjectTerm() {
        return listRegisterProjectTerm;
    }

    public void registerProjectTerm(RegisterProjectTerm registerProjectTerm) {
        projectTermRepository.registerProjecTerm(registerProjectTerm);
    }

    public MutableLiveData<Boolean> getIsRegisterProjectTerm() {
        return isRegisterProjectTerm;
    }

}
