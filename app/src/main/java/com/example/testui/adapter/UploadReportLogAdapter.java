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
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.interfaces.UploadDocumentClick;
import com.example.testui.model.UploadAttachment;
import com.example.testui.model.UploadFile;

import java.util.ArrayList;
import java.util.List;

public class UploadReportLogAdapter extends RecyclerView.Adapter<UploadReportLogAdapter.UploadReportLogViewHolder> {
    Context context;
    List<UploadAttachment> listUploadAttachment;
    UploadDocumentClick uploadAttachmentClick;

    public UploadReportLogAdapter(Context context, List<UploadAttachment> listUploadAttachment, UploadDocumentClick uploadAttachmentClick) {
        this.context = context;
        this.listUploadAttachment = listUploadAttachment;
        this.uploadAttachmentClick = uploadAttachmentClick;
    }

    @NonNull
    @Override
    public UploadReportLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upload_attachment, parent, false);
        return new UploadReportLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadReportLogViewHolder holder, int position) {
        UploadAttachment uploadAttachment = listUploadAttachment.get(position);
        holder.tvFileName.setText(uploadAttachment.getAttachment().getFile_name());
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
        return listUploadAttachment.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<UploadAttachment> newList) {
        this.listUploadAttachment = new ArrayList<>(newList); // tạo list mới
        notifyDataSetChanged();
    }


    public List<UploadAttachment> getListDocument() {
        return listUploadAttachment;
    }

    public UploadAttachment getData(int position) {
        return listUploadAttachment.get(position);
    }

    public class UploadReportLogViewHolder extends RecyclerView.ViewHolder {
        TextView tvFileName, tvUploadTime;
        ImageButton btnDelete;
        ImageView btnDownload, ivFileType;
        public UploadReportLogViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFileType = itemView.findViewById(R.id.imgFileType);
            tvFileName = itemView.findViewById(R.id.tvFileName);
            tvUploadTime = itemView.findViewById(R.id.tvUploadTime);
            btnDownload = itemView.findViewById(R.id.btnDowload);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
