package com.example.testui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testui.R;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.Supervisor;

import java.util.List;

public class GVHDAdapter extends RecyclerView.Adapter<GVHDAdapter.GVHDViewHolder> {
    Context context;
    List<Supervisor> listSupervisor;
    OnClickItem onClickItem;

    public GVHDAdapter(Context context, List<Supervisor> listSupervisor, OnClickItem onClickItem) {
        this.context = context;
        this.listSupervisor = listSupervisor;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public GVHDViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gvhd, parent, false);
        return new GVHDViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GVHDViewHolder holder, int position) {
        Supervisor supervisor = listSupervisor.get(position);
        holder.tvGiangVienName.setText(supervisor.getTeacher().getUser().getFullname());
        holder.tvHocVi.setText(supervisor.getTeacher().getDegree());
        holder.itemView.setOnClickListener(gvhddetail -> {
            onClickItem.onClickItem(position);
        });
    }

    @Override
    public int getItemCount() {
        return listSupervisor.size();
    }

    public void updateData(List<Supervisor> listSupervisor) {
        this.listSupervisor = listSupervisor;
        notifyDataSetChanged();
    }

    public class GVHDViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvGiangVienName, tvHocVi, tvDonVi;
        public GVHDViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvGiangVienName = itemView.findViewById(R.id.tvGiangVienName);
            tvHocVi = itemView.findViewById(R.id.tvHocVi);
            tvDonVi = itemView.findViewById(R.id.tvDonVi);
        }
    }
}
