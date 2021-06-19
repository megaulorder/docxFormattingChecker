package com.formatChecker.comparer.differ;

import com.formatChecker.document.model.participants.Heading;

import java.util.ArrayList;
import java.util.List;

public class HeadingDiffer {
    List<Heading> expectedHeadings;
    List<Heading> actualHeadings;
    List<Heading> headingsDifference;


    public HeadingDiffer(List<Heading> actualHeadings, List<Heading> expectedHeadings) {
        this.expectedHeadings = expectedHeadings;
        this.actualHeadings = actualHeadings;
        this.headingsDifference = getDifference();
    }

    List<Heading> getDifference() {
        if (expectedHeadings == null)
            return null;

        List<Heading> headings = new ArrayList<>();

        for (Heading expectedHeading: expectedHeadings) {
            Heading heading = new Heading();

            boolean hasHeading = actualHeadings
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

        return headings;
    }

    public List<Heading> getHeadingsDifference() {
        return headingsDifference;
    }
}
