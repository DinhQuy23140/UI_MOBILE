package com.example.testui.model;

public class PostponeProjectTermFile {
    String id, postpone_project_term_id, file_name, file_path, file_type;
    PostponeProjectTerm postponeProjectTerm;

    public PostponeProjectTermFile(String file_name, String file_path, String file_type, String postpone_project_term_id) {
        this.file_name = file_name;
        this.file_path = file_path;
        this.file_type = file_type;
        this.postpone_project_term_id = postpone_project_term_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostpone_project_term_id() {
        return postpone_project_term_id;
    }

    public void setPostpone_project_term_id(String postpone_project_term_id) {
        this.postpone_project_term_id = postpone_project_term_id;
    }

    public PostponeProjectTerm getPostponeProjectTerm() {
        return postponeProjectTerm;
    }

    public void setPostponeProjectTerm(PostponeProjectTerm postponeProjectTerm) {
        this.postponeProjectTerm = postponeProjectTerm;
    }
}
