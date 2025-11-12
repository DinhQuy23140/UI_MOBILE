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
import com.example.testui.model.CommentLog;

import java.util.List;

public class CommentLogAdapter extends RecyclerView.Adapter<CommentLogAdapter.CommentLogViewHolder> {

    Context context;
    List<CommentLog> listCommentLog;
    OnClickItem onClickItem;

    public CommentLogAdapter(Context context, List<CommentLog> listCommentLog, OnClickItem onClickItem) {
        this.context = context;
        this.listCommentLog = listCommentLog;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public CommentLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_log, parent, false);
        return new CommentLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentLogViewHolder holder, int position) {
        CommentLog commentLog = listCommentLog.get(position);
        holder.tvUsername.setText(commentLog.getContent());
        holder.tvTime.setText(commentLog.getCreated_at());
        holder.tvCommentContent.setText(commentLog.getCreated_at());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CommentLogViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvTime, tvCommentContent;
        public CommentLogViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUserName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvCommentContent = itemView.findViewById(R.id.tvCommentContent);
        }
    }
}
