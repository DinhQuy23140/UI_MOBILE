package com.example.testui.model;

public class Document {
    String id, fileName, timeUpload, typeFile, projectId, url;
    TypeDocument typeDocument;

    public Document(String fileName, String id, String projectId, String timeUpload, TypeDocument typeDocument, String typeFile, String url) {
        this.fileName = fileName;
        this.id = id;
        this.projectId = projectId;
        this.timeUpload = timeUpload;
        this.typeDocument = typeDocument;
        this.typeFile = typeFile;
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTimeUpload() {
        return timeUpload;
    }

    public void setTimeUpload(String timeUpload) {
        this.timeUpload = timeUpload;
    }

    public TypeDocument getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(TypeDocument typeDocument) {
        this.typeDocument = typeDocument;
    }

    public String getTypeFile() {
        return typeFile;
    }

    public void setTypeFile(String typeFile) {
        this.typeFile = typeFile;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
