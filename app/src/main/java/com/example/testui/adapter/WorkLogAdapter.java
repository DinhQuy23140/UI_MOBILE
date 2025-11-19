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

import java.util.List;

public class WorkLogAdapter extends RecyclerView.Adapter<WorkLogAdapter.WorkLogViewHolder> {

    Context context;
    List<String> listWork;

    public WorkLogAdapter(Context context, List<String> listWork) {
        this.context = context;
        this.listWork = listWork;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<String> listWork) {
        this.listWork = listWork;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WorkLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work_log, parent, false);
        return new WorkLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkLogViewHolder holder, int position) {
        String work = listWork.get(position);
        holder.tvWorkName.setText(work);
    }

    @Override
    public int getItemCount() {
        return listWork.size();
    }

    public class WorkLogViewHolder extends RecyclerView.ViewHolder {
        TextView tvWorkName;
        public WorkLogViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWorkName = itemView.findViewById(R.id.tv_work_name);
        }
    }
}
