package com.formatChecker.document.parser.section;

import com.formatChecker.config.model.participants.Section;
import com.formatChecker.document.converter.Converter;
import org.docx4j.wml.STPageOrientation;
import org.docx4j.wml.SectPr;
import org.docx4j.wml.SectPr.PgMar;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SectionParser implements Converter {
    static final String DEFAULT_ORIENTATION = "portrait";

    private final SectPr sectionProperties;

    public SectionParser(SectPr sectionProperties) {
        this.sectionProperties = sectionProperties;
    }

    public Section<List<Double>> parseSection() {
        Section<List<Double>> section = new Section<>();

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

    String getPageHeight() {
        BigInteger pageHeight = sectionProperties.getPgSz().getH();
        return pageValToCm(pageHeight).toString();
    }

    String getPageWidth() {
        BigInteger pageWidth = sectionProperties.getPgSz().getW();
        return pageValToCm(pageWidth).toString();
    }

    List<Double> getMargins() {
        PgMar margins = sectionProperties.getPgMar();

        return new ArrayList<>(Arrays.asList(
                pageValToCm(margins.getTop()),
                pageValToCm(margins.getRight()),
                pageValToCm(margins.getBottom()),
                pageValToCm(margins.getLeft())));
    }

    void setOrientation(Section<List<Double>> section) {
        section.setOrientation(getOrientation());
    }

    void setPageHeight(Section<List<Double>> section) {
        section.setPageHeight(getPageHeight());
    }

    void setPageWidth(Section<List<Double>> section) {
        section.setPageWidth(getPageWidth());
    }

    void setMargins(Section<List<Double>> section) {
        section.setMargins(getMargins());
    }
}
