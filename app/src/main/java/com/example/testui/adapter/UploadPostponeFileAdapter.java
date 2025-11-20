package com.example.testui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testui.R;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.UploadPostponeFile;

import java.util.Collection;
import java.util.List;

public class UploadPostponeFileAdapter extends RecyclerView.Adapter<UploadPostponeFileAdapter.UploadPostponeFileViewHolder> {

    Context context;
    List<UploadPostponeFile> listUpload;
    OnClickItem onClickItem;

    public UploadPostponeFileAdapter(Context context, List<UploadPostponeFile> listUpload, OnClickItem onClickItem) {
        this.context = context;
        this.listUpload = listUpload;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public UploadPostponeFileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_file, parent,false);
        return new UploadPostponeFileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadPostponeFileViewHolder holder, int position) {
        UploadPostponeFile uploadPostponeFile = listUpload.get(position);
        holder.tvFileName.setText(uploadPostponeFile.getPostponeProjectTermFile().getFile_name());
        holder.ivDelete.setOnClickListener(v -> onClickItem.onClickItem(position));
    }

    @Override
    public int getItemCount() {
        return listUpload.size();
    }

    public void updateData(List<UploadPostponeFile> listUploadFile) {
        this.listUpload = listUploadFile;
        notifyDataSetChanged();
    }

    public List<UploadPostponeFile> getListUpload() {
        return listUpload;
    }

    public class UploadPostponeFileViewHolder extends RecyclerView.ViewHolder {
        TextView tvFileName;
        ImageView ivDelete;
        public UploadPostponeFileViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFileName = itemView.findViewById(R.id.tvFileName);
            ivDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
