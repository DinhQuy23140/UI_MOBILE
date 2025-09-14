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
import com.example.testui.model.ReportFile;
import com.example.testui.model.UploadFile;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReportFileAdapter extends RecyclerView.Adapter<ReportFileAdapter.ReportFileViewHolder> {

    Context context;
    List<ReportFile> listReportFile;
    OnClickItem onClickItem;

    public ReportFileAdapter(Context context, List<ReportFile> listReportFile, OnClickItem onClickItem) {
        this.context = context;
        this.listReportFile = listReportFile;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public ReportFileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report_file, parent, false);
        return new ReportFileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportFileViewHolder holder, int position) {
        ReportFile reportFile = listReportFile.get(position);
        holder.tvFileName.setText(reportFile.getFile_name());
        OffsetDateTime odt = OffsetDateTime.parse(reportFile.getCreated_at());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        String formatted = odt.format(formatter);
        holder.tvUploadTime.setText(formatted);
        holder.btnDownload.setOnClickListener(click -> {
            onClickItem.onClickItem(position);
        });
    }

    @Override
    public int getItemCount() {
        return listReportFile.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<ReportFile> newList) {
        this.listReportFile = new ArrayList<>(newList); // tạo list mới
        notifyDataSetChanged();
    }


    public List<ReportFile> getListDocument() {
        return listReportFile;
    }

    public ReportFile getData(int position) {
        return listReportFile.get(position);
    }

    public class ReportFileViewHolder extends RecyclerView.ViewHolder {
        TextView tvFileName, tvUploadTime;
        ImageView btnDownload, ivFileType;
        public ReportFileViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFileType = itemView.findViewById(R.id.imgFileType);
            tvFileName = itemView.findViewById(R.id.tvFileName);
            tvUploadTime = itemView.findViewById(R.id.tvUploadTime);
            btnDownload = itemView.findViewById(R.id.btnDowload);
        }
    }
}
