package com.formatChecker.fixer;

import com.formatChecker.config.converter.ConfigConverter;
import com.formatChecker.config.model.participants.Run;
import org.docx4j.wml.R;

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
    }

    void fixBold() {
        run.getRPr().getB().setVal(expectedRun.getBold());
    }

    void fixItalic() {
        run.getRPr().getI().setVal(expectedRun.getItalic());
    }

    void fixStrikethrough() {
        run.getRPr().getStrike().setVal(expectedRun.getStrikethrough());
    }
}
