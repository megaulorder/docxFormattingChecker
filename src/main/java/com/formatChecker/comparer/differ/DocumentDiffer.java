package com.formatChecker.comparer.differ;

import com.formatChecker.config.model.participants.Pages;

public class DocumentDiffer {
    Integer actualPages;
    Pages expectedPages;
    String difference;

    public DocumentDiffer(Integer actualPages, Pages expectedPages) {
        this.actualPages = actualPages;
        this.expectedPages = expectedPages;
        this.difference = comparePages();
    }

    String comparePages() {
        if (actualPages == null)
            return "Could not read number of pages";
        if (expectedPages == null)
            return null;
        if (actualPages >= expectedPages.getMin() && actualPages < expectedPages.getMax()) {
            return null;
        } else {
            return String.format("document has %s pages, should have %d-%d pages",
                    actualPages, expectedPages.getMin(), expectedPages.getMax());
        }
    }

    public String getDifference() {
        return difference;
    }
}
