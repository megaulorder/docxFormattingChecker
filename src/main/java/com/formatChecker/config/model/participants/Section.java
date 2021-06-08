package com.formatChecker.config.model.participants;

import java.util.List;

public class Section<T> {
    String orientation;
    protected List<T> margins;
    protected T pageHeight;
    protected T pageWidth;

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public void setMargins(List<T> margins) {
        this.margins = margins;
    }

    public void setPageHeight(T pageHeight) {
        this.pageHeight = pageHeight;
    }

    public void setPageWidth(T pageWidth) {
        this.pageWidth = pageWidth;
    }

    public String getOrientation() {
        return orientation;
    }

    public List<T> getMargins() {
        return margins;
    }

    public T getPageHeight() {
        return pageHeight;
    }

    public T getPageWidth() {
        return pageWidth;
    }
}
