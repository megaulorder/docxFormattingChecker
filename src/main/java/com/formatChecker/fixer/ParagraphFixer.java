package com.formatChecker.fixer;

import com.formatChecker.config.converter.ConfigConverter;
import com.formatChecker.config.model.participants.Paragraph;
import org.docx4j.wml.Jc;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase.Ind;
import org.docx4j.wml.PPrBase.Spacing;

public class ParagraphFixer implements ConfigConverter {
    P paragraph;
    PPr paragraphProperties;
    Paragraph<Double, Boolean> actualParagraph;
    Paragraph<Double, Boolean> expectedParagraph;
    Paragraph<String, String> differenceParagraph;

    public ParagraphFixer(P paragraph,
                          Paragraph<Double, Boolean> actualParagraph,
                          Paragraph<Double, Boolean> expectedParagraph,
                          Paragraph<String, String> differenceParagraph) {
        this.paragraph = paragraph;
        this.paragraphProperties = paragraph.getPPr();
        this.actualParagraph = actualParagraph;
        this.expectedParagraph = expectedParagraph;
        this.differenceParagraph = differenceParagraph;
    }

    public void fixParagraph() {
        if (paragraphProperties == null)
            fixParagraphProperties();
        else {
            fixAlignment(paragraphProperties);
            fixIndent(paragraphProperties);
            fixSpacing(paragraphProperties);
        }
    }

    void fixParagraphProperties() {
        PPr newParagraphProperties = new PPr();

        fixAlignment(newParagraphProperties);
        fixIndent(newParagraphProperties);
        fixSpacing(newParagraphProperties);

        paragraph.setPPr(newParagraphProperties);
    }

    void fixAlignment(PPr ppr) {
        if (differenceParagraph.getAlignment() != null) {
            Jc alignment = new Jc();
            alignment.setVal(convertAlignment(expectedParagraph.getAlignment()));
            ppr.setJc(alignment);
        }
    }

    void fixIndent(PPr ppr) {
        Ind indent = new Ind();

        if (differenceParagraph.getRightIndent() == null &&
                differenceParagraph.getLeftIndent() == null &&
                differenceParagraph.getFirstLineIndent() == null)
            return;

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

        ppr.setInd(indent);
    }

    void fixSpacing(PPr ppr) {
        Spacing spacing = new Spacing();

        if (differenceParagraph.getLineSpacing() == null &&
                differenceParagraph.getSpacingBefore() == null &&
                differenceParagraph.getSpacingAfter() == null)
            return;

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

        ppr.setSpacing(spacing);
    }
}
