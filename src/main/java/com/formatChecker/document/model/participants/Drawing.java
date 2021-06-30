package com.formatChecker.document.model.participants;

import com.formatChecker.config.model.participants.Paragraph;

public class Drawing<T, D> {
    String text;
    String textErrorMessage;
    Paragraph<T, D> drawing;
    Paragraph<T, D> description;

    public Paragraph<T, D> getDrawing() {
        return drawing;
    }

    public void setDrawing(Paragraph<T, D> drawing) {
        this.drawing = drawing;
    }

    public Paragraph<T, D> getDescription() {
        return description;
    }

    public void setDescription(Paragraph<T, D> description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextErrorMessage() {
        return textErrorMessage;
    }

    public void setTextErrorMessage(String textErrorMessage) {
        this.textErrorMessage = textErrorMessage;
    }
}
