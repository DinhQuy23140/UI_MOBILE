package com.example.testui.untilities.formatter;

import com.example.testui.model.ProjectTerm;
import com.example.testui.model.StageTimeline;

import java.util.ArrayList;
import java.util.List;

public class ProjectTermFormatte {
    public static ProjectTerm format(ProjectTerm projectTerm) {
        if (projectTerm == null) {
            projectTerm = new ProjectTerm();
        }

        if (projectTerm.getStage() == null) projectTerm.setStage("-");
        if (projectTerm.getStatus() == null) projectTerm.setStatus("pending");
        if (projectTerm.getDescription() == null) projectTerm.setDescription("Chưa có");
        if (projectTerm.getStart_date() == null) projectTerm.setStart_date("-");
        if (projectTerm.getEnd_date() == null) projectTerm.setEnd_date("-");
        if (projectTerm.getAcademy_year() == null) projectTerm.setAcademy_year(AcademyYearFormatter.format(projectTerm.getAcademy_year()));
        if (projectTerm.getStage_timelines() == null) {
            List<StageTimeline> listStage = new ArrayList<>();
            for (StageTimeline stageTimeline : listStage) {
                StageTimelineFormatter.format(stageTimeline);
                listStage.add(stageTimeline);
            }
            projectTerm.setStage_timelines(listStage);
        }
        return  projectTerm;
    }
}
