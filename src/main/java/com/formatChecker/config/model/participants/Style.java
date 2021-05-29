package com.formatChecker.config.model.participants;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Style {
    @SerializedName("paragraphs")
    List<Integer> paragraphIndexes;

    @SerializedName("paragraphProperties")
    Paragraph paragraph;

    @SerializedName("runProperties")
    Run run;

    public List<Integer> getParagraphIndexes() {
        return paragraphIndexes;
    }

    public Paragraph getParagraph() {
        return paragraph;
    }

    public Run getRun() {
        return run;
    }
}
