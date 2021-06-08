package com.formatChecker.document.parser.run;

import com.formatChecker.config.model.participants.Run;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.parts.ThemePart;
import org.docx4j.wml.DocDefaults;

public class RunDefaultsParser extends RunParser implements RunSetProperties {
    static final String DEFAULT_FONT_FAMILY = "Calibri";
    static final Double DEFAULT_FONT_SIZE = 12.0;
    static final String DEFAULT_TEXT_COLOR = "000000";
    static final String DEFAULT_UNDERLINE = "none";
    static final Boolean DEFAULT_BOOL_VALUE = false;


    public RunDefaultsParser(DocDefaults docDefaults, ThemePart themePart) {
        super(docDefaults, themePart);

        this.runProperties = docDefaults.getRPrDefault().getRPr();
    }

    public Run<Boolean, Double> parseRun() throws Docx4JException {
        if (runProperties == null) {
            run.setFontFamily(DEFAULT_FONT_FAMILY);
            run.setFontSize(DEFAULT_FONT_SIZE);
            run.setItalic(DEFAULT_BOOL_VALUE);
            run.setBold(DEFAULT_BOOL_VALUE);
            run.setStrikethrough(DEFAULT_BOOL_VALUE);
            run.setUnderline(DEFAULT_UNDERLINE);
            run.setTextColor(DEFAULT_TEXT_COLOR);
        }
        else {
            setFontFamily();
            setFontSize();
            setItalic();
            setBold();
            setStrikethrough();
            setUnderline();
            setTextColor();
        }

        return run;
    }

    @Override
    public void setFontFamily() throws Docx4JException {
        String fontFamily = getFontFamily(runProperties, themePart);
        run.setFontFamily(fontFamily != null ? fontFamily : DEFAULT_FONT_FAMILY);
    }

    @Override
    public void setFontSize() {
        Double fontSize = getFontSize(runProperties);
        run.setFontSize(fontSize != null ? fontSize : DEFAULT_FONT_SIZE);
    }

    @Override
    public void setBold() {
        Boolean isBold = getBold(runProperties);
        run.setBold(isBold != null ? isBold : DEFAULT_BOOL_VALUE);
    }

    @Override
    public void setItalic() {
        Boolean isItalic = getItalic(runProperties);
        run.setItalic(isItalic != null ? isItalic : DEFAULT_BOOL_VALUE);
    }

    @Override
    public void setStrikethrough() {
        Boolean isStrikethrough = getStrikethrough(runProperties);
        run.setStrikethrough(isStrikethrough != null ? isStrikethrough : DEFAULT_BOOL_VALUE);
    }

    @Override
    public void setUnderline() {
        String underline = getUnderline(runProperties);
        run.setUnderline(underline != null ? underline : DEFAULT_UNDERLINE);
    }

    @Override
    public void setTextColor() {
        String textColor = getTextColor(runProperties);
        run.setTextColor(textColor != null ? textColor : DEFAULT_TEXT_COLOR);
    }
}
