package com.formatChecker.config.model.participants;

public class ConfigDrawing {
    String textStartsWith;

    Paragraph<Double, Boolean> drawingPosition;
    Style description;

    public Paragraph<Double, Boolean> getDrawingPosition() {
        return drawingPosition;
    }

    public Style getDescription() {
        return description;
    }

    public String getTextStartsWith() {
        return textStartsWith;
    }
}
