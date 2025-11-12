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
import java.util.List;

public class GraduateAdapter extends RecyclerView.Adapter<GraduateAdapter.GraduateViewHolder> {
    Context context;
    List<ProjectTerm> listProjectTerm;
    OnClickItem onClickItem;

    public GraduateAdapter(Context context, List<ProjectTerm> listProjectTerm, OnClickItem onClickItem) {
        this.context = context;
        this.listProjectTerm = listProjectTerm;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public GraduateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_dot_do_an, parent, false);
        return new GraduateViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull GraduateViewHolder holder, int position) {
        ProjectTerm projectTerm = listProjectTerm.get(position);
        holder.tvTenDot.setText(projectTerm.getStage());
        holder.tvTgBatDau.setText(projectTerm.getStart_date());
        holder.tvTgKetThuc.setText(projectTerm.getEnd_date());
        holder.tvNamHoc.setText(projectTerm.getAcademy_year().getYear_name());
        holder.tvMoTa.setText(projectTerm.getDescription());
//        holder.tvTrangThai.setText(projectTerm.getStatus());
        holder.itemView.setOnClickListener(click -> {
            onClickItem.onClickItem(position);
        });
    }

    @Override
    public int getItemCount() {
        return listProjectTerm.size();
    }

    public class GraduateViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenDot, tvTgBatDau, tvTgKetThuc, tvNamHoc, tvMoTa, tvTrangThai;
        public GraduateViewHolder(@NonNull View itemView) {
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
