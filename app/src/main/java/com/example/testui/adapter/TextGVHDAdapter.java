package com.example.testui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.Teacher;

import java.util.List;

public class TextGVHDAdapter extends RecyclerView.Adapter<TextGVHDAdapter.TextGVHDViewHolder> {

    Context context;
    List<Teacher> listTeacher;
    OnClickItem onClickItem;

    public TextGVHDAdapter(Context context, List<Teacher> listTeacher, OnClickItem onClickItem) {
        this.context = context;
        this.listTeacher = listTeacher;
        this.onClickItem = onClickItem;
    }

    public void updateData(List<Teacher> listTeacher) {
        this.listTeacher = listTeacher;
        notifyDataSetChanged();
    }

    public Teacher getData(int position) {
        return listTeacher.get(position);
    }

    @NonNull
    @Override
    public TextGVHDViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TextGVHDViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class TextGVHDViewHolder extends RecyclerView.ViewHolder {

        public TextGVHDViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
