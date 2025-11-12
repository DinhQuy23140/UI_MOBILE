package com.example.testui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testui.R;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.ProposedTopic;
import com.example.testui.untilities.formatter.ProposedTopicFormatter;

import java.util.List;

public class ProposedTopicAdapter extends RecyclerView.Adapter<ProposedTopicAdapter.ProposedTopicViewHolder> {
    List<ProposedTopic> listTopic;
    Context context;
    OnClickItem onClickItem;

    public ProposedTopicAdapter(Context context, List<ProposedTopic> listTopic, OnClickItem onClickItem) {
        this.context = context;
        this.listTopic = listTopic;
        this.onClickItem = onClickItem;
    }

    public void updateData(List<ProposedTopic> listTopic) {
        this.listTopic = listTopic;
        notifyDataSetChanged();
    }

    public ProposedTopic getItem(int position) {
        return listTopic.get(position);
    }

    @NonNull
    @Override
    public ProposedTopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proposed_topic, parent, false);
        return new ProposedTopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProposedTopicViewHolder holder, int position) {
        ProposedTopic proposedTopic = ProposedTopicFormatter.format(listTopic.get(position));
        holder.tvName.setText(proposedTopic.getTitle());
        holder.tvDescription.setText(proposedTopic.getDescription());
        holder.tvSupervisor.setText(proposedTopic.getSupervisor().getTeacher().getUser().getFullname());
        holder.itemView.setOnClickListener(proposedTopicClick -> {
            onClickItem.onClickItem(position);
        });
    }

    @Override
    public int getItemCount() {
        return listTopic.size();
    }

    public class ProposedTopicViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvDescription, tvSupervisor;
        public ProposedTopicViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvTopicName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvSupervisor = itemView.findViewById(R.id.tvLecturer);
        }
    }
}
