package com.example.testui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testui.R;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.InstructorLogStatus;
import com.example.testui.model.ProgressLog;
import com.example.testui.model.StudentLogStatus;

import org.w3c.dom.Text;

import java.util.List;

public class ProcessLogAdapter extends RecyclerView.Adapter<ProcessLogAdapter.ProcessLogViewHolder> {

    Context context;
    List<ProgressLog> listProcessLog;
    OnClickItem onClickItem;

    public ProcessLogAdapter(Context context, List<ProgressLog> listProcessLog, OnClickItem onClickItemn) {
        this.context = context;
        this.listProcessLog = listProcessLog;
        this.onClickItem = onClickItemn;
    }

    @NonNull
    @Override
    public ProcessLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_progress_log, parent, false);
        return new ProcessLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProcessLogViewHolder holder, int position) {
        ProgressLog progressLog = listProcessLog.get(position);
        holder.tvTaskTitle.setText(progressLog.getTitle());
        holder.tvTaskDateRange.setText(progressLog.getDateRange());
        holder.tvTaskDescription.setText(progressLog.getDescription());
        holder.tvTaskStatus.setText(convertStudentStatus(progressLog.getStudent_status()));
        holder.tvFeedback.setText(convertInstructorStatus(progressLog.getInstructor_status()));
        holder.itemView.setOnClickListener(click -> {
            onClickItem.onClickItem(position);
        });
    }

    public ProgressLog getItem(int position) {
        return listProcessLog.get(position);
    }

    public String convertStudentStatus(String student_status) {
        if (student_status == null || student_status.isEmpty()) {
            return "Trạng thái: Không xác định";
        }

        switch (student_status) {
            case "chua_bat_dau":
            case "NOT_STARTED":
                return "Trạng thái: " + StudentLogStatus.NOT_STARTED.getDisplayName();

            case "dang_thuc_hien":
            case "IN_PROGRESS":
                return "Trạng thái: " + StudentLogStatus.IN_PROGRESS.getDisplayName();

            case "chua_hoan_thanh":
            case "INCOMPLETE":
                return "Trạng thái: " + StudentLogStatus.INCOMPLETE.getDisplayName();

            case "da_hoan_thanh":
            case "COMPLETED":
                return "Trạng thái: " + StudentLogStatus.COMPLETED.getDisplayName();

            default:
                return "Trạng thái: " + student_status;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<ProgressLog> newList) {
        this.listProcessLog.clear();
        this.listProcessLog.addAll(newList);
        notifyDataSetChanged();
    }


    public String convertInstructorStatus(String instructor_status) {
        switch (instructor_status) {
            case "null":
                return String.valueOf(InstructorLogStatus.valueOf(""));
            case "dat":
                case "PASSED":
                    return InstructorLogStatus.PASSED.getDisplayName();
            case "chua_dat":
                case "FAILED":
                return InstructorLogStatus.FAILED.getDisplayName();
            case "can_chinh_sua":
                case "NEEDS_REVISION":
                return InstructorLogStatus.NEEDS_REVISION.getDisplayName();
            default:
                return instructor_status;
        }
    }

    @Override
    public int getItemCount() {
        return listProcessLog.size();
    }

    public class ProcessLogViewHolder extends RecyclerView.ViewHolder {
        TextView tvTaskTitle, tvTaskDateRange, tvTaskDescription, tvTaskStatus, tvFeedback;
        public ProcessLogViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskTitle = itemView.findViewById(R.id.tvTaskTitle);
            tvTaskDateRange = itemView.findViewById(R.id.tvTaskDateRange);
            tvTaskDescription = itemView.findViewById(R.id.tvTaskDescription);
            tvTaskStatus = itemView.findViewById(R.id.tvTaskStatus);
            tvFeedback = itemView.findViewById(R.id.tvFeedback);
        }
    }
}
