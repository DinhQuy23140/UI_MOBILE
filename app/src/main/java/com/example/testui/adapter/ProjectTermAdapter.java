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
import com.example.testui.model.ProjectTerm;
import com.example.testui.untilities.formatter.DateFormatter;

import java.util.List;

public class ProjectTermAdapter extends RecyclerView.Adapter<ProjectTermAdapter.RecyclerViewHolder> {
    Context context;
    List<ProjectTerm> projectTerms;
    OnClickItem onClickItem;

    public ProjectTermAdapter(Context context, List<ProjectTerm> projectTerms, OnClickItem onClickItem) {
        this.context = context;
        this.projectTerms = projectTerms;
        this.onClickItem = onClickItem;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dot_do_an, parent, false);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        ProjectTerm projectTerm = projectTerms.get(position);
        holder.tvTenDot.setText(projectTerm.getStage());

        String startDate = DateFormatter.formatDate(projectTerm.getStart_date());
        holder.tvTgBatDau.setText(startDate);

        String endDate = DateFormatter.formatDate(projectTerm.getEnd_date());
        holder.tvTgKetThuc.setText(endDate);

        holder.tvNamHoc.setText(projectTerm.getAcademy_year().getYear_name());
        holder.tvMoTa.setText(projectTerm.getDescription());
        holder.tvTrangThai.setText(projectTerm.toString());
        holder.tvTrangThai.setBackground(context.getDrawable(projectTerm.getBackgroundColor()));
        holder.itemView.setOnClickListener(v -> onClickItem.onClickItem(position));
    }

    public ProjectTerm getItem(int position) {
        return projectTerms.get(position);
    }

    public void updateData(List<ProjectTerm> projectTerms) {
        this.projectTerms = projectTerms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return projectTerms.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenDot, tvTgBatDau, tvTgKetThuc, tvNamHoc, tvMoTa, tvTrangThai;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenDot = itemView.findViewById(R.id.tvDotDoAn);
            tvTgBatDau = itemView.findViewById(R.id.tvStartDate);
            tvTgKetThuc = itemView.findViewById(R.id.tvEndDate);
            tvNamHoc = itemView.findViewById(R.id.tvNamHoc);
            tvMoTa = itemView.findViewById(R.id.tvMoTa);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
        }
    }
}
