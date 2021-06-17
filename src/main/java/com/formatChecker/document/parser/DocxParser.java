package com.formatChecker.document.parser;

import com.formatChecker.document.model.DocumentData;
import com.formatChecker.document.model.DocxDocument;
import com.formatChecker.document.model.Heading;
import org.docx4j.jaxb.XPathBinderAssociationIsPartialException;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Body;
import org.docx4j.wml.P;
import org.docx4j.wml.SectPr;
import org.docx4j.wml.Styles;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

public class DocxParser {
    private static final String PARAGRAPHS_ON_NEW_PAGE_XPATH = "//w:p[w:r[w:br[@w:type='page']]]/following-sibling::w:p[1]";
    private final static String TOC_XPATH = "//w:sdtContent/w:p[w:hyperlink[starts-with(@w:anchor, '_Toc')]]";
    private final static String TOC_ELEMENT_XPATH = "//w:p[w:bookmarkStart[starts-with(@w:name,'_Toc')]]";

    WordprocessingMLPackage wordprocessingMLPackage;
    DocxDocument document;
    DocumentData documentData;
    List<SectPr> sectionsProperties;
    List<String> paragraphsOnNewPage;
    List<Heading> headings;

    public DocxParser(WordprocessingMLPackage wordprocessingMLPackage)
            throws JAXBException, XPathBinderAssociationIsPartialException {
        this.wordprocessingMLPackage = wordprocessingMLPackage;
        this.headings = parseHeadings();
        this.documentData = parseDocumentData();
        this.document = parseDocument();
        this.sectionsProperties = parseSectionsProperties();
        this.paragraphsOnNewPage = parseParagraphsOnNewPage();
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

    List<String> parseParagraphsOnNewPage() throws JAXBException, XPathBinderAssociationIsPartialException {
        List<String> paragraphIds = new ArrayList<>();

        List<Object> TOCObjects = wordprocessingMLPackage.getMainDocumentPart()
                .getJAXBNodesViaXPath(PARAGRAPHS_ON_NEW_PAGE_XPATH, false);

        for (Object o : TOCObjects) {
            paragraphIds.add(((P) o).getParaId());
        }

        return paragraphIds;
    }

    List<Heading> parseHeadings() throws JAXBException, XPathBinderAssociationIsPartialException {
        List<Object> TOCObjects = wordprocessingMLPackage.getMainDocumentPart()
                .getJAXBNodesViaXPath(TOC_XPATH, false);

        List<Object> TOCElementObjects = wordprocessingMLPackage
                .getMainDocumentPart().getJAXBNodesViaXPath(TOC_ELEMENT_XPATH, false);

        List<Heading> headings = new ArrayList<>();

        for (int i = 0; i < TOCObjects.size(); ++i) {
            Heading heading = new Heading();
            heading.setLevel(Integer.parseInt(((P) TOCObjects.get(i)).getPPr().getPStyle().getVal()) / 10);
            heading.setText(TOCElementObjects.get(i).toString());
            headings.add(heading);
        }

        return headings;
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

    public List<String> getParagraphsOnNewPage() {
        return paragraphsOnNewPage;
    }

    public List<Heading> getHeadings() {
        return headings;
    }
}
