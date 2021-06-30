package com.formatChecker.document.parser;

import com.formatChecker.comparer.model.participants.HeadingsList;
import com.formatChecker.config.model.participants.Heading;
import com.formatChecker.document.model.DocxDocument;
import com.formatChecker.document.model.data.DocumentData;
import com.formatChecker.document.model.participants.raw.DrawingRaw;
import com.formatChecker.document.model.participants.raw.DrawingsRawList;
import org.docx4j.docProps.extended.Properties;
import org.docx4j.jaxb.XPathBinderAssociationIsPartialException;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.DocumentSettingsPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

public class DocxParser {
    private static final String PARAGRAPHS_ON_NEW_PAGE_XPATH = "//w:p[w:r[w:br[@w:type='page']]]/following-sibling::w:p[1]";
    private final static String TOC_XPATH = "//w:sdtContent/w:p[w:hyperlink[starts-with(@w:anchor, '_Toc')]]";
    private final static String TOC_ELEMENT_XPATH = "//w:p[w:bookmarkStart[starts-with(@w:name,'_Toc')]]";
    private final static String DRAWING_PARAGRAPH_XPATH = "//w:p[w:r[w:drawing]]";
    private final static String DESCRIPTION_PARAGRAPH_XPATH = DRAWING_PARAGRAPH_XPATH + "/following-sibling::w:p[1]";

    WordprocessingMLPackage wordprocessingMLPackage;
    DocxDocument document;
    DocumentData documentData;
    List<SectPr> sectionsProperties;
    List<String> paragraphOnNewPageIds;
    HeadingsList headings;
    DrawingsRawList drawingsAndDescriptions;

    public DocxParser(WordprocessingMLPackage wordprocessingMLPackage)
            throws JAXBException, XPathBinderAssociationIsPartialException, InvalidFormatException {
        this.wordprocessingMLPackage = wordprocessingMLPackage;
        this.headings = parseHeadings();
        this.documentData = parseDocumentData();
        this.document = parseDocument();
        this.sectionsProperties = parseSectionsProperties();
        this.paragraphOnNewPageIds = parseParagraphsOnNewPage();
        this.drawingsAndDescriptions = parseDrawingsAndDescriptions();
    }

    DocumentData parseDocumentData() {
        MainDocumentPart documentPart = wordprocessingMLPackage.getMainDocumentPart();
        Body body = documentPart.getJaxbElement().getBody();
        Styles styles = documentPart.getStyleDefinitionsPart().getJaxbElement();
        List<SectionWrapper> sections = wordprocessingMLPackage.getDocumentModel().getSections();

        Properties properties = wordprocessingMLPackage.getDocPropsExtendedPart() == null ?
                null :
                wordprocessingMLPackage.getDocPropsExtendedPart().getJaxbElement();


        return new DocumentData(
                properties,
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
        if (documentData.getDocumentInfo() != null)
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

    HeadingsList parseHeadings() throws JAXBException, XPathBinderAssociationIsPartialException, InvalidFormatException {
        if (wordprocessingMLPackage.getMainDocumentPart().getContent().size() == 0) {
            System.out.println("Error: cannot read contents of .docx file. File might be corrupted.\n" +
                    "Updating Table of Contents might help to solve the problem.");
            System.exit(0);
        }

        updateTOC();

        List<Object> TOCObjects = wordprocessingMLPackage.getMainDocumentPart()
                .getJAXBNodesViaXPath(TOC_XPATH, false);

        List<Object> TOCElementObjects = wordprocessingMLPackage
                .getMainDocumentPart().getJAXBNodesViaXPath(TOC_ELEMENT_XPATH, false);

        HeadingsList headingsList = new HeadingsList();

        List<Heading> headings = new ArrayList<>();

        if (TOCObjects.size() != TOCElementObjects.size()) {
            headingsList.setWarningMessage("Cannot recognize headings: please fix or update Table of Contents manually");
            if (TOCObjects.size() < TOCElementObjects.size())
                headingsList.setWarningMessage("Cannot recognize headings: please remove extra links within the text " +
                        "and update Table of Contents");
        }

        else {
            for (int i = 0; i < TOCObjects.size(); ++i) {
                Heading heading = new Heading();

                if (((P) TOCObjects.get(i)).getPPr().getPStyle() == null) {
                    System.out.println("Cannot recognize heading levels. Please update Table of Contents\n");
                    break;
                }

                heading.setLevel(Integer.parseInt(((P) TOCObjects.get(i)).getPPr().getPStyle().getVal()) / 10);
                heading.setText(TOCElementObjects.get(i).toString());
                headings.add(heading);
            }

            headingsList.setHeadings(headings);
        }

        return headingsList;
    }

    void updateTOC() throws InvalidFormatException {
        DocumentSettingsPart dsp = wordprocessingMLPackage.getMainDocumentPart().getDocumentSettingsPart();
        CTSettings objCTSettings = dsp.getJaxbElement();
        BooleanDefaultTrue b = new BooleanDefaultTrue();
        b.setVal(true);
        objCTSettings.setUpdateFields(b);
        dsp.setJaxbElement(objCTSettings);
        wordprocessingMLPackage.getMainDocumentPart().addTargetPart(dsp);
    }

    DrawingsRawList parseDrawingsAndDescriptions() throws JAXBException, XPathBinderAssociationIsPartialException {
        DrawingsRawList drawingsRawList = new DrawingsRawList();

        List<DrawingRaw> drawingsAndDescriptions = new ArrayList<>();
        List<Object> drawingTOCObjects = wordprocessingMLPackage.getMainDocumentPart()
                .getJAXBNodesViaXPath(DRAWING_PARAGRAPH_XPATH, false);
        List<Object> descriptionTOCObjects = wordprocessingMLPackage.getMainDocumentPart()
                .getJAXBNodesViaXPath(DESCRIPTION_PARAGRAPH_XPATH, false);

        if (drawingTOCObjects.size() != descriptionTOCObjects.size())
            drawingsRawList.setErrorMessage(
                    "Error: number of drawings does not match number of drawing descriptions.\n\t" +
                    "Please add a description to each drawing.");

        for (int i = 0; i < drawingTOCObjects.size(); ++i) {
            DrawingRaw drawingRaw = new DrawingRaw();
            drawingRaw.setDrawing(((P) drawingTOCObjects.get(i)));

            if (i < descriptionTOCObjects.size())
                drawingRaw.setDescription(((P) descriptionTOCObjects.get(i)));

            drawingsAndDescriptions.add(drawingRaw);
        }

        drawingsRawList.setDrawingsRaw(drawingsAndDescriptions);

        return drawingsRawList;
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

    public List<String> getParagraphOnNewPageIds() {
        return paragraphOnNewPageIds;
    }

    public HeadingsList getHeadings() {
        return headings;
    }

    public DrawingsRawList getDrawingsAndDescriptions() {
        return drawingsAndDescriptions;
    }
}
