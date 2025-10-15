package com.example.testui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testui.R;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.Status;
import com.example.testui.model.Supervisor;
import com.example.testui.untilities.formatter.CouncilMemberFormatter;

import java.util.List;

public class BaseGVHDAdapter extends RecyclerView.Adapter<BaseGVHDAdapter.BaseGVHDViewHolder> {
    Context context;
    List<Supervisor> listSupervisor;
    OnClickItem onClickItem;

    public BaseGVHDAdapter(Context context, List<Supervisor> listSupervisor, OnClickItem onClickItem) {
        this.context = context;
        this.listSupervisor = listSupervisor;
        this.onClickItem = onClickItem;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Supervisor> listSupervisor) {
        this.listSupervisor = listSupervisor;
        notifyDataSetChanged();
    }

    public Supervisor getData(int position) {
        return listSupervisor.get(position);
    }

    @NonNull
    @Override
    public BaseGVHDViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_base_supervisor, parent,  false);
        return new BaseGVHDViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BaseGVHDViewHolder holder, int position) {
        Supervisor supervisor = listSupervisor.get(position);
        Status statusDegree = CouncilMemberFormatter.formatDegree(supervisor.getTeacher().getDegree());
        holder.tvSupervisorName.setText(statusDegree.getStrStatus() + "." + supervisor.getTeacher().getUser().getFullname());
        holder.itemView.setOnClickListener(click -> {
            onClickItem.onClickItem(position);
        });
    }

    @Override
    public int getItemCount() {
        return listSupervisor.size();
    }

    public class BaseGVHDViewHolder extends RecyclerView.ViewHolder {
        TextView tvSupervisorName;
        public BaseGVHDViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSupervisorName = itemView.findViewById(R.id.tv_name_supervisor);
        }
    }
}
