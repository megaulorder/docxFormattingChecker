package com.formatChecker.document.parser;

import com.formatChecker.comparer.differ.DocumentDiffer;
import com.formatChecker.comparer.model.Difference;
import com.formatChecker.config.model.Config;
import com.formatChecker.config.model.participants.Style;
import com.formatChecker.config.parser.ConfigParser;
import com.formatChecker.controller.FooterController;
import com.formatChecker.controller.ParagraphController;
import com.formatChecker.controller.SectionController;
import com.formatChecker.document.model.DocumentData;
import com.formatChecker.document.model.DocxDocument;
import org.docx4j.Docx4J;
import org.docx4j.jaxb.XPathBinderAssociationIsPartialException;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocxParser {
    private final static String TOC_XPATH = "//w:p[w:bookmarkStart[starts-with(@w:name,'_Toc')]]";

    String docxPath;
    Config config;
    Boolean shouldFix;
    DocumentData documentData;
    Map<Integer, String> configStyles;

    WordprocessingMLPackage wordMLPackage;
    List<String> headings;
    List<SectPr> sectionsProperties;
    DocxDocument docxDocument;
    Difference difference;

    public DocxParser(String configPath, String docxPath) throws IOException, Docx4JException, JAXBException {
        this.docxPath = docxPath;

        this.wordMLPackage = Docx4J.load(new FileInputStream(docxPath));
        this.headings = getHeadingsByTOC(wordMLPackage);
        this.documentData = getDocumentData(wordMLPackage);
        this.sectionsProperties = getSectionsProperties();

        this.config = new ConfigParser(configPath).parseConfig();
        this.docxDocument = new DocxDocument(new ArrayList<>(), new ArrayList<>());
        this.difference = new Difference(new ArrayList<>(), new ArrayList<>());
        this.shouldFix = config.getGenerateNewDocument();
        this.configStyles = getConfigStyles();
    }

    public Difference parseDocument() throws Docx4JException, ParserConfigurationException, IOException, SAXException {

        docxDocument.setPages(documentData.getDocumentInfo().getPages());
        difference.setPages(new DocumentDiffer(docxDocument.getPages(), config.getPages()).comparePages());

        for (SectPr sectPr : sectionsProperties) {
            new SectionController(sectPr, docxDocument, difference, config, shouldFix).parseSection();
        }

        new FooterController(documentData.getHeadersAndFooters(), config.getFooter(), docxDocument, difference)
                .parseFooter();

        parseParagraphs();

//        String test = new DifferResultCollector(difference).getDifferenceAsString();

        if (shouldFix)
            wordMLPackage.save(new File(new File(docxPath).getParent() + "/fixed.docx"));

        return difference;
    }

    DocumentData getDocumentData (WordprocessingMLPackage wordMLPackage) {
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        Body body = documentPart.getJaxbElement().getBody();
        Styles styles = documentPart.getStyleDefinitionsPart().getJaxbElement();
        List<SectionWrapper> sections = wordMLPackage.getDocumentModel().getSections();

        return new DocumentData(
                wordMLPackage.getDocPropsExtendedPart().getJaxbElement(),
                styles.getDocDefaults(),
                documentPart.getThemePart(),
                sections,
                styles,
                body.getContent(),
                sections.get(0).getHeaderFooterPolicy()
        );
    }

    void parseParagraphs() throws Docx4JException {
        int count = 0;
        for (Object p : documentData.getParagraphs()) {
            if (p instanceof P) {
                P par = (P) p;
                if (!par.toString().equals("")) {
                    ++count;
                    new ParagraphController(count, par, difference, docxDocument, documentData, config, configStyles, headings)
                            .parseParagraph();
                }
            }
        }
    }

    Map<Integer, String> getConfigStyles() {
        if (config.getFindHeadingsByTOC())
            return null;
        else {
            Map<Integer, String> stylesByIndexes = new HashMap<>();

            for (Map.Entry<String, Style> entry : config.getStyles().entrySet()) {
                List<Integer> paragraphIds = entry.getValue().getParagraphIndexes();

                for (Integer id : paragraphIds) {
                    stylesByIndexes.put(id, entry.getKey());
                }
            }

            return stylesByIndexes;
        }
    }

    List<String> getHeadingsByTOC(WordprocessingMLPackage wordMLPackage) throws JAXBException, XPathBinderAssociationIsPartialException {
        List<String> headings = new ArrayList<>();
        List<Object> TOCObjects = wordMLPackage.getMainDocumentPart().getJAXBNodesViaXPath(TOC_XPATH, false);

        for (Object o : TOCObjects) {
            headings.add(o.toString());
        }

        return headings;
    }

    List<SectPr> getSectionsProperties() {
        List<SectionWrapper> sections = documentData.getSections();

        List<SectPr> sectionsProperties = new ArrayList<>();

        for (SectionWrapper sw : sections) {
            sectionsProperties.add(sw.getSectPr());
        }

        return sectionsProperties;
    }
}
