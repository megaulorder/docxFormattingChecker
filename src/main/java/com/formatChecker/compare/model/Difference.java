package com.formatChecker.compare.model;

import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.model.participants.Section;

import java.util.List;

public class Difference {
    public Difference(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public List<Paragraph> addParagraph(Paragraph paragraph) {
        paragraphs.add(paragraph);
        return paragraphs;
    }

    String pages;
    Section<List<String>> section;
    List<Paragraph> paragraphs;

    public void setPages(String pages) {
        this.pages = pages;
    }

    public void setSection(Section<List<String>> section) {
        this.section = section;
    }

    public String getPages() {
        return pages;
    }

    public Section<List<String>> getSection() {
        return section;
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }
}
