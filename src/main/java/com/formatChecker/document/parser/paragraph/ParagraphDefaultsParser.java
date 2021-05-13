package com.formatChecker.document.parser.paragraph;

import com.formatChecker.config.model.participants.Paragraph;
import org.docx4j.wml.DocDefaults;
import org.docx4j.wml.PPr;

import java.util.ArrayList;

public class ParagraphDefaultsParser implements ParagraphParser {
    static final String DEFAULT_ALIGNMENT = "left";
    static final String DEFAULT_LINE_SPACING = "1.0";
    static final String DEFAULT_SPACING_BEFORE = "0";
    static final String DEFAULT_SPACING_AFTER = "0";
    static final String DEFAULT_INDENT = "0";

    public Paragraph parseParagraph(DocDefaults docDefaults) {
        Paragraph paragraph = new Paragraph(new ArrayList<>());
        PPr paragraphProperties = docDefaults.getPPrDefault().getPPr();

        if (paragraphProperties == null) {
            paragraph.setAlignment(DEFAULT_ALIGNMENT);

            paragraph.setFirstLineIndent(DEFAULT_INDENT);
            paragraph.setLeftIndent(DEFAULT_INDENT);
            paragraph.setRightIndent(DEFAULT_INDENT);

            paragraph.setLineSpacing(DEFAULT_LINE_SPACING);
            paragraph.setSpacingBefore(DEFAULT_SPACING_BEFORE);
            paragraph.setSpacingAfter(DEFAULT_SPACING_AFTER);
        }
        else {
            setAlignment(paragraph, paragraphProperties);

            setFirstLineIndent(paragraph, paragraphProperties);
            setLeftIndent(paragraph, paragraphProperties);
            setRightIndent(paragraph, paragraphProperties);

            setLineSpacing(paragraph, paragraphProperties);
            setSpacingBefore(paragraph, paragraphProperties);
            setSpacingAfter(paragraph, paragraphProperties);
        }

        return paragraph;
    }

    void setAlignment(Paragraph paragraph, PPr paragraphProperties) {
        String alignment = getAlignment(paragraphProperties);
        paragraph.setAlignment(alignment != null ? alignment : ParagraphDefaultsParser.DEFAULT_ALIGNMENT);
    }

    void setFirstLineIndent(Paragraph paragraph, PPr paragraphProperties) {
        String firstLineIndent = getFirstLineIndent(getIndent(paragraphProperties));
        paragraph.setFirstLineIndent(firstLineIndent != null ? firstLineIndent :
                ParagraphDefaultsParser.DEFAULT_INDENT);
    }

    void setLeftIndent(Paragraph paragraph, PPr paragraphProperties) {
        String leftIndent = getLeftIndent(getIndent(paragraphProperties));
        paragraph.setLeftIndent(leftIndent != null ? leftIndent : ParagraphDefaultsParser.DEFAULT_INDENT);
    }

    void setRightIndent(Paragraph paragraph, PPr paragraphProperties) {
        String rightIndent = getRightIndent(getIndent(paragraphProperties));
        paragraph.setRightIndent(rightIndent != null ? rightIndent : ParagraphDefaultsParser.DEFAULT_INDENT);
    }

    void setLineSpacing(Paragraph paragraph, PPr paragraphProperties) {
        String lineSpacing = getLineSpacing(getSpacing(paragraphProperties));
        paragraph.setLineSpacing(lineSpacing != null ? lineSpacing : ParagraphDefaultsParser.DEFAULT_LINE_SPACING);
    }

    void setSpacingBefore(Paragraph paragraph, PPr paragraphProperties) {
        String lineSpacing = getSpacingBefore(getSpacing(paragraphProperties));
        paragraph.setSpacingBefore(lineSpacing != null ? lineSpacing : ParagraphDefaultsParser.DEFAULT_SPACING_AFTER);
    }

    void setSpacingAfter(Paragraph paragraph, PPr paragraphProperties) {
        String lineSpacing = getSpacingAfter(getSpacing(paragraphProperties));
        paragraph.setSpacingAfter(lineSpacing != null ? lineSpacing : ParagraphDefaultsParser.DEFAULT_SPACING_BEFORE);
    }
}
