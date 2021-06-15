package com.formatChecker.document.model;

import com.formatChecker.config.model.participants.Footer;
import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.model.participants.Section;

import java.util.List;

public class DocxDocument {
    public DocxDocument(List<Paragraph> paragraphs, List<Section<Double>> sections) {
        this.paragraphs = paragraphs;
        this.sections = sections;
    }

    List<Paragraph>  paragraphs;
    Integer pages;
    List<Section<Double>> sections;
    Footer footer;

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public Integer getPages() {
        return pages;
    }

    public Footer getFooter() {
        return footer;
    }

    public void setFooter(Footer footer) {
        this.footer = footer;
    }

    public List<Paragraph> addParagraph(Paragraph paragraph) {
        paragraphs.add(paragraph);
        return paragraphs;
    }

    public List<Section<Double>> addSection(Section<Double> section) {
        sections.add(section);
        return sections;
    }
}
