package com.formatChecker.document.parser.paragraph;

import com.formatChecker.config.model.participants.Paragraph;

import com.formatChecker.document.parser.styles.StyleHierarchy;
import org.docx4j.wml.DocDefaults;
import org.docx4j.wml.PPr;
import org.docx4j.wml.Style;
import org.docx4j.wml.Styles;

import java.util.Optional;

public class ParagraphStyleParser extends ParagraphParser implements ParagraphSetProperties, StyleHierarchy {
    String styleId;
    Styles styles;

    Paragraph<Double> defaultParagraph;

    Optional<Style> style;
    Optional<Style> parentStyle;

    public ParagraphStyleParser(DocDefaults docDefaults, Styles styles, String styleId) {
        super(docDefaults);

        this.styleId = styleId;
        this.styles = styles;
        this.style = getStyleById(styleId, styles);
        this.parentStyle = getParentStyle(style, styles);

        this.defaultParagraph = getDefaultProperties(docDefaults);

        this.paragraphProperties = getParagraphProperties(style);
    }

    public Paragraph<Double> parseParagraph() {
        setAlignment();

        setFirstLineIndent();
        setLeftIndent();
        setRightIndent();

        setLineSpacing();
        setSpacingBefore();
        setSpacingAfter();

        return paragraph;
    }

    PPr getParagraphProperties(Optional<Style> style) {
        return style.map(Style::getPPr).orElse(null);
    }

    @Override
    public void setAlignment() {
        String alignment = getAlignment(paragraphProperties);

        while (parentStyle != null && alignment == null) {
            alignment = getAlignment(getParagraphProperties(parentStyle));
            parentStyle = getParentStyle(parentStyle, styles);
        }

        if (alignment == null) {
            alignment = defaultParagraph.getAlignment();
        }

        paragraph.setAlignment(alignment);
    }

    @Override
    public void setFirstLineIndent() {
        Double firstLineIndent = getFirstLineIndent(getIndent(paragraphProperties));

        while (parentStyle != null && firstLineIndent == null) {
            firstLineIndent = getFirstLineIndent(getIndent(getParagraphProperties(parentStyle)));
            parentStyle = getParentStyle(parentStyle, styles);
        }

        if (firstLineIndent == null) {
            firstLineIndent = defaultParagraph.getFirstLineIndent();
        }

        paragraph.setFirstLineIndent(firstLineIndent);
    }

    @Override
    public void setLeftIndent() {
        Double leftIndent = getLeftIndent(getIndent(paragraphProperties));

        while (parentStyle != null && leftIndent == null) {
            leftIndent = getLeftIndent(getIndent(getParagraphProperties(parentStyle)));
            parentStyle = getParentStyle(parentStyle, styles);
        }

        if (leftIndent == null) {
            leftIndent = defaultParagraph.getLeftIndent();
        }

        paragraph.setLeftIndent(leftIndent);
    }

    @Override
    public void setRightIndent() {
        Double rightIndent = getRightIndent(getIndent(paragraphProperties));

        while (parentStyle != null && rightIndent == null) {
            rightIndent = getRightIndent(getIndent(getParagraphProperties(parentStyle)));
            parentStyle = getParentStyle(parentStyle, styles);
        }

        if (rightIndent == null) {
            rightIndent = defaultParagraph.getRightIndent();
        }

        paragraph.setRightIndent(rightIndent);
    }

    @Override
    public void setLineSpacing() {
        Double lineSpacing = getLineSpacing(getSpacing(paragraphProperties));

        while (parentStyle != null && lineSpacing == null) {
            lineSpacing = getLineSpacing(getSpacing(getParagraphProperties(parentStyle)));
            parentStyle = getParentStyle(parentStyle, styles);
        }

        if (lineSpacing == null) {
            lineSpacing = defaultParagraph.getLineSpacing();
        }

        paragraph.setLineSpacing(lineSpacing);
    }

    @Override
    public void setSpacingBefore() {
        Double spacingBefore = getSpacingBefore(getSpacing(paragraphProperties));

        while (parentStyle != null && spacingBefore == null) {
            spacingBefore = getSpacingBefore(getSpacing(getParagraphProperties(parentStyle)));
            parentStyle = getParentStyle(parentStyle, styles);
        }

        if (spacingBefore == null) {
            spacingBefore = defaultParagraph.getSpacingBefore();
        }

        paragraph.setSpacingBefore(spacingBefore);
    }

    @Override
    public void setSpacingAfter() {
        Double spacingAfter = getSpacingAfter(getSpacing(paragraphProperties));

        while (parentStyle != null && spacingAfter == null) {
            spacingAfter = getSpacingAfter(getSpacing(getParagraphProperties(parentStyle)));
            parentStyle = getParentStyle(parentStyle, styles);
        }

        if (spacingAfter == null) {
            spacingAfter = defaultParagraph.getSpacingAfter();
        }

        paragraph.setSpacingAfter(spacingAfter);
    }
}
