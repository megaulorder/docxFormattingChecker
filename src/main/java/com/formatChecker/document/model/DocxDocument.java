package com.formatChecker.document.model;

import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.model.participants.Section;

import java.util.List;

public class DocxDocument {
    public DocxDocument(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public List<Paragraph> addParagraph(Paragraph paragraph) {
        paragraphs.add(paragraph);
        return paragraphs;
    }

    List<Paragraph>  paragraphs;
    Integer pages;
    Section<List<Double>> section;

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public void setSection(Section<List<Double>> section) {
        this.section = section;
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public Integer getPages() {
        return pages;
    }

    public Section<List<Double>> getSection() {
        return section;
    }
}
