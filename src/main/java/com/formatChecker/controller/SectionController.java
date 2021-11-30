package com.formatChecker.controller;

import com.formatChecker.comparer.differ.SectionDiffer;
import com.formatChecker.comparer.model.Difference;
import com.formatChecker.config.model.participants.Section;
import com.formatChecker.document.model.DocxDocument;
import com.formatChecker.document.parser.section.SectionParser;
import com.formatChecker.fixer.SectionFixer;
import org.docx4j.wml.SectPr;

public class SectionController extends ParametersProcessing {
    private final SectPr sectionProperties;
    private final DocxDocument docxDocument;
    private final Difference difference;
    private final Section<Double> expectedSection;
    private final Boolean shouldFix;

    private Section<Double> section;
    private Section<String> differenceSection;

    public SectionController(SectPr sectionProperties,
                             DocxDocument docxDocument,
                             Difference difference,
                             Section<Double> expectedSection,
                             Boolean shouldFix) {
        this.sectionProperties = sectionProperties;
        this.docxDocument = docxDocument;
        this.difference = difference;
        this.expectedSection = expectedSection;
        this.shouldFix = shouldFix;
    }
    @Override
    void parse() {
       section = new SectionParser(sectionProperties).getSection();
    }

    @Override
    void compare() {
        Section<String> differenceSection = new SectionDiffer(section, expectedSection).getDifferenceSection();
        difference.addSection(differenceSection);
    }

    @Override
    void fix() {
        if (shouldFix) {
            if (differenceSection != null)
                new SectionFixer(sectionProperties, section, expectedSection, differenceSection).fixSection();
        }
    }
}
