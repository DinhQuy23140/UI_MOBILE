package com.example.testui.untilities.formatter;

import com.example.testui.model.StageTimeline;

public class StageTimelineFormatter {
    public static StageTimeline format(StageTimeline stageTimeline) {
        if (stageTimeline == null){
            stageTimeline = new StageTimeline();
        }

        if (stageTimeline.getDescription() == null) stageTimeline.setDescription("Chưa có mô tả");
//        if (stageTimeline.getStatus() == null) stageTimeline.setStatus("pending");
        if (stageTimeline.getNumber_of_rounds() == null) stageTimeline.setNumber_of_rounds("-");
        if (stageTimeline.getStart_date() == null) stageTimeline.setStart_date("-");
        if (stageTimeline.getEnd_date() == null) stageTimeline.setEnd_date("-");
//        if (stageTimeline.getProject_id() == null) stageTimeline.setProject_id("-");
        return stageTimeline;
    }
}
