package com.example.testui.untilities.formatter;

import com.example.testui.R;
import com.example.testui.model.CouncilsMember;
import com.example.testui.model.Status;

public class CouncilMemberFormatter {
    public static Status formatRole(String role) {
        switch (role) {
            case "5":
                return new Status(R.drawable.bg_badge_chu_tich, "Chủ tịch HD");
            case "4":
                return new Status(R.drawable.bg_badge_thu_ky, "Thư ký HD");
            case "3":
            case "2":
            case "1":
                return new Status(R.drawable.bg_badge_uy_vien, "Ủy viên HD");
            default:
                return new Status(R.drawable.bg_badge_uy_vien, "Ủy viên HD");
        }
    }

    public static Status formatDegree(String degree) {
        switch (degree) {
            case "Cử nhân":
                return new Status(R.drawable.bg_badge_hoc_vi_cu_nhan, "Cn");
            case "Thạc sĩ":
                return new Status(R.drawable.bg_badge_hoc_vi_thac_si, "ThS");
            case "Tiến sĩ":
                return new Status(R.drawable.bg_badge_hoc_vi_tien_si, "TS");
            case "Phó Giáo sư":
                return new Status(R.drawable.bg_badge_hoc_vi_pho_giao_su, "PhGS");
            case "Giáo sư":
                return new Status(R.drawable.bg_badge_hoc_vi_giao_su, "GS");
            default:
                return new Status(R.drawable.bg_badge_hoc_vi_cu_nhan, "Cn");
        }
    }

    public static Status formatScore(String score) {
        if (score != null) {
            return new Status(R.drawable.bg_circle_completed, "Đã chấm");
        } else {
            return  new Status(R.drawable.bg_circle_pending, "Chưa chấm");
        }
    }

    public static CouncilsMember format(CouncilsMember councilsMember) {
        if (councilsMember == null) {
            councilsMember = new CouncilsMember();
        }
        if (councilsMember.getRole() == null) councilsMember.setRole("5");
        councilsMember.setSupervisor(SupervisorFormatter.format(councilsMember.getSupervisor()));
        return councilsMember;
    }
}
