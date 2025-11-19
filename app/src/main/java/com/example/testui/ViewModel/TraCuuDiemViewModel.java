package com.example.testui.ViewModel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.testui.model.AssignmentSupervisor;
import com.example.testui.model.Supervisor;
import com.example.testui.untilities.formatter.AssignmentFormatter;
import com.example.testui.untilities.formatter.SupervisorFormatter;

import java.util.ArrayList;
import java.util.List;

public class TraCuuDiemViewModel extends ViewModel {
    Context context;

    public TraCuuDiemViewModel(Context context) {
        this.context = context;
    }

    public List<Supervisor> getSupervisor(List<AssignmentSupervisor> listAssignmentSupervisor) {
        List<Supervisor> listSupervisor = new ArrayList<>();
        for (AssignmentSupervisor assignmentSupervisor : listAssignmentSupervisor) {
            listSupervisor.add(SupervisorFormatter.format(assignmentSupervisor.getSupervisor()));
        }
        return listSupervisor;
    }
}
