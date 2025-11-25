package com.example.testui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testui.R;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.ProjectTerm;
import com.example.testui.untilities.formatter.DateFormatter;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RegisterProjectTermAdapter extends RecyclerView.Adapter<RegisterProjectTermAdapter.RegisterProjectTermViewHolder> {
    List<ProjectTerm> listProjectTerm;
    Context context;
    OnClickItem onClickItem;

    public RegisterProjectTermAdapter(Context context, List<ProjectTerm> listProjectTerm, OnClickItem onClickItem) {
        this.context = context;
        this.listProjectTerm = listProjectTerm;
        this.onClickItem = onClickItem;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<ProjectTerm> listProjectTerm) {
        this.listProjectTerm = listProjectTerm;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        listProjectTerm.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(int position, ProjectTerm projectTerm) {
        listProjectTerm.add(position, projectTerm);
        notifyItemInserted(position);
    }

    public ProjectTerm getItem(int position) {
        return listProjectTerm.get(position);
    }

    @NonNull
    @Override
    public RegisterProjectTermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_register_project_term, parent, false);
        return new RegisterProjectTermViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegisterProjectTermViewHolder holder, int position) {
        ProjectTerm projectTerm = listProjectTerm.get(position);
        LocalDate localDate = LocalDate.now();
        OffsetDateTime dateTime = OffsetDateTime.parse(projectTerm.getCreated_at());
        LocalDate startDate = dateTime.toLocalDate();
        LocalDate endDate = LocalDate.parse(projectTerm.getStart_date());
        holder.tvStage.setText(projectTerm.getStage());
        holder.tvAcademyYear.setText(projectTerm.getAcademy_year().getYear_name());
        holder.tvDescription.setText(projectTerm.getDescription());
        holder.tvStart.setText(startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());
        holder.tvEnd.setText(DateFormatter.formatDate(projectTerm.getEnd_date()));
        holder.tvCountStudent.setText(String.valueOf(startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        if (localDate.isAfter(endDate)) {
            holder.tvStatus.setText("Đã hết hạn");
        } else if (localDate.isBefore(endDate) && localDate.isAfter(startDate)) {
            holder.tvStatus.setText("Đang diễn ra");
        } else {
            holder.tvStatus.setText("Chưa bắt đầu");
        }

        if (projectTerm.getRegistered()) {
            holder.btnRegister.setVisibility(View.GONE);
            holder.btnDone.setVisibility(View.VISIBLE);
        } else {
            holder.btnRegister.setVisibility(View.VISIBLE);
            holder.btnDone.setVisibility(View.GONE);
        }

        holder.btnRegister.setOnClickListener(v -> onClickItem.onClickItem(position));
    }

    @Override
    public int getItemCount() {
        return listProjectTerm.size();
    }

    public class RegisterProjectTermViewHolder extends RecyclerView.ViewHolder {
        TextView tvStage, tvAcademyYear, tvDescription, tvStart, tvEnd, tvCountStudent, tvStatus;
        Button btnRegister, btnDone;
        public RegisterProjectTermViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStage = itemView.findViewById(R.id.tvBatchName);
            tvAcademyYear = itemView.findViewById(R.id.tvAcademicYear);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvStart = itemView.findViewById(R.id.tvStartDate);
            tvEnd = itemView.findViewById(R.id.tvEndDate);
            tvCountStudent = itemView.findViewById(R.id.tvRegisteredCount);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnRegister = itemView.findViewById(R.id.btnRegister);
            btnDone = itemView.findViewById(R.id.btnDone);
        }
    }
}
