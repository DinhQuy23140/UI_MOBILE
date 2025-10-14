package com.example.testui.model;

public class Attachment {
    private String id;            // ID tệp đính kèm
    private String progress_log_id; // ID lớp học (process log)
    private String file_name;      // Tên tệp (hiển thị)
    private String file_url;       // Đường dẫn đến file (URL hoặc Firebase path)
    private String file_type;      // Loại file: pdf, image, doc, etc.
    private String upload_time;      // Thời gian upload (timestamp)
    private String uploader_id;    // ID người upload (sinh viên hoặc giảng viên)
    private String created_at;
    private String updated_at;

    public Attachment(String id, String progress_log_id, String file_name, String file_url, String file_type, String upload_time, String uploader_id) {
        this.id = id;
        this.progress_log_id = progress_log_id;
        this.file_name = file_name;
        this.file_url = file_url;
        this.file_type = file_type;
        this.upload_time = upload_time;
        this.uploader_id = uploader_id;
    }

    public Attachment(String file_name, String file_type, String file_url, String progress_log_id, String upload_time, String uploader_id) {
        this.file_name = file_name;
        this.file_type = file_type;
        this.file_url = file_url;
        this.progress_log_id = progress_log_id;
        this.upload_time = upload_time;
        this.uploader_id = uploader_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProgress_log_id() {
        return progress_log_id;
    }

    public void setProgress_log_id(String progress_log_id) {
        this.progress_log_id = progress_log_id;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public String getUploader_id() {
        return uploader_id;
    }

    public void setUploader_id(String uploader_id) {
        this.uploader_id = uploader_id;
    }
}
    