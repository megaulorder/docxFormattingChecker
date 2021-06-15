package com.formatChecker.comparer.differ;

import com.formatChecker.comparer.model.Difference;
import com.formatChecker.document.model.Heading;

import java.util.List;

public class HeadingDiffer {
    List<Heading> requiredHeadings, actualHeadings;
    Difference difference;

    public HeadingDiffer(List<Heading> requiredHeadings, List<Heading> actualHeadings, Difference difference) {
        this.requiredHeadings = requiredHeadings;
        this.actualHeadings = actualHeadings;
        this.difference = difference;
    }
}
