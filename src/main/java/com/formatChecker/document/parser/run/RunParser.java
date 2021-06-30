package com.formatChecker.document.parser.run;

import com.formatChecker.config.model.participants.Run;
import com.formatChecker.document.converter.ValuesConverter;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.parts.ThemePart;
import org.docx4j.wml.*;

public abstract class RunParser implements ValuesConverter {
    DocDefaults docDefaults;
    ThemePart themePart;

    Run<Boolean, Double> run;
    RPr runProperties;

    public RunParser(DocDefaults docDefaults, ThemePart themePart) {
        this.docDefaults = docDefaults;
        this.themePart = themePart;

        this.run = new Run<>();
    }

    String getFontFamily(RPr runProperties, ThemePart themePart) throws Docx4JException {
        if (runProperties == null)
            return null;
        else {
            RFonts fontProperties = runProperties.getRFonts();
            if (fontProperties == null)
                return null;
            else
                return fontProperties.getAscii() != null ? fontProperties.getAscii() : null;
        }
    }

    Double getFontSize(RPr runProperties) {
        if (runProperties == null)
            return null;
        else {
            HpsMeasure fontSize = runProperties.getSz();
            return fontSize == null ? null : halfPtToPt(fontSize.getVal());
        }
    }

    Boolean getBold(RPr runProperties) {
        if (runProperties == null)
            return null;
        else {
            BooleanDefaultTrue isBold = runProperties.getB();
            return isBold == null ? null : isBold.isVal();
        }
    }

    Boolean getItalic(RPr runProperties) {
        if (runProperties == null)
            return null;
        else {
            BooleanDefaultTrue isItalic = runProperties.getI();
            return isItalic == null ? null : isItalic.isVal();
        }
    }

    Boolean getStrikethrough(RPr runProperties) {
        if (runProperties == null)
            return null;
        else {
            BooleanDefaultTrue isStrikethrough = runProperties.getStrike();
            return isStrikethrough == null ? null : isStrikethrough.isVal();
        }
    }

    String getUnderline(RPr runProperties) {
        if (runProperties == null)
            return null;
        else {
            U underline = runProperties.getU();

            if (underline == null)
                return null;

            if (underline.getVal() == null)
                return null;

            return underline.getVal().value();
        }
    }

    String getTextColor(RPr runProperties) {
        if (runProperties == null)
            return null;
        else {
            Color textColor = runProperties.getColor();
            return textColor == null ? null : textColor.getVal();
        }
    }

    Run<Boolean, Double> getDefaultProperties(DocDefaults docDefaults, ThemePart themePart) throws Docx4JException {
        return new RunDefaultsParser(docDefaults, themePart).parseRun();
    }
}
