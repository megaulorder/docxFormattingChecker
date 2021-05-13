package com.formatChecker.document.parser.paragraph;

import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.model.participants.Run;
import com.formatChecker.document.parser.run.RunDirectParser;
import org.apache.commons.lang3.StringUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.parts.ThemePart;
import org.docx4j.wml.DocDefaults;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.R;
import org.docx4j.wml.Styles;

import java.util.ArrayList;

public class ParagraphDirectParser implements ParagraphParser {
    public Paragraph parseParagraph(P par, Styles styles, DocDefaults docDefaults, ThemePart themePart)
            throws Docx4JException {
        Paragraph paragraph = new Paragraph(new ArrayList<>());

        PPr paragraphProperties = par.getPPr();

        String styleId = getStyleId(par);
        Paragraph styleParagraph = styleId == null ? null : getStyleProperties(styleId, styles, docDefaults);
        Paragraph defaultParagraph = getDefaultProperties(docDefaults);

        for (Object o : par.getContent()) {
            if (o instanceof R) {
                R r = (R) o;
                Run run = new RunDirectParser().parseRun(r, styleId, styles, docDefaults, themePart);
                if (!StringUtils.isBlank(run.getText())) {
                    paragraph.addRun(run);
                }
            }
        }

        String text = getText(par);
        paragraph.setText(text);

        setAlignment(paragraph, paragraphProperties, styleParagraph, defaultParagraph);

        setFirstLineIndent(paragraph, paragraphProperties, styleParagraph, defaultParagraph);
        setLeftIndent(paragraph, paragraphProperties, styleParagraph, defaultParagraph);
        setRightIndent(paragraph, paragraphProperties, styleParagraph, defaultParagraph);

        setLineSpacing(paragraph, paragraphProperties, styleParagraph, defaultParagraph);
        setSpacingBefore(paragraph, paragraphProperties, styleParagraph, defaultParagraph);
        setSpacingAfter(paragraph, paragraphProperties, styleParagraph, defaultParagraph);

        return paragraph;
    }

    String getText(P par) { return par.toString(); }

    String getStyleId(P par) {
        return par.getPPr().getPStyle() == null ? null : par.getPPr().getPStyle().getVal();
    }

    Paragraph getStyleProperties(String styleId, Styles styles, DocDefaults docDefaults) {
        return new ParagraphStyleParser().parseParagraph(styleId, styles, docDefaults);
    }

    void setAlignment(Paragraph paragraph, PPr paragraphProperties,
                      Paragraph styleParagraph, Paragraph defaultParagraph) {
        String alignment = getAlignment(paragraphProperties);
        if (alignment == null) {
            alignment = styleParagraph != null ? styleParagraph.getAlignment() : defaultParagraph.getAlignment();
        }
        paragraph.setAlignment(alignment);
    }

    void setFirstLineIndent(Paragraph paragraph, PPr paragraphProperties,
                      Paragraph styleParagraph, Paragraph defaultParagraph) {
        String firstLineIndent = getFirstLineIndent(getIndent(paragraphProperties));
        if (firstLineIndent == null) {
            firstLineIndent = styleParagraph != null ? styleParagraph.getFirstLineIndent() :
                    defaultParagraph.getFirstLineIndent();
        }
        paragraph.setFirstLineIndent(firstLineIndent);
    }

    void setLeftIndent(Paragraph paragraph, PPr paragraphProperties,
                            Paragraph styleParagraph, Paragraph defaultParagraph) {
        String leftIndent = getLeftIndent(getIndent(paragraphProperties));
        if (leftIndent == null) {
            leftIndent = styleParagraph != null ? styleParagraph.getLeftIndent() :
                    defaultParagraph.getLeftIndent();
        }
        paragraph.setLeftIndent(leftIndent);
    }

    void setRightIndent(Paragraph paragraph, PPr paragraphProperties,
                       Paragraph styleParagraph, Paragraph defaultParagraph) {
        String rightIndent = getRightIndent(getIndent(paragraphProperties));
        if (rightIndent == null) {
            rightIndent = styleParagraph != null ? styleParagraph.getRightIndent() :
                    defaultParagraph.getRightIndent();
        }
        paragraph.setRightIndent(rightIndent);
    }

    void setLineSpacing(Paragraph paragraph, PPr paragraphProperties,
                        Paragraph styleParagraph, Paragraph defaultParagraph) {
        String lineSpacing = getLineSpacing(getSpacing(paragraphProperties));
        if (lineSpacing == null) {
            lineSpacing = styleParagraph != null ? styleParagraph.getLineSpacing() :
                    defaultParagraph.getLineSpacing();
        }
        paragraph.setLineSpacing(lineSpacing);
    }

    void setSpacingBefore(Paragraph paragraph, PPr paragraphProperties,
                        Paragraph styleParagraph, Paragraph defaultParagraph) {
        String spacingBefore = getSpacingBefore(getSpacing(paragraphProperties));
        if (spacingBefore == null) {
            spacingBefore = styleParagraph != null ? styleParagraph.getSpacingBefore() :
                    defaultParagraph.getSpacingBefore();
        }
        paragraph.setSpacingBefore(spacingBefore);
    }

    void setSpacingAfter(Paragraph paragraph, PPr paragraphProperties,
                          Paragraph styleParagraph, Paragraph defaultParagraph) {
        String spacingAfter = getSpacingAfter(getSpacing(paragraphProperties));
        if (spacingAfter == null) {
            spacingAfter = styleParagraph != null ? styleParagraph.getSpacingAfter() :
                    defaultParagraph.getSpacingAfter();
        }
        paragraph.setSpacingAfter(spacingAfter);
    }
}
