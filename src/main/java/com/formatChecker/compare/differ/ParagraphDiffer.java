package com.formatChecker.compare.differ;

import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.model.participants.Run;

import java.util.ArrayList;

public class ParagraphDiffer implements Differ {
    public Paragraph getParagraphDifference(Paragraph actualParagraph, Paragraph expectedParagraph, Run expectedRun) {
        Paragraph paragraphDifference = new Paragraph(new ArrayList<>());

        paragraphDifference.setText(actualParagraph.getText());

        paragraphDifference.setAlignment(checkStringParameter(
                actualParagraph.getAlignment(), expectedParagraph.getAlignment(), "alignment"));

        paragraphDifference.setFirstLineIndent(checkStringParameter(
                actualParagraph.getFirstLineIndent(), expectedParagraph.getFirstLineIndent(), "first line indent", "cm"));
        paragraphDifference.setLeftIndent(checkStringParameter(
                actualParagraph.getLeftIndent(), expectedParagraph.getLeftIndent(), "left indent", "cm"));
        paragraphDifference.setRightIndent(checkStringParameter(
                actualParagraph.getRightIndent(), expectedParagraph.getRightIndent(), "right indent", "cm"));

        paragraphDifference.setLineSpacing(checkStringParameter(
                actualParagraph.getLineSpacing(), expectedParagraph.getLineSpacing(), "line spacing"));
        paragraphDifference.setSpacingBefore(checkStringParameter(
                actualParagraph.getSpacingBefore(), expectedParagraph.getSpacingBefore(), "spacing before", "pt"));
        paragraphDifference.setSpacingAfter(checkStringParameter(
                actualParagraph.getSpacingAfter(), expectedParagraph.getSpacingAfter(), "spacing after", "pt"));

        for (Run<Boolean> r: actualParagraph.getRuns()) {
            paragraphDifference.addRun(new RunDiffer().getRunDifference(r, expectedRun));
        }

        return  paragraphDifference;
    }
}
