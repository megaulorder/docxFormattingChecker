package com.formatChecker.document.parser.run;

import com.formatChecker.config.model.participants.Run;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.parts.ThemePart;
import org.docx4j.wml.DocDefaults;
import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Styles;
import org.docx4j.wml.Text;

import javax.xml.bind.JAXBElement;

public class RunDirectParser extends RunParser implements RunSetProperties {
    R r;
    String styleId;
    Styles styles;

    Run<Boolean, Double> styleRun;
    Run<Boolean, Double> defaultRun;
    String text;

    public RunDirectParser(DocDefaults docDefaults, ThemePart themePart, String styleId, Styles styles,  R r)
            throws Docx4JException {
        super(docDefaults, themePart);

        this.runProperties = r.getRPr();

        this.r = r;
        this.styleId = styleId;
        this.styles = styles;

        this.styleRun = getStyleProperties();
        this.defaultRun = getDefaultProperties(docDefaults, themePart);
        this.text = getText(r);
    }

    public Run<Boolean, Double> parseRun() {
        run.setText(text);

        setFontFamily();
        setFontSize();
        setBold();
        setItalic();
        setStrikethrough();
        setUnderline();
        setTextColor();

        return run;
    }

    String getText(R r) {
        JAXBElement obj = ((JAXBElement) r.getContent().get(0));
        if (obj.getValue() instanceof Text) {
            return ((Text) obj.getValue()).getValue();
        } else { return ""; }
    }

    String getFontFamily(RPr runProperties) {
        if (runProperties == null) { return null; }
        else { return runProperties.getRFonts() == null ? null : runProperties.getRFonts().getAscii(); }
    }

    Run<Boolean, Double> getStyleProperties() throws Docx4JException {
        return new RunStyleParser(docDefaults, themePart, styleId, styles).parseRun();
    }

    @Override
    public void setFontFamily() {
        String fontFamily = getFontFamily(runProperties);
        if (fontFamily == null) {
            fontFamily = styleRun != null ? styleRun.getFontFamily() : defaultRun.getFontFamily();
        }
        run.setFontFamily(fontFamily);
    }

    @Override
    public void setFontSize() {
        Double fontSize = getFontSize(runProperties);
        if (fontSize == null) {
            fontSize = styleRun != null ? styleRun.getFontSize() : defaultRun.getFontSize();
        }
        run.setFontSize(fontSize);
    }

    @Override
    public void setBold() {
        Boolean isBold = getBold(runProperties);
        if (isBold == null) {
            isBold = styleRun != null ? styleRun.getBold() : defaultRun.getBold();
        }
        run.setBold(isBold);
    }

    @Override
    public void setItalic() {
        Boolean isItalic = getItalic(runProperties);
        if (isItalic == null) {
            isItalic = styleRun != null ? styleRun.getItalic() : defaultRun.getItalic();
        }
        run.setItalic(isItalic);
    }

    @Override
    public void setStrikethrough() {
        Boolean isStrikethrough = getStrikethrough(runProperties);
        if (isStrikethrough == null) {
            isStrikethrough = styleRun != null ? styleRun.getStrikethrough() : defaultRun.getStrikethrough();
        }
        run.setStrikethrough(isStrikethrough);
    }

    @Override
    public void setUnderline() {
        String underline = getUnderline(runProperties);
        if (underline == null) {
            underline = styleRun != null ? styleRun.getUnderline() : defaultRun.getUnderline();
        }
        run.setUnderline(underline);
    }

    @Override
    public void setTextColor() {
        String textColor = getTextColor(runProperties);
        if (textColor == null) {
            textColor = styleRun != null ? styleRun.getTextColor() : defaultRun.getTextColor();
        }
        run.setTextColor(textColor);
    }
}
