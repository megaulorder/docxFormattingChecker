package com.formatChecker.document.parser.paragraph;

import com.formatChecker.config.model.participants.Paragraph;
import org.docx4j.wml.DocDefaults;

public class ParagraphDefaultsParser extends ParagraphParser implements ParagraphSetProperties {
    static final String DEFAULT_ALIGNMENT = "left";
    static final Double DEFAULT_LINE_SPACING = 1.0;
    static final Double DEFAULT_SPACING_BEFORE = 0.0;
    static final Double DEFAULT_SPACING_AFTER = 0.0;
    static final Double DEFAULT_INDENT = 0.0;

    public ParagraphDefaultsParser(DocDefaults docDefaults) {
        super(docDefaults);

        this.paragraphProperties = docDefaults.getPPrDefault().getPPr();
    }

    public Paragraph<Double, Boolean> parseParagraph() {
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
            setAlignment();

            setFirstLineIndent();
            setLeftIndent();
            setRightIndent();

            setLineSpacing();
            setSpacingBefore();
            setSpacingAfter();
        }

        return paragraph;
    }

    @Override
    public void setAlignment() {
        String alignment = getAlignment(paragraphProperties);
        paragraph.setAlignment(alignment != null ? alignment : ParagraphDefaultsParser.DEFAULT_ALIGNMENT);
    }

    @Override
    public void setFirstLineIndent() {
        Double firstLineIndent = getFirstLineIndent(getIndent(paragraphProperties));
        paragraph.setFirstLineIndent(firstLineIndent != null ? firstLineIndent :
                ParagraphDefaultsParser.DEFAULT_INDENT);
    }

    @Override
    public void setLeftIndent() {
        Double leftIndent = getLeftIndent(getIndent(paragraphProperties));
        paragraph.setLeftIndent(leftIndent != null ? leftIndent : ParagraphDefaultsParser.DEFAULT_INDENT);
    }

    @Override
    public void setRightIndent() {
        Double rightIndent = getRightIndent(getIndent(paragraphProperties));
        paragraph.setRightIndent(rightIndent != null ? rightIndent : ParagraphDefaultsParser.DEFAULT_INDENT);
    }

    @Override
    public void setLineSpacing() {
        Double lineSpacing = getLineSpacing(getSpacing(paragraphProperties));
        paragraph.setLineSpacing(lineSpacing != null ? lineSpacing : ParagraphDefaultsParser.DEFAULT_LINE_SPACING);
    }

    @Override
    public void setSpacingBefore() {
        Double lineSpacing = getSpacingBefore(getSpacing(paragraphProperties));
        paragraph.setSpacingBefore(lineSpacing != null ? lineSpacing : ParagraphDefaultsParser.DEFAULT_SPACING_AFTER);
    }

    @Override
    public void setSpacingAfter() {
        Double lineSpacing = getSpacingAfter(getSpacing(paragraphProperties));
        paragraph.setSpacingAfter(lineSpacing != null ? lineSpacing : ParagraphDefaultsParser.DEFAULT_SPACING_BEFORE);
    }
}
