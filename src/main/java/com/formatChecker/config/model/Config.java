package com.formatChecker.config.model;

import com.formatChecker.config.model.participants.*;

import java.util.HashMap;
import java.util.List;

public class Config {
    Pages pages;

    Section<Double> section;
    HashMap<String, Style> styles;

    Footer footer;

    ConfigDrawing drawing;

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

    public ConfigDrawing getDrawing() {
        return drawing;
    }

    public List<Heading> getRequiredHeadings() {
        return requiredHeadings;
    }

    public Boolean getGenerateNewDocument() {
        return generateNewDocument;
    }
}
