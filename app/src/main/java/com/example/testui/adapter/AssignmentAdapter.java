package com.example.testui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testui.R;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.Assignment;
import com.example.testui.model.AssignmentSupervisor;
import com.example.testui.model.CouncilProject;
import com.example.testui.model.CouncilProjectDefence;
import com.example.testui.model.Project;
import com.example.testui.untilities.formatter.CouncilProjectFormatter;
import com.example.testui.untilities.formatter.ProjectFormatter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {
    Context context;
    List<Assignment> listAssignment;
    OnClickItem onClickItem;

    public AssignmentAdapter(Context context, List<Assignment> listAssignment, OnClickItem onClickItem) {
        this.context = context;
        this.listAssignment = listAssignment;
        this.onClickItem = onClickItem;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateDate(List<Assignment> listAssignment) {
        this.listAssignment = listAssignment;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assignment, parent, false);
        return new AssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        Assignment assignment = listAssignment.get(position);
        holder.tvName.setText(assignment.getStudent().getUser().getFullname());
        holder.tvStudentId.setText(assignment.getStudent().getStudent_code());
        holder.tvProjectTerm.setText("Đợt " + assignment.getProject_term().getStage() + "/" + assignment.getProject_term().getAcademy_year().getYear_name());
        Project project = ProjectFormatter.format(assignment.getProject());
        holder.tvTopic.setText(project.getName());
        double totalScore = totalScore(assignment);
        holder.tvScore.setText(totalScore >= 0 ? String.valueOf(totalScore) : "-");
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

    @Override
    public int getItemCount() {
        return listAssignment.size();
    }

    public class AssignmentViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvStudentId, tvProjectTerm, tvTopic, tvScore;
        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvTenSinhVien);
            tvStudentId = itemView.findViewById(R.id.tvMaSinhVien);
            tvProjectTerm = itemView.findViewById(R.id.tvDotDoAn);
            tvTopic = itemView.findViewById(R.id.tvTenDeTai);
            tvScore = itemView.findViewById(R.id.tvDiemSo);
        }
    }
}
