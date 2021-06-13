package com.formatChecker.document.model;

import org.docx4j.docProps.extended.Properties;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.parts.ThemePart;
import org.docx4j.wml.DocDefaults;
import org.docx4j.wml.Styles;

import java.util.List;

public class DocumentData {
    Properties documentInfo;
    DocDefaults docDefaults;
    ThemePart themePart;
    List<SectionWrapper> sections;
    Styles styles;
    List<Object> paragraphs;

    public DocumentData(Properties documentInfo, DocDefaults docDefaults, ThemePart themePart,
                        List<SectionWrapper> sections, Styles styles, List<Object> paragraphs) {
        this.documentInfo = documentInfo;
        this.docDefaults = docDefaults;
        this.themePart = themePart;
        this.sections = sections;
        this.styles = styles;
        this.paragraphs = paragraphs;
    }

    public Properties getDocumentInfo() {
        return documentInfo;
    }

    public DocDefaults getDocDefaults() {
        return docDefaults;
    }

    public ThemePart getThemePart() {
        return themePart;
    }

    public List<SectionWrapper> getSections() {
        return sections;
    }

    public List<Object> getParagraphs() {
        return paragraphs;
    }

    public Styles getStyles() {
        return styles;
    }
}
