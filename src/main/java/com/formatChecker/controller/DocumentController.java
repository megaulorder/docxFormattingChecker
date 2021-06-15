package com.formatChecker.controller;

import com.formatChecker.comparer.differ.DocumentDiffer;
import com.formatChecker.comparer.model.Difference;
import com.formatChecker.config.model.Config;
import com.formatChecker.config.parser.ConfigParser;
import com.formatChecker.document.model.DocumentData;
import com.formatChecker.document.model.DocxDocument;
import com.formatChecker.document.model.Heading;
import com.formatChecker.document.parser.DocxParser;
import org.docx4j.Docx4J;
import org.docx4j.jaxb.XPathBinderAssociationIsPartialException;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.P;
import org.docx4j.wml.SectPr;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DocumentController {
    String docxPath;

    WordprocessingMLPackage wordMLPackage;
    DocxDocument docxDocument;
    Difference difference;

    ConfigParser configParser;
    Config config;
    Map<Integer, String> configStyles;
    Boolean shouldFix;

    DocxParser docxParser;
    DocumentData documentData;
    List<SectPr> sectionsProperties;

    List<Heading> headings;

    public DocumentController(String configPath, String docxPath)
            throws IOException, Docx4JException, ParserConfigurationException, SAXException, JAXBException {
        this.docxPath = docxPath;

        this.configParser = new ConfigParser(configPath);
        this.config = configParser.getConfig();
        this.configStyles = configParser.getStyles();
        this.shouldFix = configParser.getShouldFix();

        this.wordMLPackage = Docx4J.load(new FileInputStream(docxPath));

        this.docxParser = new DocxParser(wordMLPackage);
        this.documentData = docxParser.getDocumentData();
        this.docxDocument = docxParser.getDocument();
        this.sectionsProperties = docxParser.getSectionsProperties();

        this.difference = getDocumentDifference();
    }

    Difference getDocumentDifference() throws Docx4JException, ParserConfigurationException, IOException, SAXException, JAXBException {
        Difference difference = new Difference();

        difference.setPages(new DocumentDiffer(docxDocument.getPages(), config.getPages()).comparePages());

        runSectionController(difference);
        runFooterController(difference);
        runHeadingsController(difference);
        runParagraphController(difference);

        if (shouldFix)
            saveNewDocument();

        return difference;
    }

    void runSectionController(Difference difference) {
        for (SectPr sectPr : sectionsProperties) {
            new SectionController(sectPr, docxDocument, difference, config, shouldFix).parseSection();
        }
    }

    void runFooterController(Difference difference) throws ParserConfigurationException, IOException, SAXException {
        new FooterController(documentData.getHeadersAndFooters(), config.getFooter(), docxDocument, difference)
                .parseFooter();
    }

    void runHeadingsController(Difference difference) throws JAXBException, XPathBinderAssociationIsPartialException {
        if (configParser.getFindHeadingsByTOC()) {
            HeadingController headingController = new HeadingController(wordMLPackage, config.getRequiredHeadings(), difference, docxDocument);
            headingController.compareHeadings();

            headings = headingController.getHeadings();
        }
        else
            headings = null;
    }

    void runParagraphController(Difference difference) throws Docx4JException {
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

    void saveNewDocument() throws Docx4JException {
        wordMLPackage.save(new File(new File(docxPath).getParent() + "/fixed.docx"));
    }

    public Difference getDifference() {
        return difference;
    }
}
