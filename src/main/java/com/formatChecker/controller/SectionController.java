package com.formatChecker.controller;

import com.formatChecker.comparer.differ.SectionDiffer;
import com.formatChecker.comparer.model.Difference;
import com.formatChecker.config.model.Config;
import com.formatChecker.config.model.participants.Section;
import com.formatChecker.document.model.DocxDocument;
import com.formatChecker.document.parser.section.SectionParser;
import com.formatChecker.fixer.SectionFixer;
import org.docx4j.wml.SectPr;

public class SectionController {
    SectPr sectionProperties;

    DocxDocument docxDocument;
    Difference difference;
    Config config;

    Boolean shouldFix;

    public SectionController(SectPr sectionProperties, DocxDocument docxDocument, Difference difference,
                             Config config, Boolean shouldFix) {
        this.sectionProperties = sectionProperties;
        this.docxDocument = docxDocument;
        this.difference = difference;
        this.config = config;
        this.shouldFix = shouldFix;
    }

    public void parseSection() {
        Section<Double> section = new SectionParser(sectionProperties).parseSection();
        docxDocument.addSection(section);

        Section<String> differenceSection = new SectionDiffer(section, config.getSection()).getSectionDifference();
        difference.addSection(differenceSection);
        if (shouldFix) {
            new SectionFixer(sectionProperties, section, config.getSection(), differenceSection).fixSection();
        }
    }
}
