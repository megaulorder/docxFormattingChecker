package com.formatChecker.controller;

import com.formatChecker.compare.differ.ParagraphDiffer;
import com.formatChecker.compare.model.Difference;
import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.model.participants.Run;
import com.formatChecker.fixer.ParagraphFixer;
import org.docx4j.wml.P;

public class ParagraphController {
    P documentParagraph;
    Paragraph actualParagraph;
    Paragraph expectedParagraph;
    Run<Boolean> expectedRun;
    Difference difference;
    Boolean shouldFix;

    public ParagraphController(P documentParagraph, Paragraph actualParagraph, Paragraph expectedParagraph,
                               Run<Boolean> expectedRun, Difference difference, Boolean shouldFix) {
        this.documentParagraph = documentParagraph;
        this.actualParagraph = actualParagraph;
        this.expectedParagraph = expectedParagraph;
        this.expectedRun = expectedRun;
        this.difference = difference;
        this.shouldFix = shouldFix;
    }

    public void compareParagraph() {
        Paragraph paragraph = new ParagraphDiffer(this.actualParagraph, this.expectedParagraph, this.expectedRun)
                .getParagraphDifference();

        difference.addParagraph(paragraph);

        if (shouldFix)
            new ParagraphFixer(this.documentParagraph, this.actualParagraph, this.expectedParagraph, paragraph,
                this.expectedRun)
                .fixParagraph();
    }
}
