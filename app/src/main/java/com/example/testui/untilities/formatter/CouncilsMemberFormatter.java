package com.example.testui.untilities.formatter;

import com.example.testui.model.CouncilsMember;

public class CouncilsMemberFormatter {
    public static CouncilsMember format(CouncilsMember councilsMember) {
        if (councilsMember == null) {
            councilsMember = new CouncilsMember();
        }

        if (councilsMember.getRole() == null) councilsMember.setRole("1");

        return councilsMember;
    }
}
