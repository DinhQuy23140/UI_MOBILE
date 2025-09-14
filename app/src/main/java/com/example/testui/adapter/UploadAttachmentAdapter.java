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
import com.example.testui.model.ReportFile;
import com.example.testui.model.UploadFile;

import java.util.ArrayList;
import java.util.List;

public class UploadAttachmentAdapter extends RecyclerView.Adapter<UploadAttachmentAdapter.UploadAttachmentViewHolder> {

    Context context;
    List<UploadFile> listUploadFile;
    UploadDocumentClick uploadAttachmentClick;

    public UploadAttachmentAdapter(Context context, List<UploadFile> listUploadFile, UploadDocumentClick uploadAttachmentClick) {
        this.context = context;
        this.listUploadFile = listUploadFile;
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
        UploadFile uploadFile = listUploadFile.get(position);
        String fileType = uploadFile.getReportFile().getFile_type();
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
        holder.tvFileName.setText(uploadFile.getReportFile().getFile_name());
//        holder.tvUploadTime.setText(uploadFile.getReportFile().getCreate_at());
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
        return listUploadFile.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<UploadFile> newList) {
        this.listUploadFile = new ArrayList<>(newList); // tạo list mới
        notifyDataSetChanged();
    }


    public List<UploadFile> getListDocument() {
        return listUploadFile;
    }

    public UploadFile getData(int position) {
        return listUploadFile.get(position);
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
