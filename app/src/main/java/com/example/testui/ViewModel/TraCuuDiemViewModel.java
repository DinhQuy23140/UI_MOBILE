package com.example.testui.ViewModel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.testui.R;
import com.example.testui.model.Assignment;
import com.example.testui.model.AssignmentSupervisor;
import com.example.testui.model.CouncilProject;
import com.example.testui.model.CouncilProjectDefence;
import com.example.testui.model.Status;
import com.example.testui.model.Supervisor;
import com.example.testui.untilities.formatter.AssignmentFormatter;
import com.example.testui.untilities.formatter.CouncilProjectFormatter;
import com.example.testui.untilities.formatter.SupervisorFormatter;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public double totalScore(Assignment assignment) {

        CouncilProject councilProject = CouncilProjectFormatter.format(assignment.getCouncil_project());

        double scoreReview = Double.parseDouble(councilProject.getReview_score());

        List<Double> supervisorScores = new ArrayList<>();
        for (AssignmentSupervisor sup : assignment.getAssignment_supervisors()) {
            supervisorScores.add(Double.parseDouble(sup.getScore_report()));
        }

        double avgSupervisor = 0;
        for (double s : supervisorScores) avgSupervisor += s;
        avgSupervisor /= supervisorScores.size();


        List<Double> defenceScores = new ArrayList<>();
        for (CouncilProjectDefence def : councilProject.getCouncil_project_defences()) {
            defenceScores.add(Double.parseDouble(def.getScore()));
        }

        double avgDefence = removeOutlierAndAverage(defenceScores);

        return BigDecimal.valueOf((scoreReview + avgSupervisor + avgDefence) / 3)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private double removeOutlierAndAverage(List<Double> scores) {
        if (scores == null || scores.isEmpty()) return 0;

        double avg = 0;
        for (double s : scores) avg += s;
        avg /= scores.size();

        List<Double> validScores = new ArrayList<>();
        for (double s : scores) {
            if (Math.abs(s - avg) < 1.5) {
                validScores.add(s);
            }
        }

        if (validScores.isEmpty()) validScores = scores;

        double finalAvg = 0;
        for (double s : validScores) finalAvg += s;
        return finalAvg / validScores.size();
    }


    public Status statusScore(Assignment assignment) {
        double score = totalScore(assignment);
        Status status;
        if (score >= 4 && score <= 10) {
            status = new Status(R.drawable.bg_status_completed, "Đạt");
        } else {
            status = new Status(R.drawable.bg_status_pending, "Không đạt");
        }
        return status;
    }
}
