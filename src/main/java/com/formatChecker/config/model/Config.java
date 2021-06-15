package com.formatChecker.config.model;

import com.formatChecker.config.model.participants.Footer;
import com.formatChecker.config.model.participants.Pages;
import com.formatChecker.config.model.participants.Section;
import com.formatChecker.config.model.participants.Style;
import com.formatChecker.document.model.Heading;

import java.util.HashMap;
import java.util.List;

public class Config {
    Pages pages;

    Section<Double> section;
    HashMap<String, Style> styles;

    Footer footer;

    List<Heading> requiredHeadings;

    Boolean findHeadingsByTOC = false;
    Boolean generateNewDocument = false;

    public Pages getPages() {
        return pages;
    }

    public Boolean getFindHeadingsByTOC() {
        return findHeadingsByTOC;
    }

    public Section<Double> getSection() {
        return section;
    }

    public HashMap<String, Style> getStyles() {
        return styles;
    }

    public Footer getFooter() {
        return footer;
    }

    public List<Heading> getRequiredHeadings() {
        return requiredHeadings;
    }

    public Boolean getGenerateNewDocument() {
        return generateNewDocument;
    }
}
