package com.formatChecker.compare.differ;

import com.formatChecker.config.model.participants.Paragraph;

import java.util.ArrayList;

public class ParagraphDiffer implements Differ {
    Paragraph<Double> actualParagraph;
    Paragraph<Double> expectedParagraph;

    public ParagraphDiffer(Paragraph<Double> actualParagraph, Paragraph<Double> expectedParagraph) {
        this.actualParagraph = actualParagraph;
        this.expectedParagraph = expectedParagraph;
    }

    public Paragraph<String> getParagraphDifference() {
        Paragraph<String> paragraphDifference = new Paragraph<>(new ArrayList<>());

        paragraphDifference.setText(actualParagraph.getText());

        paragraphDifference.setAlignment(checkStringParameter(
                actualParagraph.getAlignment(), expectedParagraph.getAlignment(), "alignment"));

        paragraphDifference.setFirstLineIndent(checkDoubleParameter(
                actualParagraph.getFirstLineIndent(), expectedParagraph.getFirstLineIndent(), "first line indent", "cm"));
        paragraphDifference.setLeftIndent(checkDoubleParameter(
                actualParagraph.getLeftIndent(), expectedParagraph.getLeftIndent(), "left indent", "cm"));
        paragraphDifference.setRightIndent(checkDoubleParameter(
                actualParagraph.getRightIndent(), expectedParagraph.getRightIndent(), "right indent", "cm"));

        paragraphDifference.setLineSpacing(checkDoubleParameter(
                actualParagraph.getLineSpacing(), expectedParagraph.getLineSpacing(), "line spacing", ""));
        paragraphDifference.setSpacingBefore(checkDoubleParameter(
                actualParagraph.getSpacingBefore(), expectedParagraph.getSpacingBefore(), "spacing before", "pt"));
        paragraphDifference.setSpacingAfter(checkDoubleParameter(
                actualParagraph.getSpacingAfter(), expectedParagraph.getSpacingAfter(), "spacing after", "pt"));

        return  paragraphDifference;
    }
}
