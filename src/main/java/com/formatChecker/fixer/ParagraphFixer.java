package com.formatChecker.fixer;

import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.model.participants.Run;
import org.docx4j.wml.P;

public class ParagraphFixer {
    P paragraph;
    Paragraph actualParagraph;
    Paragraph expectedParagraph;
    Paragraph differenceParagraph;
    Run expectedRun;

    public ParagraphFixer(P paragraph, Paragraph actualParagraph, Paragraph expectedParagraph,
                          Paragraph differenceParagraph, Run expectedRun) {
        this.paragraph = paragraph;
        this.actualParagraph = actualParagraph;
        this.expectedParagraph = expectedParagraph;
        this.differenceParagraph = differenceParagraph;
        this.expectedRun = expectedRun;
    }

    public void fixParagraph() {
        fixAlignment(expectedParagraph.getAlignment());
    }

    void fixAlignment(String expectedAlignment) {
//        paragraph.getPPr().getJc().setVal(expectedAlignment);
    }
}
