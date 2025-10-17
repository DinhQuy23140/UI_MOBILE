package com.example.testui.untilities.formatter;

import com.example.testui.model.CouncilProjectDefence;

public class CouncilProjectDefenceFormatter {
    public static CouncilProjectDefence format(CouncilProjectDefence councilProjectDefence) {
        if (councilProjectDefence == null) {
            councilProjectDefence = new CouncilProjectDefence();
        }
        if (councilProjectDefence.getScore() == null) councilProjectDefence.setScore("-");
        if (councilProjectDefence.getComments() == null) councilProjectDefence.setComments("-");

        councilProjectDefence.setCouncil_member(CouncilMemberFormatter.format(councilProjectDefence.getCouncil_member()));
        councilProjectDefence.setCouncil_project(CouncilProjectFormatter.format(councilProjectDefence.getCouncil_project()));

        return councilProjectDefence;
    }
}