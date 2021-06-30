package com.formatChecker.comparer.model;

import com.formatChecker.comparer.model.participants.DrawingsList;
import com.formatChecker.comparer.model.participants.HeadingsList;
import com.formatChecker.config.model.participants.Footer;
import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.model.participants.Section;

import java.util.ArrayList;
import java.util.List;

public class Difference {
    public Difference() {
        this.paragraphs = new ArrayList<>();
        this.sections = new ArrayList<>();
    }

    String pages;

    List<Section<String>> sections;
    List<Paragraph<String, String>> paragraphs;
    Footer footer;
    DrawingsList drawings;
    HeadingsList headings;

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPages() {
        return pages;
    }

    public List<Section<String>> getSections() {
        return sections;
    }

    public List<Paragraph<String, String>> getParagraphs() {
        return paragraphs;
    }

    public Footer getFooter() {
        return footer;
    }

    public DrawingsList getDrawings() {
        return drawings;
    }

    public void setFooter(Footer footer) {
        this.footer = footer;
    }

    public HeadingsList getHeadings() {
        return headings;
    }

    public void setHeadings(HeadingsList headings) {
        this.headings = headings;
    }

    public void addParagraph(Paragraph<String, String> paragraph) {
        paragraphs.add(paragraph);
    }

    public void addSection(Section<String> section) {
        sections.add(section);
    }

    public void setDrawings(DrawingsList drawings) {
        this.drawings = drawings;
    }
}
