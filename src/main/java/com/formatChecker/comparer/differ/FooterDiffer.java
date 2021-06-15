package com.formatChecker.comparer.differ;

import com.formatChecker.config.model.participants.Footer;

public class FooterDiffer implements Differ {
    Footer actualFooter, expectedFooter, differenceFooter;

    public FooterDiffer(Footer actualFooter, Footer expectedFooter) {
        this.actualFooter = actualFooter;
        this.expectedFooter = expectedFooter;
        this.differenceFooter = new Footer();
    }

    public Footer getFooterDifference() {
        if (actualFooter == null && expectedFooter != null) {
            differenceFooter.setIsPresent(false);
        }
        else if (actualFooter != null && expectedFooter != null) {
            differenceFooter.setIsPresent(true);

            differenceFooter.setType(checkStringParameter(
                    actualFooter.getType(), expectedFooter.getType(), "footer type"));
            differenceFooter.setAlignment(checkStringParameter(
                    actualFooter.getAlignment(), expectedFooter.getAlignment(), "footer alignment"));
        }
        else
            return null;

        return differenceFooter;
    }
}
