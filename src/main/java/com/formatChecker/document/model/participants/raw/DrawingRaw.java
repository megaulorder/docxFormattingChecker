package com.formatChecker.document.model.participants.raw;

import org.docx4j.wml.P;

public class DrawingRaw {
    P drawing;
    P description;

    public P getDrawing() {
        return drawing;
    }

    public void setDrawing(P drawing) {
        this.drawing = drawing;
    }

    public P getDescription() {
        return description;
    }

    public void setDescription(P description) {
        this.description = description;
    }
}
