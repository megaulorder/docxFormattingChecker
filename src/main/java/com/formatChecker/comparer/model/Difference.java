package com.formatChecker.comparer.model;

import com.formatChecker.config.model.participants.Footer;
import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.model.participants.Section;

import java.util.List;

public class Difference {
    public Difference(List<Paragraph> paragraphs, List<Section<String>> sections) {
        this.paragraphs = paragraphs;
        this.sections = sections;
    }

    String pages;

    List<Section<String>> sections;
    List<Paragraph> paragraphs;
    Footer footer;

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPages() {
        return pages;
    }

    public List<Section<String>> getSections() {
        return sections;
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
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

    public List<Section<String>> addSection(Section<String> section) {
        sections.add(section);
        return sections;
    }
}
