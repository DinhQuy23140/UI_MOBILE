package com.example.testui.model;

import java.io.File;

public class UploadPostponeFile {
    String id;
    File file;
    PostponeProjectTermFile postponeProjectTermFile;

    public UploadPostponeFile(File file, PostponeProjectTermFile postponeProjectTermFile) {
        this.file = file;
        this.postponeProjectTermFile = postponeProjectTermFile;
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

    public PostponeProjectTermFile getPostponeProjectTermFile() {
        return postponeProjectTermFile;
    }

    public void setPostponeProjectTermFile(PostponeProjectTermFile postponeProjectTermFile) {
        this.postponeProjectTermFile = postponeProjectTermFile;
    }
}
