package com.formatChecker.document.parser;

import com.formatChecker.document.model.DocumentData;
import com.formatChecker.document.model.DocxDocument;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Body;
import org.docx4j.wml.SectPr;
import org.docx4j.wml.Styles;

import java.util.ArrayList;
import java.util.List;

public class DocxParser {
    WordprocessingMLPackage wordprocessingMLPackage;
    DocxDocument document;
    DocumentData documentData;
    List<SectPr> sectionsProperties;

    public DocxParser(WordprocessingMLPackage wordprocessingMLPackage) {
        this.wordprocessingMLPackage = wordprocessingMLPackage;
        this.documentData = parseDocumentData();
        this.document = parseDocument();
        this.sectionsProperties = parseSectionsProperties();
    }

    DocumentData parseDocumentData() {
        MainDocumentPart documentPart = wordprocessingMLPackage.getMainDocumentPart();
        Body body = documentPart.getJaxbElement().getBody();
        Styles styles = documentPart.getStyleDefinitionsPart().getJaxbElement();
        List<SectionWrapper> sections = wordprocessingMLPackage.getDocumentModel().getSections();

        return new DocumentData(
                wordprocessingMLPackage.getDocPropsExtendedPart().getJaxbElement(),
                styles.getDocDefaults(),
                documentPart.getThemePart(),
                sections,
                styles,
                body.getContent(),
                sections.get(0).getHeaderFooterPolicy()
        );
    }

    DocxDocument parseDocument() {
        DocxDocument docxDocument = new DocxDocument();
        docxDocument.setPages(documentData.getDocumentInfo().getPages());

        return docxDocument;
    }

    List<SectPr> parseSectionsProperties() {
        List<SectionWrapper> sections = documentData.getSections();

        List<SectPr> sectionsProperties = new ArrayList<>();

        for (SectionWrapper sw : sections) {
            sectionsProperties.add(sw.getSectPr());
        }

        return sectionsProperties;
    }

    public DocxDocument getDocument() {
        return document;
    }

    public DocumentData getDocumentData() {
        return documentData;
    }

    public List<SectPr> getSectionsProperties() {
        return sectionsProperties;
    }
}
