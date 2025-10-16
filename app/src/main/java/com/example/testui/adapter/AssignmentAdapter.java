package com.example.testui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testui.R;
import com.example.testui.model.Assignment;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {
    Context context;
    List<Assignment> listAssignment;

    public AssignmentAdapter(Context context, List<Assignment> listAssignment) {
        this.context = context;
        this.listAssignment = listAssignment;
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

    }

    @Override
    public int getItemCount() {
        return listAssignment.size();
    }

    public class AssignmentViewHolder extends RecyclerView.ViewHolder {

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
