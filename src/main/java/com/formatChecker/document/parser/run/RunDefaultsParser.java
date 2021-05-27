package com.formatChecker.document.parser.run;

import com.formatChecker.config.model.participants.Run;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.parts.ThemePart;
import org.docx4j.wml.DocDefaults;
import org.docx4j.wml.RPr;

public class RunDefaultsParser implements RunParser {
    static final String DEFAULT_FONT_FAMILY = "Calibri";
    static final String DEFAULT_FONT_SIZE = "12.0";
    static final String DEFAULT_TEXT_COLOR = "000000";
    static final String DEFAULT_UNDERLINE = "none";
    static final Boolean DEFAULT_BOOL_VALUE = false;

    public Run<Boolean> parseRun(DocDefaults docDefaults, ThemePart themePart) throws Docx4JException {
        Run<Boolean> run = new Run<>();
        RPr runProperties = docDefaults.getRPrDefault().getRPr();

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
            setFontFamily(run,runProperties, themePart);
            setFontSize(run,runProperties);
            setItalic(run,runProperties);
            setBold(run,runProperties);
            setStrikethrough(run,runProperties);
            setUnderline(run,runProperties);
            setTextColor(run,runProperties);
        }

        return run;
    }

    void setFontFamily(Run<Boolean> run, RPr runProperties, ThemePart themePart) throws Docx4JException {
        String fontFamily = getFontFamily(runProperties, themePart);
        run.setFontFamily(fontFamily != null ? fontFamily : DEFAULT_FONT_FAMILY);
    }

    void setFontSize(Run<Boolean> run, RPr runProperties) {
        String fontSize = getFontSize(runProperties);
        run.setFontSize(fontSize != null ? fontSize : DEFAULT_FONT_SIZE);
    }

    void setBold(Run<Boolean> run, RPr runProperties) {
        Boolean isBold = getBold(runProperties);
        run.setBold(isBold != null ? isBold : DEFAULT_BOOL_VALUE);
    }

    void setItalic(Run<Boolean> run, RPr runProperties) {
        Boolean isItalic = getItalic(runProperties);
        run.setItalic(isItalic != null ? isItalic : DEFAULT_BOOL_VALUE);
    }

    void setStrikethrough(Run<Boolean> run, RPr runProperties) {
        Boolean isStrikethrough = getStrikethrough(runProperties);
        run.setStrikethrough(isStrikethrough != null ? isStrikethrough : DEFAULT_BOOL_VALUE);
    }

    void setUnderline(Run<Boolean> run, RPr runProperties) {
        String underline = getUnderline(runProperties);
        run.setUnderline(underline != null ? underline : DEFAULT_UNDERLINE);
    }

    void setTextColor(Run<Boolean> run, RPr runProperties) {
        String textColor = getTextColor(runProperties);
        run.setTextColor(textColor != null ? textColor : DEFAULT_UNDERLINE);
    }
}
