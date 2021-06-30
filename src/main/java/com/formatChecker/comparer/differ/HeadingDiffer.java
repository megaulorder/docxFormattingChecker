package com.formatChecker.comparer.differ;

import com.formatChecker.comparer.model.participants.HeadingsList;
import com.formatChecker.config.model.participants.Heading;

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
            headingsList.setWarningMessage(actualHeadings.getWarningMessage());
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
