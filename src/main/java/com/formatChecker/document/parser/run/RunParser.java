package com.formatChecker.document.parser.run;

import com.formatChecker.config.model.participants.Run;
import com.formatChecker.document.converter.Converter;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.parts.ThemePart;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.Color;
import org.docx4j.wml.DocDefaults;
import org.docx4j.wml.HpsMeasure;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.RPr;
import org.docx4j.wml.U;

public interface RunParser extends Converter {
    default String getFontFamily(RPr runProperties, ThemePart themePart) throws Docx4JException {
        if (runProperties == null) { return null; }
        else {
            RFonts fontProperties = runProperties.getRFonts();
            return fontProperties.getAscii() != null ? fontProperties.getAscii() :
                    themePart.getMajorLatin().getTypeface();
        }
    }

    default String getFontSize(RPr runProperties) {
        if (runProperties == null) { return null; }
        else {
            HpsMeasure fontSize = runProperties.getSz();
            return fontSize == null ? null : halfPtToPt(fontSize.getVal());
        }
    }

    default Boolean getBold(RPr runProperties) {
        if (runProperties == null) { return null; }
        else {
            BooleanDefaultTrue isBold = runProperties.getB();
            return isBold == null ? null : isBold.isVal();
        }
    }

    default Boolean getItalic(RPr runProperties) {
        if (runProperties == null) { return null; }
        else {
            BooleanDefaultTrue isItalic = runProperties.getI();
            return isItalic == null ? null : isItalic.isVal();
        }
    }

    default Boolean getStrikethrough(RPr runProperties) {
        if (runProperties == null) { return null; }
        else {
            BooleanDefaultTrue isStrikethrough = runProperties.getStrike();
            return isStrikethrough == null ? null : isStrikethrough.isVal();
        }
    }

    default String getUnderline(RPr runProperties) {
        if (runProperties == null) { return null; }
        else {
            U underline = runProperties.getU();
            return underline == null ? null : underline.getVal().value();
        }
    }

    default String getTextColor(RPr runProperties) {
        if (runProperties == null) { return null; }
        else {
            Color textColor = runProperties.getColor();
            return textColor == null ? null : textColor.getVal();
        }
    }

    default Run getDefaultProperties(DocDefaults docDefaults, ThemePart themePart) throws Docx4JException {
        return new RunDefaultsParser().parseRun(docDefaults, themePart);
    }
}
