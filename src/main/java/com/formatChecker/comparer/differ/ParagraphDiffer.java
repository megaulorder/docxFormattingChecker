package com.formatChecker.comparer.differ;

import com.formatChecker.config.model.participants.Paragraph;

public class ParagraphDiffer implements Differ {
    Paragraph<Double, Boolean> actualParagraph;
    Paragraph<Double, Boolean> expectedParagraph;
    Paragraph<String, String> paragraphDifference;

    public ParagraphDiffer(Paragraph<Double, Boolean> actualParagraph, Paragraph<Double, Boolean> expectedParagraph) {
        this.actualParagraph = actualParagraph;
        this.expectedParagraph = expectedParagraph;
        this.paragraphDifference = getDifference();
    }

    Paragraph<String, String> getDifference() {
        Paragraph<String, String> paragraphDifference = new Paragraph<>();

        paragraphDifference.setText(actualParagraph.getText());

        paragraphDifference.setAlignment(checkStringParameter(
                actualParagraph.getAlignment(),
                expectedParagraph.getAlignment(),
                "alignment"));

        paragraphDifference.setFirstLineIndent(checkDoubleParameter(
                actualParagraph.getFirstLineIndent(),
                expectedParagraph.getFirstLineIndent(),
                "first line indent", "cm"));

        paragraphDifference.setLeftIndent(checkDoubleParameter(
                actualParagraph.getLeftIndent(),
                expectedParagraph.getLeftIndent(),
                "left indent", "cm"));

        paragraphDifference.setRightIndent(checkDoubleParameter(
                actualParagraph.getRightIndent(),
                expectedParagraph.getRightIndent(),
                "right indent", "cm"));

        paragraphDifference.setLineSpacing(checkDoubleParameter(
                actualParagraph.getLineSpacing(),
                expectedParagraph.getLineSpacing(),
                "line spacing", ""));

        paragraphDifference.setSpacingBefore(checkDoubleParameter(
                actualParagraph.getSpacingBefore(),
                expectedParagraph.getSpacingBefore(),
                "spacing before", "pt"));

        paragraphDifference.setSpacingAfter(checkDoubleParameter(
                actualParagraph.getSpacingAfter(),
                expectedParagraph.getSpacingAfter(),
                "spacing after", "pt"));

        paragraphDifference.setPageBreakBefore(checkBooleanParameter(
                actualParagraph.getPageBreakBefore(),
                expectedParagraph.getPageBreakBefore(),
                "page break before paragraph"));

        return paragraphDifference;
    }

    public Paragraph<String, String> getParagraphDifference() {
        return paragraphDifference;
    }
}
