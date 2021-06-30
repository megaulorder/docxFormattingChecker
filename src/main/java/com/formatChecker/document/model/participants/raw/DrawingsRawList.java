package com.formatChecker.document.model.participants.raw;

import java.util.List;

public class DrawingsRawList {
    String errorMessage;
    List<DrawingRaw> drawingRaws;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<DrawingRaw> getDrawingsRaw() {
        return drawingRaws;
    }

    public void setDrawingsRaw(List<DrawingRaw> drawingRaws) {
        this.drawingRaws = drawingRaws;
    }
}
