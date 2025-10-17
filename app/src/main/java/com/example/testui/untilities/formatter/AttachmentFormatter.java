package com.example.testui.untilities.formatter;

import com.example.testui.model.Attachment;

public class AttachmentFormatter {
    public static Attachment format(Attachment attachment) {
        if (attachment == null) {
            attachment = new Attachment();
        }
        if (attachment.getFile_name() == null) attachment.setFile_name("-");
        if (attachment.getFile_url() == null) attachment.setFile_url("");
        if (attachment.getFile_type() == null) attachment.setFile_type("-");
        if (attachment.getUpload_time() == null) attachment.setUpload_time("-");
        return attachment;
    }
}
