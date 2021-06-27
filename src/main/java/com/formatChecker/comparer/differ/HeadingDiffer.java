package com.formatChecker.comparer.differ;

import com.formatChecker.config.model.participants.Heading;
import com.formatChecker.document.model.participants.HeadingsList;

import java.util.ArrayList;
import java.util.List;

public class HeadingDiffer {
    List<Heading> expectedHeadings;
    HeadingsList actualHeadings;
    HeadingsList headingsDifference;


    public HeadingDiffer(HeadingsList actualHeadings, List<Heading> expectedHeadings) {
        this.expectedHeadings = expectedHeadings;
        this.actualHeadings = actualHeadings;
        this.headingsDifference = getDifference();
    }

    HeadingsList getDifference() {
        HeadingsList headingsList = new HeadingsList();

        if (expectedHeadings == null)
            return null;

        if (actualHeadings.getHeadings() == null) {
            headingsList.setWarningMessage("Cannot recognize headings: please update Table of Contents manually");
            return headingsList;
        }

        List<Heading> headings = new ArrayList<>();

        for (Heading expectedHeading: expectedHeadings) {
            Heading heading = new Heading();

            boolean hasHeading = actualHeadings.getHeadings()
                    .stream()
                    .anyMatch(
                            h -> h.getLevel().equals(expectedHeading.getLevel())
                                    && h.getText().equalsIgnoreCase(expectedHeading.getText()));
            if (!hasHeading)
                heading.setText(String.format(
                        "no heading %s found on level %d",
                        expectedHeading.getText(),
                        expectedHeading.getLevel()));

            headings.add(heading);
        }

        headingsList.setHeadings(headings);

        return headingsList;
    }

    public HeadingsList getHeadingsDifference() {
        return headingsDifference;
    }
}
