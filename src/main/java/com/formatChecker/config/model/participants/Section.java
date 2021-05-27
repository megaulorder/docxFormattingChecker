package com.formatChecker.config.model.participants;

import com.google.gson.annotations.SerializedName;

public class Section<T> {
    String orientation;

    protected T margins;

    @SerializedName("page-height")
    String pageHeight;

    @SerializedName("page-width")
    String pageWidth;

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public void setMargins(T margins) {
        this.margins = margins;
    }

    public void setPageHeight(String pageHeight) {
        this.pageHeight = pageHeight;
    }

    public void setPageWidth(String pageWidth) {
        this.pageWidth = pageWidth;
    }

    public String getOrientation() {
        return orientation;
    }

    public T getMargins() {
        return margins;
    }

    public String getPageHeight() {
        return pageHeight;
    }

    public String getPageWidth() {
        return pageWidth;
    }
}
