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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        ProjectTerm projectTerm = projectTerms.get(position);
        holder.tvTenDot.setText("Đợt đồ án: Kỳ " + projectTerm.getStage() + " năm " + projectTerm.getAcademy_year().getYear_name());
        holder.tvThoiGian.setText(projectTerm.getStart_date() + " - " + projectTerm.getEnd_date());
        holder.tvTrangthai.setText(projectTerm.getStatus());
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
        TextView tvTenDot, tvThoiGian, tvTrangthai;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenDot = itemView.findViewById(R.id.tvTenDot);
            tvThoiGian = itemView.findViewById(R.id.tvThoiGian);
            tvTrangthai = itemView.findViewById(R.id.tvStatus);
        }
    }
}
