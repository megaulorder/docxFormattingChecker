package com.formatChecker.document.parser.run;

import com.formatChecker.config.model.participants.Run;
import com.formatChecker.document.parser.styles.StyleHierarchy;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.parts.ThemePart;
import org.docx4j.wml.DocDefaults;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Style;
import org.docx4j.wml.Styles;

import java.util.Optional;

public class RunStyleParser implements RunParser, StyleHierarchy {
    public Run<Boolean> parseRun(String styleId, Styles styles, DocDefaults docDefaults, ThemePart themePart)
            throws Docx4JException {
        Run<Boolean> run = new Run<>();
        Run<Boolean> defaultRun = getDefaultProperties(docDefaults, themePart);

        Optional<Style> style = getStyleById(styleId, styles);
        Optional<Style> parentStyle = getParentStyle(style, styles);

        RPr runProperties = getRunProperties(style);

        setFontFamily(run, runProperties, defaultRun, parentStyle, styles, themePart);
        setFontSize(run, runProperties, defaultRun, parentStyle, styles);
        setBold(run, runProperties, defaultRun, parentStyle, styles);
        setItalic(run, runProperties, defaultRun, parentStyle, styles);
        setStrikethrough(run, runProperties, defaultRun, parentStyle, styles);
        setUnderline(run, runProperties, defaultRun, parentStyle, styles);
        setTextColor(run, runProperties, defaultRun, parentStyle, styles);

        return run;
    }

    RPr getRunProperties(Optional<Style> style) {
        return style.map(Style::getRPr).orElse(null);
    }

    void setFontFamily(Run<Boolean> run, RPr runProperties, Run defaultRun, Optional<Style> parentStyle, Styles styles,
                       ThemePart themePart) throws Docx4JException {
        String fontFamily = getFontFamily(runProperties, themePart);

        while (parentStyle != null && fontFamily == null) {
            fontFamily = getFontFamily(getRunProperties(parentStyle), themePart);
            parentStyle = getParentStyle(parentStyle, styles);
        }

        if (fontFamily == null) {
            fontFamily = defaultRun.getFontFamily();
        }

        run.setFontFamily(fontFamily);
    }

    void setFontSize(Run<Boolean> run, RPr runProperties, Run<Boolean> defaultRun,
                       Optional<Style> parentStyle, Styles styles) {
        String fontSize = getFontSize(runProperties);

        while (parentStyle != null && fontSize == null) {
            fontSize = getFontSize(getRunProperties(parentStyle));
            parentStyle = getParentStyle(parentStyle, styles);
        }

        if (fontSize == null) {
            fontSize = defaultRun.getFontSize();
        }

        run.setFontSize(fontSize);
    }

    void setBold(Run<Boolean> run, RPr runProperties, Run<Boolean> defaultRun,
                     Optional<Style> parentStyle, Styles styles) {
        Boolean isBold = getBold(runProperties);

        while (parentStyle != null && isBold == null) {
            isBold = getBold(getRunProperties(parentStyle));
            parentStyle = getParentStyle(parentStyle, styles);
        }

        if (isBold == null) {
            isBold = defaultRun.getBold();
        }

        run.setBold(isBold);
    }

    void setItalic(Run<Boolean> run, RPr runProperties, Run<Boolean> defaultRun,
                 Optional<Style> parentStyle, Styles styles) {
        Boolean isItalic = getItalic(runProperties);

        while (parentStyle != null && isItalic == null) {
            isItalic = getItalic(getRunProperties(parentStyle));
            parentStyle = getParentStyle(parentStyle, styles);
        }

        if (isItalic == null) {
            isItalic = defaultRun.getItalic();
        }

        run.setItalic(isItalic);
    }

    void setStrikethrough(Run<Boolean> run, RPr runProperties, Run<Boolean> defaultRun,
                   Optional<Style> parentStyle, Styles styles) {
        Boolean isStrikethrough = getStrikethrough(runProperties);

        while (parentStyle != null && isStrikethrough == null) {
            isStrikethrough = getStrikethrough(getRunProperties(parentStyle));
            parentStyle = getParentStyle(parentStyle, styles);
        }

        if (isStrikethrough == null) {
            isStrikethrough = defaultRun.getStrikethrough();
        }

        run.setStrikethrough(isStrikethrough);
    }

    void setUnderline(Run<Boolean> run, RPr runProperties, Run<Boolean> defaultRun,
                          Optional<Style> parentStyle, Styles styles) {
        String underline = getUnderline(runProperties);

        while (parentStyle != null && underline == null) {
            underline = getUnderline(getRunProperties(parentStyle));
            parentStyle = getParentStyle(parentStyle, styles);
        }

        if (underline == null) {
            underline = defaultRun.getUnderline();
        }

        run.setUnderline(underline);
    }

    void setTextColor(Run<Boolean> run, RPr runProperties, Run<Boolean> defaultRun,
                      Optional<Style> parentStyle, Styles styles) {
        String textColor = getTextColor(runProperties);

        while (parentStyle != null && textColor == null) {
            textColor = getTextColor(getRunProperties(parentStyle));
            parentStyle = getParentStyle(parentStyle, styles);
        }

        if (textColor == null) {
            textColor = defaultRun.getTextColor();
        }

        run.setTextColor(textColor);
    }
}
