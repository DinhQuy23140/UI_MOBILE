package com.example.testui.untilities.formatter;

import com.example.testui.model.ProposedTopic;

public class ProposedTopicFormatter {
    public static ProposedTopic format(ProposedTopic proposedTopic) {
        if (proposedTopic == null) proposedTopic = new ProposedTopic();

        if (proposedTopic.getTitle() == null) proposedTopic.setTitle("-");
        if (proposedTopic.getDescription() == null) proposedTopic.setDescription("-");

        return proposedTopic;
    }
}
