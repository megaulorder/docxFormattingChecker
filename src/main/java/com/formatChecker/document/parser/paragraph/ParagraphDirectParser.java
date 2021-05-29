package com.formatChecker.document.parser.paragraph;

import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.model.participants.Run;
import com.formatChecker.document.parser.run.RunDirectParser;
import org.apache.commons.lang3.StringUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.parts.ThemePart;
import org.docx4j.wml.DocDefaults;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.Styles;

import javax.xml.bind.JAXBElement;
import java.util.Optional;

public class ParagraphDirectParser extends ParagraphParser implements ParagraphSetProperties {
    P par;
    Styles styles;
    ThemePart themePart;

    String styleId;
    Paragraph styleParagraph;
    Paragraph defaultParagraph;
    String text;

    public ParagraphDirectParser(DocDefaults docDefaults, Styles styles, ThemePart themePart, P par) {
        super(docDefaults);

        this.par = par;
        this.styles = styles;
        this.themePart = themePart;

        this.styleId = getStyleId(par);
        this.styleParagraph = styleId == null ? null : getStyleProperties();
        this.defaultParagraph = getDefaultProperties(docDefaults);

        this.paragraphProperties = par.getPPr();

        this.text = getText();
    }

    public Paragraph parseParagraph() throws Docx4JException {
        for (Object o : par.getContent()) {
            if (o instanceof R) {
                R r = (R) o;
                Run run = new RunDirectParser(docDefaults, themePart, styleId, styles, r).parseRun();
                if (!StringUtils.isBlank(run.getText())) {
                    paragraph.addRun(run);
                }
            }
        }

        paragraph.setText(text);

        setAlignment();

        setFirstLineIndent();
        setLeftIndent();
        setRightIndent();

        setLineSpacing();
        setSpacingBefore();
        setSpacingAfter();

        setIsHeader();

        return paragraph;
    }

    String getText() { return par.toString(); }

    String getStyleId(P par) {
        if (par.getPPr() == null) return null;
        else return par.getPPr().getPStyle() == null ? null : par.getPPr().getPStyle().getVal();
    }

    Paragraph getStyleProperties() {
        return new ParagraphStyleParser(docDefaults, styles, styleId).parseParagraph();
    }

    @Override
    public void setAlignment() {
        String alignment = getAlignment(paragraphProperties);
        if (alignment == null) {
            alignment = styleParagraph != null ? styleParagraph.getAlignment() : defaultParagraph.getAlignment();
        }
        paragraph.setAlignment(alignment);
    }

    @Override
    public void setFirstLineIndent() {
        String firstLineIndent = getFirstLineIndent(getIndent(paragraphProperties));
        if (firstLineIndent == null) {
            firstLineIndent = styleParagraph != null ? styleParagraph.getFirstLineIndent() :
                    defaultParagraph.getFirstLineIndent();
        }
        paragraph.setFirstLineIndent(firstLineIndent);
    }

    @Override
    public void setLeftIndent() {
        String leftIndent = getLeftIndent(getIndent(paragraphProperties));
        if (leftIndent == null) {
            leftIndent = styleParagraph != null ? styleParagraph.getLeftIndent() :
                    defaultParagraph.getLeftIndent();
        }
        paragraph.setLeftIndent(leftIndent);
    }

    @Override
    public void setRightIndent() {
        String rightIndent = getRightIndent(getIndent(paragraphProperties));
        if (rightIndent == null) {
            rightIndent = styleParagraph != null ? styleParagraph.getRightIndent() :
                    defaultParagraph.getRightIndent();
        }
        paragraph.setRightIndent(rightIndent);
    }

    @Override
    public void setLineSpacing() {
        String lineSpacing = getLineSpacing(getSpacing(paragraphProperties));
        if (lineSpacing == null) {
            lineSpacing = styleParagraph != null ? styleParagraph.getLineSpacing() :
                    defaultParagraph.getLineSpacing();
        }
        paragraph.setLineSpacing(lineSpacing);
    }

    @Override
    public void setSpacingBefore() {
        String spacingBefore = getSpacingBefore(getSpacing(paragraphProperties));
        if (spacingBefore == null) {
            spacingBefore = styleParagraph != null ? styleParagraph.getSpacingBefore() :
                    defaultParagraph.getSpacingBefore();
        }
        paragraph.setSpacingBefore(spacingBefore);
    }

    @Override
    public void setSpacingAfter() {
        String spacingAfter = getSpacingAfter(getSpacing(paragraphProperties));
        if (spacingAfter == null) {
            spacingAfter = styleParagraph != null ? styleParagraph.getSpacingAfter() :
                    defaultParagraph.getSpacingAfter();
        }
        paragraph.setSpacingAfter(spacingAfter);
    }

    void setIsHeader() {
        Optional<Object> tocElement = par.getContent().stream().filter(i -> i instanceof JAXBElement).findFirst();
        paragraph.setIsHeader(tocElement.isPresent());
    }
}
