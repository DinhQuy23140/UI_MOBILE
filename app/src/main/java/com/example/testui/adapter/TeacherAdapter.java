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
import com.example.testui.model.Teacher;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHoler> {
    Context context;
    List<Teacher> listTeacher;
    OnClickItem onClickItem;

    public TeacherAdapter(Context context, List<Teacher> listTeacher, OnClickItem onClickItem) {
        this.context = context;
        this.listTeacher = listTeacher;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public TeacherViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gvhd, parent, false);
        return new TeacherViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHoler holder, int position) {
        Teacher teacher = listTeacher.get(position);
        holder.tvGiangVienName.setText(teacher.getUser().getFullname());
        holder.tvHocVi.setText(teacher.getDegree());
        holder.itemView.setOnClickListener(gvhddetail -> onClickItem.onClickItem(position));
    }

    @Override
    public int getItemCount() {
        return listTeacher.size();
    }

    public void updateData(List<Teacher> listTeacher) {
        this.listTeacher = listTeacher;
        notifyDataSetChanged();
    }

    public class TeacherViewHoler extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvGiangVienName, tvHocVi, tvDonVi;
        public TeacherViewHoler(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvGiangVienName = itemView.findViewById(R.id.tvGiangVienName);
            tvHocVi = itemView.findViewById(R.id.tvHocVi);
            tvDonVi = itemView.findViewById(R.id.tvDonVi);
        }
    }
}
