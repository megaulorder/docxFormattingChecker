package com.formatChecker.comparer.model.participants;

import com.formatChecker.document.model.participants.Drawing;

import java.util.ArrayList;
import java.util.List;

public class DrawingsList {
    String errorMessage;
    List<Drawing> drawings;

    public DrawingsList() {
        this.drawings = new ArrayList<>();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<Drawing> getDrawings() {
        return drawings;
    }

    public void addDrawing(Drawing drawing) {
        drawings.add(drawing);
    }
}
