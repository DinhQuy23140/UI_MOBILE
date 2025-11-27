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

        double scoreReview = parseScore(councilProject.getReview_score());

        List<Double> supervisorScores = new ArrayList<>();
        List<AssignmentSupervisor> sups = assignment.getAssignment_supervisors();
        if (sups != null && !sups.isEmpty()) {
            for (AssignmentSupervisor sup : sups) {
                double score = parseScore(sup.getScore_report());
                if (score >= 0) supervisorScores.add(score);
            }
        }

        double avgSupervisor = 0;
        if (!supervisorScores.isEmpty()) {
            for (double s : supervisorScores) avgSupervisor += s;
            avgSupervisor /= supervisorScores.size();
        }

        List<Double> defenceScores = new ArrayList<>();
        List<CouncilProjectDefence> defs = councilProject.getCouncil_project_defences();
        if (defs != null && !defs.isEmpty()) {
            for (CouncilProjectDefence def : defs) {
                double score = parseScore(def.getScore());
                if (score >= 0) defenceScores.add(score);
            }
        }

        double avgDefence = defenceScores.isEmpty()
                ? 0
                : removeOutlierAndAverage(defenceScores);

        List<Double> finalParts = new ArrayList<>();
        if (scoreReview >= 0) finalParts.add(scoreReview);
        if (!supervisorScores.isEmpty()) finalParts.add(avgSupervisor);
        if (!defenceScores.isEmpty()) finalParts.add(avgDefence);

        if (finalParts.isEmpty()) return -1;

        double total = 0;
        for (double part : finalParts) total += part;

        return BigDecimal.valueOf(total / finalParts.size())
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private double parseScore(String score) {
        if (score == null || score.trim().isEmpty() || score.equals("-")) {
            return -1;
        }
        try {
            return Double.parseDouble(score);
        } catch (Exception e) {
            return -1;
        }
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
        } else if (score < 4 && score >= 0) {
            status = new Status(R.drawable.bg_status_pending, "Không đạt");
        } else {
            status = new Status(R.drawable.bg_task_icon, "Chưa có");
        }
        return status;
    }
}
