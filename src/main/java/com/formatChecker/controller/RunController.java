package com.formatChecker.controller;

import com.formatChecker.comparer.differ.RunDiffer;
import com.formatChecker.config.model.Config;
import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.model.participants.Run;
import com.formatChecker.fixer.RunFixer;
import org.docx4j.wml.R;

import java.util.Map;

public class RunController {
    private static final String HEADING_STYLE_NAME = "heading";
    private static final String BODY_STYLE_NAME = "body";

    Integer index;
    R documentRun;
    Run<Boolean, Double> actualRun;
    Run<Boolean, Double> expectedRun;
    Boolean shouldFix;

    Paragraph<String, String> differenceParagraph;
    Map<Integer, String> configStyles;
    Config config;
    Integer headingLevel;

    public RunController(Integer index,
                         R documentRun,
                         Run<Boolean, Double> actualRun,
                         Paragraph<String, String> differenceParagraph,
                         Map<Integer, String> configStyles,
                         Integer headingLevel,
                         Config config,
                         Boolean shouldFix) {
        this.index = index;
        this.documentRun = documentRun;
        this.actualRun = actualRun;
        this.shouldFix = shouldFix;

        this.differenceParagraph = differenceParagraph;
        this.config = config;
        this.configStyles = configStyles;
        this.headingLevel = headingLevel;
    }

    public void parseRun() {

        if (configStyles == null) {
            if (headingLevel > 0) {
                expectedRun = config.getStyles().get(HEADING_STYLE_NAME + headingLevel).getRun();
            } else {
                expectedRun = config.getStyles().get(BODY_STYLE_NAME).getRun();
            }
        } else {
            expectedRun = config.getStyles().get(configStyles.get(index)).getRun();
        }
        compareRun();
    }

    void compareRun() {
        Run<String, String> differenceRun = new RunDiffer(actualRun, expectedRun).getRunDifference();
        differenceParagraph.addRun(differenceRun);

        if (shouldFix) {
            new RunFixer(this.documentRun, this.actualRun, expectedRun, differenceRun).fixRun();
        }
    }
}
