package com.example.testui.untilities.formatter;

import com.example.testui.model.Council;
import com.example.testui.model.CouncilsMember;

import java.util.ArrayList;
import java.util.List;

public class CouncilFormatter {
    public static Council format(Council council) {
        if (council == null) {
            council = new Council();
        }

        if (council.getName() == null) council.setName("-");
        if (council.getCode() == null) council.setCode("-");
        if (council.getDate() == null) council.setDate("-");
        if (council.getAddress() == null) council.setAddress("-");
        if (council.getDescription() == null) council.setDescription("-");
        if (council.getStatus() == null) council.setStatus("pending");
        return council;
    }
}
