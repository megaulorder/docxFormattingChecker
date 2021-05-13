package com.formatChecker.document.parser.paragraph;

import com.formatChecker.config.model.participants.Paragraph;

import com.formatChecker.document.parser.styles.StyleHierarchy;
import org.docx4j.wml.DocDefaults;
import org.docx4j.wml.PPr;
import org.docx4j.wml.Style;
import org.docx4j.wml.Styles;

import java.util.ArrayList;
import java.util.Optional;

public class ParagraphStyleParser implements ParagraphParser, StyleHierarchy {
    public Paragraph parseParagraph(String styleId, Styles styles, DocDefaults docDefaults) {
        Paragraph paragraph = new Paragraph(new ArrayList<>());
        Paragraph defaultParagraph = getDefaultProperties(docDefaults);

        Optional<Style> style = getStyleById(styleId, styles);
        Optional<Style> parentStyle = getParentStyle(style, styles);

        PPr paragraphProperties = getParagraphProperties(style);

        setAlignment(paragraph, paragraphProperties, defaultParagraph, parentStyle, styles);

        setFirstLineIndent(paragraph, paragraphProperties, defaultParagraph, parentStyle, styles);
        setLeftIndent(paragraph, paragraphProperties, defaultParagraph, parentStyle, styles);
        setRightIndent(paragraph, paragraphProperties, defaultParagraph, parentStyle, styles);

        setLineSpacing(paragraph, paragraphProperties, defaultParagraph, parentStyle, styles);
        setSpacingBefore(paragraph, paragraphProperties, defaultParagraph, parentStyle, styles);
        setSpacingAfter(paragraph, paragraphProperties, defaultParagraph, parentStyle, styles);

        return paragraph;
    }

    PPr getParagraphProperties(Optional<Style> style) {
        return style.map(Style::getPPr).orElse(null);
    }

    void setAlignment(Paragraph paragraph, PPr paragraphProperties, Paragraph defaultParagraph,
                      Optional<Style> parentStyle, Styles styles) {
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

    void setFirstLineIndent(Paragraph paragraph, PPr paragraphProperties, Paragraph defaultParagraph,
                      Optional<Style> parentStyle, Styles styles) {
        String firstLineIndent = getFirstLineIndent(getIndent(paragraphProperties));

        while (parentStyle != null && firstLineIndent == null) {
            firstLineIndent = getFirstLineIndent(getIndent(getParagraphProperties(parentStyle)));
            parentStyle = getParentStyle(parentStyle, styles);
        }

        if (firstLineIndent == null) {
            firstLineIndent = defaultParagraph.getFirstLineIndent();
        }

        paragraph.setFirstLineIndent(firstLineIndent);
    }

    void setLeftIndent(Paragraph paragraph, PPr paragraphProperties, Paragraph defaultParagraph,
                            Optional<Style> parentStyle, Styles styles) {

        String leftIndent = getLeftIndent(getIndent(paragraphProperties));

        while (parentStyle != null && leftIndent == null) {
            leftIndent = getLeftIndent(getIndent(getParagraphProperties(parentStyle)));
            parentStyle = getParentStyle(parentStyle, styles);
        }

        if (leftIndent == null) {
            leftIndent = defaultParagraph.getLeftIndent();
        }

        paragraph.setLeftIndent(leftIndent);
    }

    void setRightIndent(Paragraph paragraph, PPr paragraphProperties, Paragraph defaultParagraph,
                       Optional<Style> parentStyle, Styles styles) {
        String rightIndent = getRightIndent(getIndent(paragraphProperties));

        while (parentStyle != null && rightIndent == null) {
            rightIndent = getRightIndent(getIndent(getParagraphProperties(parentStyle)));
            parentStyle = getParentStyle(parentStyle, styles);
        }

        if (rightIndent == null) {
            rightIndent = defaultParagraph.getRightIndent();
        }

        paragraph.setRightIndent(rightIndent);
    }

    void setLineSpacing(Paragraph paragraph, PPr paragraphProperties, Paragraph defaultParagraph,
                        Optional<Style> parentStyle, Styles styles) {
        String lineSpacing = getLineSpacing(getSpacing(paragraphProperties));

        while (parentStyle != null && lineSpacing == null) {
            lineSpacing = getLineSpacing(getSpacing(getParagraphProperties(parentStyle)));
            parentStyle = getParentStyle(parentStyle, styles);
        }

        if (lineSpacing == null) {
            lineSpacing = defaultParagraph.getLineSpacing();
        }

        paragraph.setLineSpacing(lineSpacing);
    }

    void setSpacingBefore(Paragraph paragraph, PPr paragraphProperties, Paragraph defaultParagraph,
                        Optional<Style> parentStyle, Styles styles) {
        String spacingBefore = getSpacingBefore(getSpacing(paragraphProperties));

        while (parentStyle != null && spacingBefore == null) {
            spacingBefore = getSpacingBefore(getSpacing(getParagraphProperties(parentStyle)));
            parentStyle = getParentStyle(parentStyle, styles);
        }

        if (spacingBefore == null) {
            spacingBefore = defaultParagraph.getSpacingBefore();
        }

        paragraph.setSpacingBefore(spacingBefore);
    }

    void setSpacingAfter(Paragraph paragraph, PPr paragraphProperties, Paragraph defaultParagraph,
                        Optional<Style> parentStyle, Styles styles) {
        String spacingAfter = getSpacingAfter(getSpacing(paragraphProperties));

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
