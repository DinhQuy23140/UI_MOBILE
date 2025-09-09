package com.example.testui.model;

import android.net.Uri;

import java.io.File;

public class UploadFile extends Document {
    String id;
    File file;

    public UploadFile(String fileName, String id, String projectId, String timeUpload, TypeDocument typeDocument, String typeFile, String url, File file, String id1) {
        super(fileName, id, projectId, timeUpload, typeDocument, typeFile, url);
        this.file = file;
        this.id = id1;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
