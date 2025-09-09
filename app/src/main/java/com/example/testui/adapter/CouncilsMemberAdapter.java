package com.example.testui.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CouncilsMemberAdapter extends RecyclerView.Adapter<CouncilsMemberAdapter.CouncilsMemberViewHolder> {

    @NonNull
    @Override
    public CouncilsMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CouncilsMemberViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class CouncilsMemberViewHolder extends RecyclerView.ViewHolder {

        public CouncilsMemberViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
