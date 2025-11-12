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
import com.example.testui.model.Research;

import java.util.List;

public class ResearchAdapter extends RecyclerView.Adapter<ResearchAdapter.ResearchViewHolder> {
    Context context;
    List<Research> listResearch;
    OnClickItem onClickItem;

    public ResearchAdapter(Context context, List<Research> listResearch, OnClickItem onClickItem) {
        this.context = context;
        this.listResearch = listResearch;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public ResearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_research, parent, false);
        return new ResearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResearchViewHolder holder, int position) {
        Research research = listResearch.get(position);
        holder.tvResearchName.setText(research.getName());
        holder.tvResearchDescription.setText(research.getDescription());
        holder.itemView.setOnClickListener(click -> {
            onClickItem.onClickItem(position);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Research> listResearch) {
        this.listResearch = listResearch;
        notifyDataSetChanged();
    }

    public Research getItem(int position) {
        return listResearch.get(position);
    }

    @Override
    public int getItemCount() {
        return listResearch.size();
    }

    public class ResearchViewHolder extends RecyclerView.ViewHolder {
        TextView tvResearchName, tvResearchDescription;
        public ResearchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvResearchName = itemView.findViewById(R.id.tvResearchName);
            tvResearchDescription = itemView.findViewById(R.id.tvDescription);
        }
    }
}
