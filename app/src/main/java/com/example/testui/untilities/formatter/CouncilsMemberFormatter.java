package com.example.testui.untilities.formatter;

import com.example.testui.model.CouncilsMember;

public class CouncilsMemberFormatter {
    public static CouncilsMember format(CouncilsMember councilsMember) {
        if (councilsMember == null) {
            councilsMember = new CouncilsMember();
        }

        if (councilsMember.getRole() == null) councilsMember.setRole("1");
        councilsMember.setSupervisor(SupervisorFormatter.format(councilsMember.getSupervisor()));
        councilsMember.setCouncil(CouncilFormatter.format(councilsMember.getCouncil()));

        return councilsMember;
    }
}
