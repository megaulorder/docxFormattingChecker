package com.formatChecker.config.model;

import com.formatChecker.config.model.participants.Pages;
import com.formatChecker.config.model.participants.Section;
import com.formatChecker.config.model.participants.Style;

import java.util.HashMap;

public class Config {
    Pages pages;

    Boolean findHeadersByTOC = false;

    Section<Double> section;
    HashMap<String, Style> styles;

    Boolean generateNewDocument = false;

    public Pages getPages() {
        return pages;
    }

    public Boolean getFindHeadersByToc() {
        return findHeadersByTOC;
    }

    public Section<Double> getSection() {
        return section;
    }

    public HashMap<String, Style> getStyles() {
        return styles;
    }

    public Boolean getGenerateNewDocument() {
        return generateNewDocument;
    }
}
