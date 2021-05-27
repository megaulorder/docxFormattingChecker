package com.formatChecker.config.model.participants;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Paragraph {
    public Paragraph(List<Run> runs) {
        this.runs = runs;
    }

    List<Run> runs;

    String text;

    String alignment;

    @SerializedName("first-line-indent")
    String firstLineIndent;

    @SerializedName("left-indent")
    String leftIndent;

    @SerializedName("right-indent")
    String rightIndent;

    @SerializedName("line-spacing")
    String lineSpacing;

    @SerializedName("spacing-before")
    String spacingBefore;

    @SerializedName("spacing-after")
    String spacingAfter;

    public List<Run> addRun(Run run) {
        runs.add(run);
        return runs;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public void setFirstLineIndent(String firstLineIndent) {
        this.firstLineIndent = firstLineIndent;
    }

    public void setLeftIndent(String leftIndent) {
        this.leftIndent = leftIndent;
    }

    public void setRightIndent(String rightIndent) {
        this.rightIndent = rightIndent;
    }

    public void setLineSpacing(String lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    public void setSpacingBefore(String spacingBefore) {
        this.spacingBefore = spacingBefore;
    }

    public void setSpacingAfter(String spacingAfter) {
        this.spacingAfter = spacingAfter;
    }

    public String getAlignment() {
        return alignment;
    }

    public String getFirstLineIndent() {
        return firstLineIndent;
    }

    public String getLeftIndent() {
        return leftIndent;
    }

    public String getRightIndent() {
        return rightIndent;
    }

    public String getLineSpacing() {
        return lineSpacing;
    }

    public String getSpacingBefore() {
        return spacingBefore;
    }

    public String getSpacingAfter() {
        return spacingAfter;
    }

    public List<Run> getRuns() {
        return runs;
    }

    public String getText() {
        return text;
    }
}
