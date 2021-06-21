package com.formatChecker.fixer;

import com.formatChecker.config.converter.ConfigConverter;
import com.formatChecker.config.model.participants.Section;
import org.docx4j.wml.SectPr;
import org.docx4j.wml.SectPr.PgMar;
import org.docx4j.wml.SectPr.PgSz;

public class SectionFixer implements ConfigConverter {
    SectPr sectionProperties;
    Section<Double> actualSection;
    Section<Double> expectedSection;
    Section<String> differenceSection;

    public SectionFixer(SectPr sectionProperties,
                        Section<Double> actualSection,
                        Section<Double> expectedSection,
                        Section<String> differenceSection) {
        this.sectionProperties = sectionProperties;
        this.actualSection = actualSection;
        this.expectedSection = expectedSection;
        this.differenceSection = differenceSection;
    }

    public void fixSection() {
        fixPageSize();
        fixMargins();
    }

    void fixPageSize() {
        PgSz pageProperties = new PgSz();

        if (differenceSection.getOrientation() == null &&
                differenceSection.getPageWidth() == null &&
                differenceSection.getPageHeight() == null)
            return;

        if (differenceSection.getOrientation() != null)
            pageProperties.setOrient(convertOrientation(expectedSection.getOrientation()));
        else
            pageProperties.setOrient(convertOrientation(actualSection.getOrientation()));

        if (differenceSection.getPageHeight() != null)
            pageProperties.setH(cmToPageVal(expectedSection.getPageHeight()));
        else
            pageProperties.setH(cmToPageVal(actualSection.getPageHeight()));

        if (differenceSection.getPageWidth() != null)
            pageProperties.setW(cmToPageVal(expectedSection.getPageWidth()));
        else
            pageProperties.setW(cmToPageVal(actualSection.getPageWidth()));

        sectionProperties.setPgSz(pageProperties);
    }

    void fixMargins() {
        PgMar pageMargins = new PgMar();

        if (differenceSection.getMargins() == null)
            return;

        if (differenceSection.getMargins().get(0) != null)
            pageMargins.setTop(cmToPageVal(expectedSection.getMargins().get(0)));
        else
            pageMargins.setTop(cmToPageVal(actualSection.getMargins().get(0)));

        if (differenceSection.getMargins().get(1) != null)
            pageMargins.setRight(cmToPageVal(expectedSection.getMargins().get(1)));
        else
            pageMargins.setRight(cmToPageVal(actualSection.getMargins().get(1)));

        if (differenceSection.getMargins().get(2) != null)
            pageMargins.setBottom(cmToPageVal(expectedSection.getMargins().get(2)));
        else
            pageMargins.setBottom(cmToPageVal(actualSection.getMargins().get(2)));

        if (differenceSection.getMargins().get(3) != null)
            pageMargins.setLeft(cmToPageVal(expectedSection.getMargins().get(3)));
        else
            pageMargins.setLeft(cmToPageVal(actualSection.getMargins().get(3)));

        sectionProperties.setPgMar(pageMargins);
    }
}
