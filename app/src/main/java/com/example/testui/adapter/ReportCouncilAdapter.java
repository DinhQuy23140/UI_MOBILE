package com.example.testui.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReportCouncilAdapter extends RecyclerView.Adapter<ReportCouncilAdapter.ReportCouncilViewHolder> {

    @NonNull
    @Override
    public ReportCouncilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ReportCouncilViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ReportCouncilViewHolder extends RecyclerView.ViewHolder {

        public ReportCouncilViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
