package com.example.testui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testui.R;
import com.example.testui.model.Question;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    List<Question> listQuestion;
    Context context;

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listQuestion.size();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
