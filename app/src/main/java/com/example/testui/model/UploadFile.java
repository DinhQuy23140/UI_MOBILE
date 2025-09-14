package com.example.testui.model;

import android.net.Uri;

import java.io.File;

public class UploadFile {
    String id;
    File file;
    ReportFile reportFile;

    public UploadFile(String id, File file, ReportFile reportFile) {
        this.id = id;
        this.file = file;
        this.reportFile = reportFile;
    }

    public UploadFile(File file, ReportFile reportFile) {
        this.file = file;
        this.reportFile = reportFile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReportFile getReportFile() {
        return reportFile;
    }

    public void setReportFile(ReportFile reportFile) {
        this.reportFile = reportFile;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
