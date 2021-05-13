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

public class RunDirectParser implements RunParser {
     public Run parseRun(R r, String styleId, Styles styles, DocDefaults docDefaults, ThemePart themePart)
            throws Docx4JException {
        Run run = new Run();

        String text = getText(r);
        run.setText(text);

        RPr runProperties = r.getRPr();

        Run styleRun = getStyleProperties(styleId, styles, docDefaults, themePart);
        Run defaultRun = getDefaultProperties(docDefaults, themePart);

        setFontFamily(run, runProperties, styleRun, defaultRun);
        setFontSize(run, runProperties, styleRun, defaultRun);
        setBold(run, runProperties, styleRun, defaultRun);
        setItalic(run, runProperties, styleRun, defaultRun);
        setStrikethrough(run, runProperties, styleRun, defaultRun);
        setUnderline(run, runProperties, styleRun, defaultRun);
        setTextColor(run, runProperties, styleRun, defaultRun);

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
        else { return runProperties.getRFonts() == null ? null : runProperties.getRFonts().getAscii().toLowerCase(); }
    }

    Run getStyleProperties(String styleId, Styles styles, DocDefaults docDefaults, ThemePart themePart)
            throws Docx4JException {
        return new RunStyleParser().parseRun(styleId, styles, docDefaults, themePart);
    }

    void setFontFamily(Run run, RPr runProperties, Run styleRun, Run defaultRun) {
        String fontFamily = getFontFamily(runProperties);
        if (fontFamily == null) {
            fontFamily = styleRun != null ? styleRun.getFontFamily() : defaultRun.getFontFamily();
        }
        run.setFontFamily(fontFamily);
    }

    void setFontSize(Run run, RPr runProperties, Run styleRun, Run defaultRun) {
        String fontSize = getFontSize(runProperties);
        if (fontSize == null) {
            fontSize = styleRun != null ? styleRun.getFontSize() : defaultRun.getFontSize();
        }
        run.setFontSize(fontSize);
    }

    void setBold(Run run, RPr runProperties, Run styleRun, Run defaultRun) {
        Boolean isBold = getBold(runProperties);
        if (isBold == null) {
            isBold = styleRun != null ? styleRun.getBold() : defaultRun.getBold();
        }
        run.setBold(isBold);
    }

    void setItalic(Run run, RPr runProperties, Run styleRun, Run defaultRun) {
        Boolean isItalic = getItalic(runProperties);
        if (isItalic == null) {
            isItalic = styleRun != null ? styleRun.getItalic() : defaultRun.getItalic();
        }
        run.setItalic(isItalic);
    }

    void setStrikethrough(Run run, RPr runProperties, Run styleRun, Run defaultRun) {
        Boolean isStrikethrough = getStrikethrough(runProperties);
        if (isStrikethrough == null) {
            isStrikethrough = styleRun != null ? styleRun.getStrikethrough() : defaultRun.getStrikethrough();
        }
        run.setStrikethrough(isStrikethrough);
    }

    void setUnderline(Run run, RPr runProperties, Run styleRun, Run defaultRun) {
        String underline = getUnderline(runProperties);
        if (underline == null) {
            underline = styleRun != null ? styleRun.getUnderline() : defaultRun.getUnderline();
        }
        run.setUnderline(underline);
    }

    void setTextColor(Run run, RPr runProperties, Run styleRun, Run defaultRun) {
        String textColor = getTextColor(runProperties);
        if (textColor == null) {
            textColor = styleRun != null ? styleRun.getTextColor() : defaultRun.getTextColor();
        }
        run.setTextColor(textColor);
    }
}
