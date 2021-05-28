package com.formatChecker.config.model;

import com.formatChecker.config.model.participants.Pages;
import com.formatChecker.config.model.participants.Section;
import com.formatChecker.config.model.participants.Style;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Config {
    Pages pages;

    @SerializedName("find-headers-by-TOC")
    Boolean finByToc = false;

    Section section;
    HashMap<String, Style> styles;

    public Pages getPages() {
        return pages;
    }

    public Boolean getFinByToc() {
        return finByToc;
    }

    public Section getSection() {
        return section;
    }

    public HashMap<String, Style> getStyles() {
        return styles;
    }
}
