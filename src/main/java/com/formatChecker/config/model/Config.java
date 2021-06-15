package com.formatChecker.config.model;

import com.formatChecker.config.model.participants.Footer;
import com.formatChecker.config.model.participants.Pages;
import com.formatChecker.config.model.participants.Section;
import com.formatChecker.config.model.participants.Style;

import java.util.HashMap;

public class Config {
    Pages pages;

    Section<Double> section;
    HashMap<String, Style> styles;

    Footer footer;

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

    public Boolean getGenerateNewDocument() {
        return generateNewDocument;
    }
}
