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
import com.example.testui.model.Project;
import com.example.testui.untilities.formatter.ProjectFormatter;

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
        holder.tvScore.setText("9");
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
