package com.example.testui.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.model.Assignment;
import com.example.testui.model.AssignmentSupervisor;
import com.example.testui.model.Council;
import com.example.testui.model.CouncilsMember;
import com.example.testui.model.Supervisor;
import com.example.testui.untilities.formatter.DateFormatter;

import java.util.ArrayList;
import java.util.List;

public class TraCuuBaoVeViewModel extends ViewModel {
    private Context context;
    public TraCuuBaoVeViewModel(Context context) {
        this.context = context;
    }

    public List<Supervisor> convertListSupervisor(List<CouncilsMember> listCouncilMember) {
        List<Supervisor> listSupervisor = new ArrayList<>();
        for (CouncilsMember councilsMember : listCouncilMember) {
            listSupervisor.add(councilsMember.getSupervisor());
        }
        return listSupervisor;
    }

    public List<Supervisor> convertListBaseSupervisor(List<AssignmentSupervisor> listAssignmentSupervisor) {
        List<Supervisor> listSupervisor = new ArrayList<>();
        for (AssignmentSupervisor assignmentSupervisor : listAssignmentSupervisor) {
            listSupervisor.add(assignmentSupervisor.getSupervisor());
        }
        return listSupervisor;
    }

    public Council getCouncilSafe(Assignment assignment) {
        if (assignment == null || assignment.getCouncil_project() == null) return null;
        return assignment.getCouncil_project().getCouncil();
    }
    public MutableLiveData<Council> councilDisplay = new MutableLiveData<>();

    public void setCouncil(Council council) {
        Council model = new Council();
        model.setName(council != null && council.getName() != null ? council.getName() : "—");
        model.setId(council != null && council.getId() != null ? council.getId() : "—");
        model.getDepartment().setName(council != null && council.getDepartment() != null ? council.getDepartment().getName() : "—");
        model.setDescription(council != null && council.getDescription() != null ? council.getDescription() : "—");
        model.setAddress(council != null && council.getAddress() != null ? council.getAddress() : "—");
        model.setDate(council != null && council.getDate() != null ? DateFormatter.formatDate(council.getDate()) : "—");
        councilDisplay.setValue(model);
    }

}
