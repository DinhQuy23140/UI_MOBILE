package com.example.testui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testui.R;
import com.example.testui.interfaces.OnClickItem;
import com.example.testui.model.InstructorLogStatus;
import com.example.testui.model.ProgressLog;
import com.example.testui.model.StudentLogStatus;

import org.w3c.dom.Text;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ProcessLogViewHolder holder, int position) {
        ProgressLog progressLog = listProcessLog.get(position);
        holder.tvTaskTitle.setText(progressLog.getTitle());

        String startDate = progressLog.getStart_date_time(); // "2025-10-12T14:13:46.000000Z"
        String endDate = progressLog.getEnd_date_time();     // "2025-10-13T08:05:10.000000Z"

        try {
            // Chuyển chuỗi ISO 8601 thành Instant
            Instant instantStart = Instant.parse(startDate);
            Instant instantEnd = Instant.parse(endDate);

            // Lấy LocalDate (chỉ phần ngày) theo timezone của hệ thống (VD: Asia/Ho_Chi_Minh)
            LocalDate localDateStart = instantStart.atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate localDateEnd = instantEnd.atZone(ZoneId.systemDefault()).toLocalDate();
            long daysBetween = ChronoUnit.DAYS.between(localDateStart, localDateEnd);

            // Format lại thành dd/MM/yyyy
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedStart = localDateStart.format(formatter);
            String formattedEnd = localDateEnd.format(formatter);

            // Hiển thị
            holder.tvTaskDateRange.setText(formattedStart + " - " + formattedEnd);
            holder.tvCountDate.setText(daysBetween + " ngày");
        } catch (Exception e) {
            e.printStackTrace();
            holder.tvTaskDateRange.setText("—");
        }

        holder.tvTaskDescription.setText(progressLog.getDescription());
        holder.tvTaskStatus.setText(progressLog.studentStatusToString());
        holder.tvFeedback.setText(progressLog.instructorStatusToString());
        holder.itemView.setOnClickListener(click -> {
            onClickItem.onClickItem(position);
        });
        holder.tvTaskStatus.setBackground(context.getDrawable(progressLog.getBackgroundStudent()));
        holder.ln_instructor_status.setBackground(context.getDrawable(progressLog.getBackgroundInstructor()));
    }

    public ProgressLog getItem(int position) {
        return listProcessLog.get(position);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<ProgressLog> newList) {
        this.listProcessLog.clear();
        this.listProcessLog.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listProcessLog.size();
    }

    public class ProcessLogViewHolder extends RecyclerView.ViewHolder {
        TextView tvTaskTitle, tvTaskDateRange, tvTaskDescription, tvTaskStatus, tvFeedback, tvCountDate;
        LinearLayout ln_instructor_status;
        public ProcessLogViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskTitle = itemView.findViewById(R.id.tvTaskTitle);
            tvTaskDateRange = itemView.findViewById(R.id.tvTaskDateRange);
            tvTaskDescription = itemView.findViewById(R.id.tvTaskDescription);
            tvTaskStatus = itemView.findViewById(R.id.tvTaskStatus);
            tvFeedback = itemView.findViewById(R.id.tvFeedback);
            ln_instructor_status = itemView.findViewById(R.id.background_instructor_status);
            tvCountDate = itemView.findViewById(R.id.tv_count_date);
        }
    }
}
