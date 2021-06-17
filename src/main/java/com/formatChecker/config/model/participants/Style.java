package com.formatChecker.config.model.participants;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Style {
    @SerializedName("paragraphs")
    List<Integer> paragraphIndexes;

    @SerializedName("paragraphProperties")
    Paragraph<Double, Boolean> paragraph;

    @SerializedName("runProperties")
    Run<Boolean, Double> run;

    public List<Integer> getParagraphIndexes() {
        return paragraphIndexes;
    }

    public Paragraph<Double, Boolean> getParagraph() {
        return paragraph;
    }

    public Run<Boolean, Double> getRun() {
        return run;
    }
}
