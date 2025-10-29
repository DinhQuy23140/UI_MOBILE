package com.example.testui.model;

import java.io.File;

public class UploadAttachment {
    String id;
    File file;
    Attachment attachment;

    public UploadAttachment(File file, Attachment attachment) {
        this.file = file;
        this.attachment = attachment;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
