package com.example.testui.untilities.formatter;

import com.example.testui.model.Marjor;

public class MarjorFormatter {
    public static Marjor format(Marjor marjor) {
        if (marjor == null) {
            marjor = new Marjor();
        }

        if (marjor.getCode() == null) marjor.setCode("-");
        if (marjor.getDescription() == null) marjor.setDescription("-");
        if (marjor.getName() == null) marjor.setName("-");
        return marjor;
    }
}
