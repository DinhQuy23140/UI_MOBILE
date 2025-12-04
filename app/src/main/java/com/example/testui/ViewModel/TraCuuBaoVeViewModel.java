package com.example.testui.ViewModel;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testui.R;
import com.example.testui.model.Assignment;
import com.example.testui.model.AssignmentSupervisor;
import com.example.testui.model.Council;
import com.example.testui.model.CouncilsMember;
import com.example.testui.model.Project;
import com.example.testui.model.ReportFile;
import com.example.testui.model.Status;
import com.example.testui.model.Supervisor;
import com.example.testui.repository.ReportFileRepository;
import com.example.testui.untilities.Constants;
import com.example.testui.untilities.formatter.DateFormatter;
import com.example.testui.untilities.formatter.ProjectFormatter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TraCuuBaoVeViewModel extends ViewModel {
    private Context context;
    ReportFileRepository reportFileRepository;
    MutableLiveData<Boolean> isCreateSuccess;
    public TraCuuBaoVeViewModel(Context context) {
        this.context = context;
        reportFileRepository = new ReportFileRepository(context);
        isCreateSuccess = reportFileRepository.getIsCreateSuccess();
    }

    public List<Supervisor> convertListSupervisor(List<CouncilsMember> listCouncilMember) {
        List<Supervisor> listSupervisor = new ArrayList<>();
        for (CouncilsMember councilsMember : listCouncilMember) {
            listSupervisor.add(councilsMember.getSupervisor());
        }
        return listSupervisor;
    }

    public List<Supervisor> convertListBaseSupervisor(List<AssignmentSupervisor> listAssignmentSupervisor) {
        List<Supervisor> listSupervisor = new ArrayList<>();
        for (AssignmentSupervisor assignmentSupervisor : listAssignmentSupervisor) {
            listSupervisor.add(assignmentSupervisor.getSupervisor());
        }
        return listSupervisor;
    }

    public Council getCouncilSafe(Assignment assignment) {
        if (assignment == null || assignment.getCouncil_project() == null) return null;
        return assignment.getCouncil_project().getCouncil();
    }
    public MutableLiveData<Council> councilDisplay = new MutableLiveData<>();

    public void setCouncil(Council council) {
        Council model = new Council();
        model.setName(council != null && council.getName() != null ? council.getName() : "—");
        model.setId(council != null && council.getId() != null ? council.getId() : "—");
        model.getDepartment().setName(council != null && council.getDepartment() != null ? council.getDepartment().getName() : "—");
        model.setDescription(council != null && council.getDescription() != null ? council.getDescription() : "—");
        model.setAddress(council != null && council.getAddress() != null ? council.getAddress() : "—");
        model.setDate(council != null && council.getDate() != null ? DateFormatter.formatDate(council.getDate()) : "—");
        councilDisplay.setValue(model);
    }

    public String safeFileName(String fileName) {
        return fileName.replaceAll("\\s+", "_").toLowerCase().replaceAll("[^a-zA-Z0-9._-]", "");
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex >= 0) {
                        result = cursor.getString(nameIndex);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public File getFileFromUri(Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        File tempFile = File.createTempFile("upload", ".tmp", context.getCacheDir());
        FileOutputStream out = new FileOutputStream(tempFile);

        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.close();
        inputStream.close();
        return tempFile;
    }

    public void uploadReportFile(ReportFile reportFile) {
        reportFileRepository.uploadReportFile(reportFile);
    }

    public MutableLiveData<Boolean> getIsCreateSuccess() {
        return isCreateSuccess;
    }

    public boolean isUploadReport(Assignment assignment) {
        Project project = ProjectFormatter.format(assignment.getProject());
        List<ReportFile> listReportFile = project.getReport_files();
        if (listReportFile == null && listReportFile.isEmpty()) return false;
        else {
            for (ReportFile reportFile : listReportFile) {
                if (reportFile.getType_report().equals(Constants.KEY_TYPE_REPORT_REPORT_COUNCIL)) return true;
            }
        }
        return false;
    }

    public Status loadStatus(boolean result) {
        Status status;
        if (result) {
            status = new Status(R.drawable.bg_success_message, "Đã nộp");
        } else {
            status = new Status(R.drawable.bg_task_icon, "Chưa nộp");
        }
        return status;
    }
}
