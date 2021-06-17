package com.formatChecker.fixer;

import com.formatChecker.config.converter.ConfigConverter;
import com.formatChecker.config.model.participants.Paragraph;
import org.docx4j.wml.Jc;
import org.docx4j.wml.P;
import org.docx4j.wml.PPrBase.Ind;
import org.docx4j.wml.PPrBase.Spacing;

public class ParagraphFixer implements ConfigConverter {
    P paragraph;
    Paragraph<Double, Boolean> actualParagraph;
    Paragraph<Double, Boolean> expectedParagraph;
    Paragraph<String, String> differenceParagraph;

    public ParagraphFixer(P paragraph,
                          Paragraph<Double, Boolean> actualParagraph,
                          Paragraph<Double, Boolean> expectedParagraph,
                          Paragraph<String, String> differenceParagraph) {
        this.paragraph = paragraph;
        this.actualParagraph = actualParagraph;
        this.expectedParagraph = expectedParagraph;
        this.differenceParagraph = differenceParagraph;
    }

    public void fixParagraph() {
        fixAlignment();
        fixIndent();
        fixSpacing();
    }

    void fixAlignment() {
        if (differenceParagraph.getAlignment() != null) {
            Jc alignment = new Jc();
            alignment.setVal(convertAlignment(expectedParagraph.getAlignment()));
            paragraph.getPPr().setJc(alignment);
        }
    }

    void fixIndent() {
        Ind indent = new Ind();

        if (differenceParagraph.getRightIndent() != null)
            indent.setRight(cmToTwips(expectedParagraph.getRightIndent()));
        else
            indent.setRight(cmToTwips(actualParagraph.getRightIndent()));

        if (differenceParagraph.getLeftIndent() != null)
            indent.setLeft(cmToTwips(expectedParagraph.getLeftIndent()));
        else
            indent.setLeft(cmToTwips(actualParagraph.getLeftIndent()));

        if (differenceParagraph.getFirstLineIndent() != null)
            indent.setFirstLine(cmToTwips(expectedParagraph.getFirstLineIndent()));
        else
            indent.setFirstLine(cmToTwips(actualParagraph.getFirstLineIndent()));

        paragraph.getPPr().setInd(indent);
    }

    void fixSpacing() {
        Spacing spacing = new Spacing();

        if (differenceParagraph.getLineSpacing() != null)
            spacing.setLine(cmToLineSpacing(expectedParagraph.getLineSpacing()));
        else
            spacing.setLine(cmToLineSpacing(actualParagraph.getLineSpacing()));

        if (differenceParagraph.getSpacingBefore() != null)
            spacing.setBefore(cmToAbsUnits(expectedParagraph.getSpacingBefore()));
        else
            spacing.setBefore(cmToAbsUnits(actualParagraph.getSpacingBefore()));

        if (differenceParagraph.getSpacingAfter() != null)
            spacing.setAfter(cmToAbsUnits(expectedParagraph.getSpacingAfter()));
        else
            spacing.setBefore(cmToAbsUnits(actualParagraph.getSpacingAfter()));

        paragraph.getPPr().setSpacing(spacing);
    }
}
