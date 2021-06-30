package com.formatChecker.comparer.model.participants;

import com.formatChecker.config.model.participants.Heading;

import java.util.List;

public class HeadingsList {
    String warningMessage;

    List<Heading> headings;

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    public List<Heading> getHeadings() {
        return headings;
    }

    public void setHeadings(List<Heading> headings) {
        this.headings = headings;
    }
}
