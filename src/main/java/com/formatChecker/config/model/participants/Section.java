package com.formatChecker.config.model.participants;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Section {
    String orientation;

    List<Double> margins;

    @SerializedName("page-height")
    String pageHeight;

    @SerializedName("page-width")
    String pageWidth;

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public void setMargins(List<Double> margins) {
        this.margins = margins;
    }

    public void setPageHeight(String pageHeight) {
        this.pageHeight = pageHeight;
    }

    public void setPageWidth(String pageWidth) {
        this.pageWidth = pageWidth;
    }
}
