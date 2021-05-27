package com.formatChecker.config.model.participants;

import com.google.gson.annotations.SerializedName;

public class Run<T> {
    String text;

    @SerializedName("font-family")
    String fontFamily;

    @SerializedName("font-size")
    String fontSize;

    T bold;
    T italic;
    T strikethrough;
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

    public void setBold(T bold) {
        this.bold = bold;
    }

    public void setItalic(T italic) {
        this.italic = italic;
    }

    public void setStrikethrough(T strikethrough) {
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

    public T getBold() {
        return bold;
    }

    public T getItalic() {
        return italic;
    }

    public T getStrikethrough() {
        return strikethrough;
    }

    public String getUnderline() {
        return underline;
    }

    public String getTextColor() {
        return textColor;
    }
}
