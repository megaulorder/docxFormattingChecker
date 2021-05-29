package com.formatChecker.compare.differ;

import com.formatChecker.config.model.participants.Run;

public class RunDiffer implements Differ {
    Run<Boolean> actualRun;
    Run<Boolean> expectedRun;

    public RunDiffer(Run<Boolean> actualRun, Run<Boolean> expectedRun) {
        this.actualRun = actualRun;
        this.expectedRun = expectedRun;
    }

    public Run<String> getRunDifference() {
        Run<String> runDifference = new Run<>();

        runDifference.setFontFamily(checkStringParameter(actualRun.getFontFamily(), expectedRun.getFontFamily(), "font family"));
        runDifference.setFontSize(checkStringParameter(actualRun.getFontSize(), expectedRun.getFontSize(), "font size", "pt"));
        runDifference.setBold(checkBooleanParameter(actualRun.getBold(), expectedRun.getBold(), "bold"));
        runDifference.setItalic(checkBooleanParameter(actualRun.getItalic(), expectedRun.getItalic(), "italic"));
        runDifference.setStrikethrough(checkBooleanParameter(actualRun.getStrikethrough(), expectedRun.getStrikethrough(), "strikethrough"));
        runDifference.setUnderline(checkStringParameter(actualRun.getUnderline(), expectedRun.getUnderline(), "underline"));
        runDifference.setTextColor(checkStringParameter(actualRun.getTextColor(), expectedRun.getTextColor(), "text color"));
        return runDifference;
    }
}
