package com.example.testui.untilities.formatter;

import com.example.testui.model.CouncilProjectDefence;

public class CouncilProjectDefenceFormatter {
    public static CouncilProjectDefence format(CouncilProjectDefence councilProjectDefence) {
        if (councilProjectDefence == null) {
            councilProjectDefence = new CouncilProjectDefence();
        }
        if (councilProjectDefence.getScore() == null) councilProjectDefence.setScore("-");
        if (councilProjectDefence.getComments() == null) councilProjectDefence.setComments("-");

        return councilProjectDefence;
    }
}