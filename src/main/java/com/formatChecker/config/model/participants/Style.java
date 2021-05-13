package com.formatChecker.config.model.participants;

import com.google.gson.annotations.SerializedName;

public class Style {
    @SerializedName("paragraphs")
    Integer[] paragraphIndexes;

    @SerializedName("paragraph_properties")
    Paragraph paragraph;

    @SerializedName("run_properties")
    Run run;
}
