package com.example.testui.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.testui.R;
import com.example.testui.model.AssignmentSupervisor;
import com.example.testui.model.CouncilsMember;
import com.example.testui.model.Status;
import com.example.testui.model.Supervisor;

import java.util.ArrayList;
import java.util.List;

public class TraCuuHoiDongViewModel extends ViewModel {
    public TraCuuHoiDongViewModel() {
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

    public Status getRole(String role) {
        switch (role) {
            case "5":
                return new Status(R.drawable.bg_badge_chu_tich, "Chủ tịch hội đồng");
            case "4":
                return new Status(R.drawable.bg_badge_thu_ky, "Thư ký hội đồng");
            case "3":
            case "2":
            case "1":
                return new Status(R.drawable.bg_badge_uy_vien, "Ủy viên hội đồng");
            default:
                return new Status(R.drawable.bg_badge_uy_vien, "Ủy viên hội đồng");
        }
    }
}
