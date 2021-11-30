package com.formatChecker.document.parser.paragraph;

import com.formatChecker.comparer.model.participants.HeadingsList;
import com.formatChecker.config.model.participants.Heading;
import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.model.participants.Run;
import com.formatChecker.document.parser.ParserType;
import com.formatChecker.document.parser.run.RunParserFactory;
import org.apache.commons.lang3.StringUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.parts.ThemePart;
import org.docx4j.wml.DocDefaults;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.Styles;

import java.util.List;

public class ParagraphDirectParser extends ParagraphParser implements ParagraphPropertiesSetter, ParagraphPropertiesParser {
    private final P par;
    private final Styles styles;
    private final ThemePart themePart;
    private final String styleId;
    private final Paragraph<Double, Boolean> styleParagraph;
    private final Paragraph<Double, Boolean> defaultParagraph;
    private final String text;
    private final String id;

    private HeadingsList headings;
    private List<String> paragraphsOnNewPages;

    public ParagraphDirectParser(DocDefaults docDefaults,
                                 Styles styles,
                                 ThemePart themePart,
                                 P par,
                                 HeadingsList headings,
                                 List<String> paragraphsOnNewPages) {
        super(docDefaults);

        this.par = par;
        this.styles = styles;
        this.themePart = themePart;
        this.headings = headings;
        this.paragraphsOnNewPages = paragraphsOnNewPages;

        this.styleId = getStyleId(par);
        this.styleParagraph = styleId == null ? null : getStyleProperties();
        this.defaultParagraph = getDefaultProperties(docDefaults);

        this.paragraphProperties = par.getPPr();

        this.text = getText();
        this.id = getId();
    }

    public ParagraphDirectParser(DocDefaults docDefaults,
                                 Styles styles,
                                 ThemePart themePart,
                                 P par) {
        super(docDefaults);

        this.par = par;
        this.styles = styles;
        this.themePart = themePart;

        this.styleId = getStyleId(par);
        this.styleParagraph = styleId == null ? null : getStyleProperties();
        this.defaultParagraph = getDefaultProperties(docDefaults);

        this.paragraphProperties = par.getPPr();

        this.text = getText();
        this.id = getId();
    }

    @Override
    public Paragraph<Double, Boolean> parseParagraph() throws Docx4JException {
        paragraph.setText(text);
        paragraph.setId(id);

        setAlignment();

        setFirstLineIndent();
        setLeftIndent();
        setRightIndent();

        setLineSpacing();
        setSpacingBefore();
        setSpacingAfter();

        setIsHeading();
        setPageBreakBefore();

        for (Object o : par.getContent()) {
            if (o instanceof R) {
                R r = (R) o;
                Run<Boolean, Double> run = RunParserFactory.makeParser(ParserType.DIRECT, styleId, r).parseRun();
                if (!StringUtils.isBlank(run.getText())) {
                    paragraph.addRun(run);
                }
            }
        }

        return paragraph;
    }

    String getText() {
        return par.toString();
    }

    String getId() {
        return par.getParaId();
    }

    String getStyleId(P par) {
        if (par.getPPr() == null) return null;
        else return par.getPPr().getPStyle() == null ? null : par.getPPr().getPStyle().getVal();
    }

    Paragraph<Double, Boolean> getStyleProperties() {
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
        Double firstLineIndent = getFirstLineIndent(getIndent(paragraphProperties));
        if (firstLineIndent == null) {
            firstLineIndent = styleParagraph != null ? styleParagraph.getFirstLineIndent() :
                    defaultParagraph.getFirstLineIndent();
        }
        paragraph.setFirstLineIndent(firstLineIndent);
    }

    @Override
    public void setLeftIndent() {
        Double leftIndent = getLeftIndent(getIndent(paragraphProperties));
        if (leftIndent == null) {
            leftIndent = styleParagraph != null ? styleParagraph.getLeftIndent() :
                    defaultParagraph.getLeftIndent();
        }
        paragraph.setLeftIndent(leftIndent);
    }

    @Override
    public void setRightIndent() {
        Double rightIndent = getRightIndent(getIndent(paragraphProperties));
        if (rightIndent == null) {
            rightIndent = styleParagraph != null ? styleParagraph.getRightIndent() :
                    defaultParagraph.getRightIndent();
        }
        paragraph.setRightIndent(rightIndent);
    }

    @Override
    public void setLineSpacing() {
        Double lineSpacing = getLineSpacing(getSpacing(paragraphProperties));
        if (lineSpacing == null) {
            lineSpacing = styleParagraph != null ? styleParagraph.getLineSpacing() :
                    defaultParagraph.getLineSpacing();
        }
        paragraph.setLineSpacing(lineSpacing);
    }

    @Override
    public void setSpacingBefore() {
        Double spacingBefore = getSpacingBefore(getSpacing(paragraphProperties));
        if (spacingBefore == null) {
            spacingBefore = styleParagraph != null ? styleParagraph.getSpacingBefore() :
                    defaultParagraph.getSpacingBefore();
        }
        paragraph.setSpacingBefore(spacingBefore);
    }

    @Override
    public void setSpacingAfter() {
        Double spacingAfter = getSpacingAfter(getSpacing(paragraphProperties));
        if (spacingAfter == null) {
            spacingAfter = styleParagraph != null ? styleParagraph.getSpacingAfter() :
                    defaultParagraph.getSpacingAfter();
        }
        paragraph.setSpacingAfter(spacingAfter);
    }

    void setIsHeading() {
        paragraph.setHeadingLevel(0);

        if (headings != null && headings.getHeadings() != null) {
            for (Heading heading : headings.getHeadings()) {
                if (heading.getText().equals(text)) {
                    paragraph.setHeadingLevel(heading.getLevel());
                    break;
                }
            }
        }
    }

    private void setPageBreakBefore() {
        if (paragraphsOnNewPages != null && id != null)
            paragraph.setPageBreakBefore(paragraphsOnNewPages.stream().anyMatch(p -> p.equals(id)));
    }
}
