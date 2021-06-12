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
        if (differenceRun.getBold() != null)
            fixBold();
        if (differenceRun.getItalic() != null)
            fixItalic();
        if (differenceRun.getStrikethrough() != null)
            fixStrikethrough();
        if (differenceRun.getFontSize() != null)
            fixFontSize();
        if (differenceRun.getFontFamily() != null)
            fixFontFamily();
        if (differenceRun.getUnderline() != null)
            fixUnderline();
    }

    void fixBold() {
        BooleanDefaultTrue bold = new BooleanDefaultTrue();
        bold.setVal(expectedRun.getBold());
        run.getRPr().setB(bold);
    }

    void fixItalic() {
        BooleanDefaultTrue italic = new BooleanDefaultTrue();
        italic.setVal(expectedRun.getItalic());
        run.getRPr().setI(italic);
    }

    void fixStrikethrough() {
        BooleanDefaultTrue strikethrough = new BooleanDefaultTrue();
        strikethrough.setVal(expectedRun.getStrikethrough());
        run.getRPr().setI(strikethrough);
    }

    void fixFontSize() {
        HpsMeasure fontSize = new HpsMeasure();
        fontSize.setVal(ptToHalfPt(expectedRun.getFontSize()));
        run.getRPr().setSz(fontSize);
    }

    void fixFontFamily() {
        RFonts fontFamily = new RFonts();
        fontFamily.setAscii(expectedRun.getFontFamily());
        run.getRPr().setRFonts(fontFamily);
    }

    void fixUnderline() {
        U underline = new U();
        underline.setVal(convertUnderline(expectedRun.getUnderline()));
        run.getRPr().setU(underline);
    }
}
