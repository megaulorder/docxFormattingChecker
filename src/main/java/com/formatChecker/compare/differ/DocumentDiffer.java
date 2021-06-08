package com.formatChecker.compare.differ;

import com.formatChecker.config.model.participants.Pages;

public class DocumentDiffer {
    private static final String headerStyleName = "header";
    private static final String bodyStyleName = "body";

    Integer actualPages;
    Pages expectedPages;

    public DocumentDiffer(Integer actualPages, Pages expectedPages) {
        this.actualPages = actualPages;
        this.expectedPages = expectedPages;
    }

    public String comparePages() {
        if (actualPages >= expectedPages.getMin() && actualPages < expectedPages.getMax()) {
            return null;
        } else {
            return String.format("document has %s pages, should have %d-%d pages",
                    actualPages, expectedPages.getMin(), expectedPages.getMax());
        }
    }
}
