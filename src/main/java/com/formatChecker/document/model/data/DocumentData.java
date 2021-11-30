package com.formatChecker.document.model.data;

import org.docx4j.docProps.extended.Properties;
import org.docx4j.model.structure.HeaderFooterPolicy;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.parts.ThemePart;
import org.docx4j.wml.DocDefaults;
import org.docx4j.wml.Styles;

import java.util.List;

public class DocumentData {

    private static Properties documentInfo;
    private static DocDefaults docDefaults;
    private static ThemePart themePart;
    private static List<SectionWrapper> sections;
    private static Styles styles;
    private static List<Object> paragraphs;
    private static HeaderFooterPolicy headersAndFooters;

    private static DocumentData instance = null;

    private DocumentData() {
    }

    public static DocumentData getInstance() {
        if (instance == null)
            instance = new DocumentData();
        return instance;
    }

    public void init(Properties documentInfo,
                            DocDefaults docDefaults,
                            ThemePart themePart,
                            List<SectionWrapper> sections,
                            Styles styles,
                            List<Object> paragraphs,
                            HeaderFooterPolicy headersAndFooters) {
        getInstance().setDocumentInfo(documentInfo);
        getInstance().setDocDefaults(docDefaults);
        getInstance().setThemePart(themePart);
        getInstance().setSections(sections);
        getInstance().setStyles(styles);
        getInstance().setParagraphs(paragraphs);
        getInstance().setHeadersAndFooters(headersAndFooters);
    }

    private void setDocumentInfo(Properties documentInfo) {
        DocumentData.documentInfo = documentInfo;
    }

    private void setDocDefaults(DocDefaults docDefaults) {
        DocumentData.docDefaults = docDefaults;
    }

    private void setThemePart(ThemePart themePart) {
        DocumentData.themePart = themePart;
    }

    private void setSections(List<SectionWrapper> sections) {
        DocumentData.sections = sections;
    }

    private void setStyles(Styles styles) {
        DocumentData.styles = styles;
    }

    private void setParagraphs(List<Object> paragraphs) {
        DocumentData.paragraphs = paragraphs;
    }

    private void setHeadersAndFooters(HeaderFooterPolicy headersAndFooters) {
        DocumentData.headersAndFooters = headersAndFooters;
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

    public HeaderFooterPolicy getHeadersAndFooters() {
        return headersAndFooters;
    }
}
