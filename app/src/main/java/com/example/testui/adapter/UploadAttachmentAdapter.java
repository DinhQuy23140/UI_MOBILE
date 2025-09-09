package com.example.testui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testui.R;
import com.example.testui.interfaces.UploadDocumentClick;
import com.example.testui.model.Attachment;
import com.example.testui.model.Document;

import java.util.ArrayList;
import java.util.List;

public class UploadAttachmentAdapter extends RecyclerView.Adapter<UploadAttachmentAdapter.UploadAttachmentViewHolder> {

    Context context;
    List<Document> listDocument;
    UploadDocumentClick uploadAttachmentClick;

    public UploadAttachmentAdapter(Context context, List<Document> listDocument, UploadDocumentClick uploadAttachmentClick) {
        this.context = context;
        this.listDocument = listDocument;
        this.uploadAttachmentClick = uploadAttachmentClick;
    }

    @NonNull
    @Override
    public UploadAttachmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upload_attachment, parent, false);
        return new UploadAttachmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadAttachmentViewHolder holder, int position) {
        Document attachment = listDocument.get(position);
        String fileType = attachment.getTypeFile();
//        if (fileType == null) {
//            holder.ivFileType.setImageResource(R.drawable.file_default_ic);
//        } else if (fileType.equals("application/pdf")) {
//            holder.ivFileType.setImageResource(R.drawable.pdf_ic);
//        } else if (fileType.equals("application/msword") || fileType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
//            holder.ivFileType.setImageResource(R.drawable.word_ic);
//        } else if (fileType.startsWith("image/")) {
//            holder.ivFileType.setImageResource(R.drawable.image_ic);
//        } else if (fileType.startsWith("audio/")) {
//            holder.ivFileType.setImageResource(R.drawable.video_ic);
//        } else {
//            holder.ivFileType.setImageResource(R.drawable.file_default_ic);
//        }
        holder.tvFileName.setText(attachment.getFileName());
        holder.tvUploadTime.setText(attachment.getTimeUpload());
        holder.btnDownload.setOnClickListener(click -> {
            uploadAttachmentClick.onDownload(position);
        });
        holder.btnDelete.setOnClickListener(click -> {
            uploadAttachmentClick.onDelete(position);
        });
        holder.itemView.setOnClickListener(click -> {
            uploadAttachmentClick.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return listDocument.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Document> newList) {
        this.listDocument = new ArrayList<>(newList); // tạo list mới
        notifyDataSetChanged();
    }


    public List<Document> getListDocument() {
        return listDocument;
    }

    public Document getData(int position) {
        return listDocument.get(position);
    }

    public class UploadAttachmentViewHolder extends RecyclerView.ViewHolder {
        TextView tvFileName, tvUploadTime;
        ImageButton btnDelete;
        ImageView btnDownload, ivFileType;
        public UploadAttachmentViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFileType = itemView.findViewById(R.id.imgFileType);
            tvFileName = itemView.findViewById(R.id.tvFileName);
            tvUploadTime = itemView.findViewById(R.id.tvUploadTime);
            btnDownload = itemView.findViewById(R.id.btnDowload);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
