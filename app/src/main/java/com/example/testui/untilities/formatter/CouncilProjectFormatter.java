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
        councilProject.setCouncil(CouncilFormatter.format(councilProject.getCouncil()));
        councilProject.setAssignment(AssignmentFormatter.format(councilProject.getAssignment()));
        councilProject.setCouncil_member(CouncilMemberFormatter.format(councilProject.getCouncil_member()));
        List<CouncilProjectDefence> listCouncilProjectDefence = new ArrayList<>();
        if (councilProject.getCouncil_project_defences() != null) {
            for (CouncilProjectDefence councilProjectDefence : councilProject.getCouncil_project_defences()){
                councilProjectDefence = CouncilProjectDefenceFormatter.format(councilProjectDefence);
                listCouncilProjectDefence.add(councilProjectDefence);
            }
        }
        councilProject.setCouncil_project_defences(listCouncilProjectDefence);

        return councilProject;
    }
}
