package com.formatChecker.fixer;

import com.formatChecker.config.converter.ConfigConverter;
import com.formatChecker.config.model.participants.Run;
import org.docx4j.wml.*;

public class RunFixer implements ConfigConverter {
    R run;
    Run<Boolean, Double> actualRun;
    Run<Boolean, Double> expectedRun;
    Run<String, String> differenceRun;

    public RunFixer(R run, Run<Boolean, Double> actualRun, Run<Boolean, Double> expectedRun,
                    Run<String, String> differenceRun) {
        this.run = run;
        this.actualRun = actualRun;
        this.expectedRun = expectedRun;
        this.differenceRun = differenceRun;
    }

    public void fixRun() {
        fixBold();
        fixItalic();
        fixStrikethrough();
        fixFontSize();
        fixFontFamily();
        fixUnderline();
    }

    void fixBold() {
        if (differenceRun.getBold() != null) {
            BooleanDefaultTrue bold = new BooleanDefaultTrue();
            bold.setVal(expectedRun.getBold());
            run.getRPr().setB(bold);
        }
    }

    void fixItalic() {
        if (differenceRun.getItalic() != null) {
            BooleanDefaultTrue italic = new BooleanDefaultTrue();
            italic.setVal(expectedRun.getItalic());
            run.getRPr().setI(italic);
        }
    }

    void fixStrikethrough() {
        if (differenceRun.getStrikethrough() != null) {
            BooleanDefaultTrue strikethrough = new BooleanDefaultTrue();
            strikethrough.setVal(expectedRun.getStrikethrough());
            run.getRPr().setI(strikethrough);
        }
    }

    void fixFontSize() {
        if (differenceRun.getFontSize() != null) {
            HpsMeasure fontSize = new HpsMeasure();
            fontSize.setVal(ptToHalfPt(expectedRun.getFontSize()));
            run.getRPr().setSz(fontSize);
        }
    }

    void fixFontFamily() {
        if (differenceRun.getFontFamily() != null) {
            RFonts fontFamily = new RFonts();
            fontFamily.setAscii(expectedRun.getFontFamily());
            run.getRPr().setRFonts(fontFamily);
        }
    }

    void fixUnderline() {
        if (differenceRun.getUnderline() != null) {
            U underline = new U();
            underline.setVal(convertUnderline(expectedRun.getUnderline()));
            run.getRPr().setU(underline);
        }
    }
}
