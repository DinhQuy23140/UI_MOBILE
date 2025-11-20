package com.example.testui.untilities.formatter;

import com.example.testui.model.PostponeProjectTerm;

public class PostponeProjectTermFormatter {
    public static PostponeProjectTerm format(PostponeProjectTerm postponeProjectTerm) {
        if (postponeProjectTerm == null) {
            postponeProjectTerm = new PostponeProjectTerm();
        }
        if (postponeProjectTerm.getNote() == null) postponeProjectTerm.setNote("-");
        return  postponeProjectTerm;
    }
}
