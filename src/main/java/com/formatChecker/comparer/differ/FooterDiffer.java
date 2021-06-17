package com.formatChecker.comparer.differ;

import com.formatChecker.config.model.participants.Footer;

public class FooterDiffer implements Differ {
    Footer actualFooter;
    Footer expectedFooter;
    Footer differenceFooter;

    public FooterDiffer(Footer actualFooter, Footer expectedFooter) {
        this.actualFooter = actualFooter;
        this.expectedFooter = expectedFooter;
        this.differenceFooter = getDifference();
    }

    Footer getDifference() {
        Footer footer = new Footer();

        if (actualFooter == null && expectedFooter != null) {
            footer.setErrorMessage(
                    String.format(
                            "add footer with %s content aligned by %s",
                            expectedFooter.getType(),
                            expectedFooter.getAlignment()));
        } else if (actualFooter != null && expectedFooter != null) {
            footer.setErrorMessage("");

            footer.setType(checkStringParameter(
                    actualFooter.getType(),
                    expectedFooter.getType(),
                    "footer type"));

            footer.setAlignment(checkStringParameter(
                    actualFooter.getAlignment(),
                    expectedFooter.getAlignment(),
                    "footer alignment"));
        } else
            return null;

        return footer;
    }

    public Footer getDifferenceFooter() {
        return differenceFooter;
    }
}
