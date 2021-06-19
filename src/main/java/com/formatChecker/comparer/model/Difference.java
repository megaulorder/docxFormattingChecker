package com.formatChecker.comparer.model;

import com.formatChecker.config.model.participants.Footer;
import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.model.participants.Section;
import com.formatChecker.document.model.participants.Drawing;
import com.formatChecker.document.model.participants.Heading;

import java.util.ArrayList;
import java.util.List;

public class Difference {
    public Difference() {
        this.paragraphs = new ArrayList<>();
        this.sections = new ArrayList<>();
        this.drawings = new ArrayList<>();
    }

    String pages;

    List<Section<String>> sections;
    List<Paragraph<String, String>> paragraphs;
    Footer footer;
    List<Drawing<String, String>> drawings;
    List<Heading> headings;

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

    public List<Drawing<String, String>> getDrawings() {
        return drawings;
    }

    public void setFooter(Footer footer) {
        this.footer = footer;
    }

    public List<Heading> getHeadings() {
        return headings;
    }

    public void setHeadings(List<Heading> headings) {
        this.headings = headings;
    }

    public void addParagraph(Paragraph<String, String> paragraph) {
        paragraphs.add(paragraph);
    }

    public void addSection(Section<String> section) {
        sections.add(section);
    }

    public void addDrawing(Drawing<String, String> drawing) {
        drawings.add(drawing);
    }

}
