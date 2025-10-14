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
import com.example.testui.model.Attachment;
import com.example.testui.untilities.DateFormatter;

import java.util.Date;
import java.util.List;

public class AttachmentAdapter extends RecyclerView.Adapter<AttachmentAdapter.AttachmentViewHolder> {

    Context context;
    List<Attachment> listAttachment;
    OnClickItem onClickItem;

    public AttachmentAdapter(Context context, List<Attachment> listAttachment, OnClickItem onClickItem) {
        this.context = context;
        this.listAttachment = listAttachment;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public AttachmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_attachment, parent, false);
        return new AttachmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttachmentViewHolder holder, int position) {
        Attachment attachment = listAttachment.get(position);
        holder.tvFileName.setText(attachment.getFile_name());
        holder.tvFileUrl.setText(attachment.getFile_url());
        holder.tvUploadTime.setText(DateFormatter.formatDate(attachment.getCreated_at()));
        holder.itemView.setOnClickListener(click -> {
            onClickItem.onClickItem(position);
        });
    }

    @Override
    public int getItemCount() {
        return listAttachment.size();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Attachment> listAttachment) {
        this.listAttachment = listAttachment;
        notifyDataSetChanged();
    }

    public Attachment getData(int position) {
        return listAttachment.get(position);
    }

    public class AttachmentViewHolder extends RecyclerView.ViewHolder {

        TextView tvFileName, tvFileUrl, tvUploadTime;
        public AttachmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFileName = itemView.findViewById(R.id.tvFileName);
            tvFileUrl = itemView.findViewById(R.id.tvFileUrl);
            tvUploadTime = itemView.findViewById(R.id.tvUploadTime);
        }
    }
}
