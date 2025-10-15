package com.example.testui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testui.R;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.Supervisor;

import java.util.List;

public class CouncilsMemberAdapter extends RecyclerView.Adapter<CouncilsMemberAdapter.CouncilsMemberViewHolder> {
    Context context;
    List<Supervisor> listSupervisor;
    OnClickItem onClickItem;

    public CouncilsMemberAdapter(Context context, List<Supervisor> listSupervisor, OnClickItem onClickItem) {
        this.context = context;
        this.listSupervisor = listSupervisor;
        this.onClickItem = onClickItem;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Supervisor> listSupervisor) {
        this.listSupervisor = listSupervisor;
        notifyDataSetChanged();
    }

    public Supervisor getSupervisor(int position) {
        return listSupervisor.get(position);
    }

    @NonNull
    @Override
    public CouncilsMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_council_member, parent, false);
        return new CouncilsMemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CouncilsMemberViewHolder holder, int position) {
        Supervisor supervisor = listSupervisor.get(position);
        holder.tvName.setText(supervisor.getTeacher().getUser().getFullname());
        holder.tvPosition.setText(supervisor.getTeacher().getPosition());
        holder.tvDegree.setText(supervisor.getTeacher().getDegree());
        holder.btnView.setOnClickListener(v -> onClickItem.onClickItem(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class CouncilsMemberViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPosition, tvDegree, tvFaculty;
        ImageButton btnView;
        ImageView ivAvatar;
        public CouncilsMemberViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvThanhVienName);
            tvPosition = itemView.findViewById(R.id.tvVaiTro);
            tvDegree = itemView.findViewById(R.id.tvHocVi);
            tvFaculty = itemView.findViewById(R.id.tvDonVi);
            btnView = itemView.findViewById(R.id.btnDetail);
            ivAvatar = itemView.findViewById(R.id.imgAvatar);
        }
    }
}
