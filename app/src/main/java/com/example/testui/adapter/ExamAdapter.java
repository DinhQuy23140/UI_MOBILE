package com.example.testui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testui.R;
import com.example.testui.model.Exam;

import java.util.ArrayList;
import java.util.List;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ExamViewHolder> {

    List<String> listExam;
    Context context;

    public ExamAdapter(Context context, List<String> listExam) {
        this.context = context;
        this.listExam = listExam;
    }

    @NonNull
    @Override
    public ExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_exam, parent, false);
        return new ExamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamViewHolder holder, int position) {
        String exam = listExam.get(position);
        holder.tvExamNumber.setText(exam);
    }

    @Override
    public int getItemCount() {
        return listExam.size();
    }

    public class ExamViewHolder extends RecyclerView.ViewHolder {
        TextView tvExamNumber;
        public ExamViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExamNumber = itemView.findViewById(R.id.txtExamNumber);
        }
    }
}
