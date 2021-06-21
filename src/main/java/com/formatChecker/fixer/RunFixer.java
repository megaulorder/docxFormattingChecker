package com.formatChecker.fixer;

import com.formatChecker.config.converter.ConfigConverter;
import com.formatChecker.config.model.participants.Run;
import org.docx4j.wml.*;

public class RunFixer implements ConfigConverter {
    R run;
    RPr runProperties;
    Run<Boolean, Double> actualRun;
    Run<Boolean, Double> expectedRun;
    Run<String, String> differenceRun;

    public RunFixer(R run,
                    Run<Boolean, Double> actualRun,
                    Run<Boolean, Double> expectedRun,
                    Run<String, String> differenceRun) {
        this.run = run;
        this.runProperties = run.getRPr();
        this.actualRun = actualRun;
        this.expectedRun = expectedRun;
        this.differenceRun = differenceRun;
    }

    public void fixRun() {
        if (runProperties == null)
            fixRunProperties();
        else {
            fixBold(runProperties);
            fixItalic(runProperties);
            fixStrikethrough(runProperties);
            fixFontSize(runProperties);
            fixFontFamily(runProperties);
            fixUnderline(runProperties);
        }
    }

    void fixRunProperties() {
        RPr newRunProperties = new RPr();

        fixBold(newRunProperties);
        fixItalic(newRunProperties);
        fixStrikethrough(newRunProperties);
        fixFontSize(newRunProperties);
        fixFontFamily(newRunProperties);
        fixUnderline(newRunProperties);

        run.setRPr(newRunProperties);
    }

    void fixBold(RPr rpr) {
        if (differenceRun.getBold() != null) {
            BooleanDefaultTrue bold = new BooleanDefaultTrue();
            bold.setVal(expectedRun.getBold());
            rpr.setB(bold);
        }
    }

    void fixItalic(RPr rpr) {
        if (differenceRun.getItalic() != null) {
            BooleanDefaultTrue italic = new BooleanDefaultTrue();
            italic.setVal(expectedRun.getItalic());
            rpr.setI(italic);
        }
    }

    void fixStrikethrough(RPr rpr) {
        if (differenceRun.getStrikethrough() != null) {
            BooleanDefaultTrue strikethrough = new BooleanDefaultTrue();
            strikethrough.setVal(expectedRun.getStrikethrough());
            rpr.setI(strikethrough);
        }
    }

    void fixFontSize(RPr rpr) {
        if (differenceRun.getFontSize() != null) {
            HpsMeasure fontSize = new HpsMeasure();
            fontSize.setVal(ptToHalfPt(expectedRun.getFontSize()));
            rpr.setSz(fontSize);
        }
    }

    void fixFontFamily(RPr rpr) {
        if (differenceRun.getFontFamily() != null) {
            RFonts fontFamily = new RFonts();
            fontFamily.setAscii(expectedRun.getFontFamily());
            rpr.setRFonts(fontFamily);
        }
    }

    void fixUnderline(RPr rpr) {
        if (differenceRun.getUnderline() != null) {
            U underline = new U();
            underline.setVal(convertUnderline(expectedRun.getUnderline()));
            rpr.setU(underline);
        }
    }
}
