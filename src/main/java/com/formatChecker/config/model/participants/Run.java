package com.formatChecker.config.model.participants;

import com.google.gson.annotations.SerializedName;

public class Run {
    String text;

    @SerializedName("font-family")
    String fontFamily;

    @SerializedName("font-size")
    String fontSize;

    Boolean bold;
    Boolean italic;
    Boolean strikethrough;
    String underline;
    String textColor;

    public void setText(String text) {
        this.text = text;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public void setBold(Boolean bold) {
        this.bold = bold;
    }

    public void setItalic(Boolean italic) {
        this.italic = italic;
    }

    public void setStrikethrough(Boolean strikethrough) {
        this.strikethrough = strikethrough;
    }

    public void setUnderline(String underline) {
        this.underline = underline;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getText() {
        return text;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public String getFontSize() {
        return fontSize;
    }

    public Boolean getBold() {
        return bold;
    }

    public Boolean getItalic() {
        return italic;
    }

    public Boolean getStrikethrough() {
        return strikethrough;
    }

    public String getUnderline() {
        return underline;
    }

    public String getTextColor() {
        return textColor;
    }
}
