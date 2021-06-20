package com.formatChecker.document.model;

import com.formatChecker.config.model.participants.ConfigDrawing;
import com.formatChecker.config.model.participants.Footer;
import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.model.participants.Section;
import com.formatChecker.document.model.participants.Drawing;

import java.util.ArrayList;
import java.util.List;

public class DocxDocument {
    public DocxDocument() {
        this.paragraphs = new ArrayList<>();
        this.sections = new ArrayList<>();
        this.drawings = new ArrayList<>();
    }

    List<Paragraph<Double, Boolean>> paragraphs;
    Integer pages;
    List<Section<Double>> sections;
    Footer footer;
    List<Drawing<Double, Boolean>> drawings;

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getPages() {
        return pages;
    }

    public void setFooter(Footer footer) {
        this.footer = footer;
    }

    public void addParagraph(Paragraph<Double, Boolean> paragraph) {
        paragraphs.add(paragraph);
    }

    public void addSection(Section<Double> section) {
        sections.add(section);
    }

    public void addDrawing(Drawing<Double, Boolean> drawing) {
        drawings.add(drawing);
    }

    public Footer getFooter() {
        return footer;
    }

    public List<Paragraph<Double, Boolean>> getParagraphs() {
        return paragraphs;
    }

    public List<Section<Double>> getSections() {
        return sections;
    }

    public List<Drawing<Double, Boolean>> getDrawings() {
        return drawings;
    }
}
