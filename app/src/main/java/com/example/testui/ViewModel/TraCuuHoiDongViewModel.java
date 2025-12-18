package com.example.testui.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.R;
import com.example.testui.model.Assignment;
import com.example.testui.model.AssignmentSupervisor;
import com.example.testui.model.CouncilsMember;
import com.example.testui.model.Status;
import com.example.testui.model.Supervisor;
import com.example.testui.repository.AssignmentRepository;
import com.example.testui.repository.SinhVienRepository;

import java.util.ArrayList;
import java.util.List;

public class TraCuuHoiDongViewModel extends ViewModel {
    AssignmentRepository assignmentRepository;
    SinhVienRepository sinhVienRepository;
    MutableLiveData<Assignment> getAssignmentByStudentIdAndTermIdMutableLiveData;
    Context context;
    public TraCuuHoiDongViewModel(Context context) {
        this.context = context;
        assignmentRepository = new AssignmentRepository();
        sinhVienRepository = new SinhVienRepository(context);
        getAssignmentByStudentIdAndTermIdMutableLiveData = assignmentRepository.getAssignmentByStudentIdAndTermId();
    }

    public List<Supervisor> convertListSupervisor(List<CouncilsMember> listCouncilMember) {
        List<Supervisor> listSupervisor = new ArrayList<>();
        if (listCouncilMember != null && listCouncilMember.isEmpty()) {
            for (CouncilsMember councilsMember : listCouncilMember) {
                listSupervisor.add(councilsMember.getSupervisor());
            }
        }
        return listSupervisor;
    }

    public List<Supervisor> convertListBaseSupervisor(List<AssignmentSupervisor> listAssignmentSupervisor) {
        List<Supervisor> listSupervisor = new ArrayList<>();
        if (listAssignmentSupervisor != null && !listAssignmentSupervisor.isEmpty()) {
            for (AssignmentSupervisor assignmentSupervisor : listAssignmentSupervisor) {
                listSupervisor.add(assignmentSupervisor.getSupervisor());
            }
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

    public void loadAssignment(String termId) {
        String studenId = sinhVienRepository.getStudentId();
        assignmentRepository.loadAssignmentByStudentIdAndTermId(studenId, termId);
    }

    public MutableLiveData<Assignment> getGetAssignmentByStudentIdAndTermIdMutableLiveData() {
        return getAssignmentByStudentIdAndTermIdMutableLiveData;
    }
}
