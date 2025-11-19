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
import com.example.testui.model.AssignmentSupervisor;
import com.example.testui.model.Supervisor;
import com.example.testui.model.Teacher;
import com.example.testui.model.User;
import com.example.testui.untilities.formatter.SupervisorFormatter;
import com.example.testui.untilities.formatter.TeacherFormatter;
import com.example.testui.untilities.formatter.UserFormatter;

import java.util.List;

public class ScoreSupervisorAdapter extends RecyclerView.Adapter<ScoreSupervisorAdapter.ScoreSupervisorViewHolder> {

    Context context;
    List<AssignmentSupervisor> listAssigmentSupervisor;

    public ScoreSupervisorAdapter(Context context, List<AssignmentSupervisor> listAssigmentSupervisor) {
        this.context = context;
        this.listAssigmentSupervisor = listAssigmentSupervisor;
    }

    @NonNull
    @Override
    public ScoreSupervisorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score_supervisor, parent, false);
        return new ScoreSupervisorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreSupervisorViewHolder holder, int position) {
        AssignmentSupervisor assignmentSupervisor = listAssigmentSupervisor.get(position);
        Supervisor supervisor = SupervisorFormatter.format(assignmentSupervisor.getSupervisor());
        Teacher teacher = TeacherFormatter.format(supervisor.getTeacher());
        User user = UserFormatter.format(teacher.getUser());
        holder.tvSupervisorName.setText(user.getFullname());
        if (assignmentSupervisor.getScore_report() != null) {
            holder.tvScore.setText(String.valueOf(assignmentSupervisor.getScore_report()));
            holder.tvTime.setText(assignmentSupervisor.getUpdated_at());
        }
        holder.tvComment.setText(assignmentSupervisor.getComments());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<AssignmentSupervisor> listAssigmentSupervisor) {
        this.listAssigmentSupervisor = listAssigmentSupervisor;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listAssigmentSupervisor.size();
    }

    public class ScoreSupervisorViewHolder extends RecyclerView.ViewHolder {
        TextView tvSupervisorName, tvScore, tvTime, tvComment;
        public ScoreSupervisorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSupervisorName = itemView.findViewById(R.id.txtAdvisorName);
            tvScore = itemView.findViewById(R.id.txtAdvisorScore);
            tvTime = itemView.findViewById(R.id.txtAdvisorScoreDate);
            tvComment = itemView.findViewById(R.id.txtAdvisorComment);
        }
    }
}
