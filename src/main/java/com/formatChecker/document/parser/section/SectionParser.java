package com.formatChecker.document.parser.section;

import com.formatChecker.config.model.participants.Section;
import com.formatChecker.document.converter.ValuesConverter;
import org.docx4j.wml.STPageOrientation;
import org.docx4j.wml.SectPr;
import org.docx4j.wml.SectPr.PgMar;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SectionParser implements ValuesConverter {
    static final String DEFAULT_ORIENTATION = "portrait";

    SectPr sectionProperties;
    Section<Double> section;

    public SectionParser(SectPr sectionProperties) {
        this.sectionProperties = sectionProperties;
        this.section = parseSection();
    }

    Section<Double> parseSection() {
        Section<Double> section = new Section<>();

        setOrientation(section);
        setMargins(section);
        setPageHeight(section);
        setPageWidth(section);

        return section;
    }

    String getOrientation() {
        STPageOrientation orientation = sectionProperties.getPgSz().getOrient();
        return orientation != null ? orientation.value().toLowerCase() : DEFAULT_ORIENTATION;
    }

    Double getPageHeight() {
        BigInteger pageHeight = sectionProperties.getPgSz().getH();
        return pageValToCm(pageHeight);
    }

    Double getPageWidth() {
        BigInteger pageWidth = sectionProperties.getPgSz().getW();
        return pageValToCm(pageWidth);
    }

    List<Double> getMargins() {
        PgMar margins = sectionProperties.getPgMar();

        return new ArrayList<>(Arrays.asList(
                pageValToCm(margins.getTop()),
                pageValToCm(margins.getRight()),
                pageValToCm(margins.getBottom()),
                pageValToCm(margins.getLeft())));
    }

    void setOrientation(Section<Double> section) {
        section.setOrientation(getOrientation());
    }

    void setPageHeight(Section<Double> section) {
        section.setPageHeight(getPageHeight());
    }

    void setPageWidth(Section<Double> section) {
        section.setPageWidth(getPageWidth());
    }

    void setMargins(Section<Double> section) {
        section.setMargins(getMargins());
    }

    public Section<Double> getSection() {
        return section;
    }
}
