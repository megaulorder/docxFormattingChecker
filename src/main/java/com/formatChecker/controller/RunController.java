package com.formatChecker.controller;

import com.formatChecker.comparer.differ.RunDiffer;
import com.formatChecker.config.model.Config;
import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.model.participants.Run;
import com.formatChecker.fixer.RunFixer;
import org.docx4j.wml.R;

import java.util.Map;

public class RunController {
    private static final String HEADER_STYLE_NAME = "header";
    private static final String BODY_STYLE_NAME = "body";

    Integer index;
    R documentRun;
    Run<Boolean, Double> actualRun;
    Run expectedRun;
    Boolean shouldFix;

    Paragraph differenceParagraph;
    Map<Integer, String> configStyles;
    Config config;
    Boolean isHeader;

    public RunController(Integer index, R documentRun, Run<Boolean, Double> actualRun, Paragraph differenceParagraph,
                         Map<Integer, String> configStyles, Boolean isHeader, Config config, Boolean shouldFix) {
        this.index = index;
        this.documentRun = documentRun;
        this.actualRun = actualRun;
        this.shouldFix = shouldFix;

        this.differenceParagraph = differenceParagraph;
        this.config = config;
        this.configStyles = configStyles;
        this.isHeader = isHeader;
    }

    public void parseRun() {

        if (configStyles == null) {
            if (isHeader) {
                expectedRun = config.getStyles().get(HEADER_STYLE_NAME).getRun();
            }
            else {
                expectedRun = config.getStyles().get(BODY_STYLE_NAME).getRun();
            }
        }
        else {
            expectedRun = config.getStyles().get(configStyles.get(index)).getRun();
        }
        compareRun();
    }

    void compareRun() {
        Run differenceRun = new RunDiffer(actualRun, expectedRun).getRunDifference();
        differenceParagraph.addRun(differenceRun);

        if (shouldFix) {
            new RunFixer(this.documentRun, this.actualRun, expectedRun, differenceRun).fixRun();
        }
    }
}
