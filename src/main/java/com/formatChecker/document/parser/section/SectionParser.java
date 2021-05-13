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

    public Section parseSection(SectPr sectionProperties) {
        Section section = new Section();

        setOrientation(section, sectionProperties);
        setMargins(section, sectionProperties);
        setPageHeight(section, sectionProperties);
        setPageWidth(section, sectionProperties);

        return section;
    }

    String getOrientation(SectPr sectionProperties) {
        STPageOrientation orientation = sectionProperties.getPgSz().getOrient();
        return orientation != null ? orientation.value().toLowerCase() : DEFAULT_ORIENTATION;
    }

    String getPageHeight(SectPr sectionProperties) {
        BigInteger pageHeight = sectionProperties.getPgSz().getH();
        return pageValToCm(pageHeight).toString();
    }

    String getPageWidth(SectPr sectionProperties) {
        BigInteger pageWidth = sectionProperties.getPgSz().getW();
        return pageValToCm(pageWidth).toString();
    }

    List<Double> getMargins(SectPr sectionProperties) {
        PgMar margins = sectionProperties.getPgMar();

        return new ArrayList<>(Arrays.asList(
                pageValToCm(margins.getTop()),
                pageValToCm(margins.getRight()),
                pageValToCm(margins.getBottom()),
                pageValToCm(margins.getLeft())));
    }

    void setOrientation(Section section, SectPr sectionProperties) {
        section.setOrientation(getOrientation(sectionProperties));
    }

    void setPageHeight(Section section, SectPr sectionProperties) {
        section.setPageHeight(getPageHeight(sectionProperties));
    }

    void setPageWidth(Section section, SectPr sectionProperties) {
        section.setPageWidth(getPageWidth(sectionProperties));
    }

    void setMargins(Section section, SectPr sectionProperties) {
        section.setMargins(getMargins(sectionProperties));
    }
}
