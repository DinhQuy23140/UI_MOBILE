package com.example.testui.untilities.formatter;

import com.example.testui.model.Research;

public class ResearchFormatter {
    public static Research format(Research research) {
        if (research == null) research = new Research();
        if (research.getName() == null) research.setName("Chưa có tiêu đề");
        if (research.getDescription() == null) research.setDescription("-");
        return  research;
    }
}
