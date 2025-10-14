package com.example.testui.ViewModel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.testui.R;
import com.example.testui.model.Status;

public class ProgressLogDetailViewModel extends ViewModel {
    Context context;

    public ProgressLogDetailViewModel(Context context) {
        this.context = context;
    }

    public Status loadStatusProgress(String status) {
        switch (status) {
            case "pending":
                return new Status(R.drawable.bg_task_icon, "Chưa bắt đầu");
            case "in_progress":
                return new Status(R.drawable.bg_primary_button, "Đang tiến hành");
            case "not_completed":
                return new Status(R.drawable.bg_icon_success, "Chưa hoàn thành");
            case "completed":
                return new Status(R.drawable.bg_icon_accent, "Hoàn thành");
            default:
                return new Status(R.drawable.bg_task_icon, "Chưa bắt đầu");
        }
    }

    public Status loadInstructorStatus(String status) {
        switch(status) {
            case "pending":
                return new Status(R.drawable.bg_icon_primary, "Chưa duyệt");
            case "approved":
                return new Status(R.drawable.bg_icon_accent, "Đạt");
            case "need_editing":
                return new Status(R.drawable.bg_icon_success, "Cần chỉnh sửa");
            case "not_achieved":
                return new Status(R.drawable.bg_status_pending, "Chưa đạt");
            default:
                return new Status(R.drawable.bg_icon_primary, "Chưa duyệt");
        }
    }
}
