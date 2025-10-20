package com.example.testui.untilities.formatter;

import com.example.testui.model.CouncilProject;
import com.example.testui.model.CouncilProjectDefence;

import java.util.ArrayList;
import java.util.List;

public class CouncilProjectFormatter {
    public static CouncilProject format(CouncilProject councilProject) {
        if (councilProject == null) {
            councilProject = new CouncilProject();
        }

        if (councilProject.getRoom() == null) councilProject.setRoom("-");
        if (councilProject.getDate() == null) councilProject.setDate("-");
        if (councilProject.getTime() == null) councilProject.setTime("-");
        if (councilProject.getReview_score() == null) councilProject.setReview_score("-");
        return councilProject;
    }
}
