package com.example.testui.repository;

import android.content.Context;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.example.testui.client.Client;
import com.example.testui.model.ProjectTerm;
import com.example.testui.model.RegisterProjectTerm;
import com.example.testui.service.ProjectTermService;
import com.example.testui.service.RegisterProjectTermService;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

public class ProjectTermRepository {
    Context context;
    ProjectTermService projectTermService;
    RegisterProjectTermService registerProjectTermService;
    MutableLiveData<Boolean> isRegisterProjectTerm = new MutableLiveData<>();

    public ProjectTermRepository(Context context) {
        this.context = context;
        this.projectTermService = Client.getInstance().create(ProjectTermService.class);
        registerProjectTermService = Client.getInstance().create(RegisterProjectTermService.class);
    }

    public Observable<Pair<List<ProjectTerm>, List<RegisterProjectTerm>>> loadListNewProjectTerm(String studentId) {
        return Observable.zip(
                projectTermService.getNewTerms(),
                registerProjectTermService.getRegisterProjectTermByStudentId(studentId),
                (t1, t2) -> new Pair<>(t1, t2)
        );
    }

    public void registerProjecTerm(RegisterProjectTerm registerProjectTerm) {
        Call<RegisterProjectTerm> call = registerProjectTermService.registerProjectTerm(registerProjectTerm);
        call.enqueue(new Callback<RegisterProjectTerm>() {
            @Override
            public void onResponse(Call<RegisterProjectTerm> call, Response<RegisterProjectTerm> response) {
                if (response.isSuccessful()) {
                    RegisterProjectTerm registerProjectTerm = response.body();
                    isRegisterProjectTerm.setValue(true);
                } else {
                    isRegisterProjectTerm.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<RegisterProjectTerm> call, Throwable t) {
                t.printStackTrace();
                isRegisterProjectTerm.setValue(false);
            }
        });
    }

    public MutableLiveData<Boolean> getIsRegisterProjectTerm() {
        return isRegisterProjectTerm;
    }
}
