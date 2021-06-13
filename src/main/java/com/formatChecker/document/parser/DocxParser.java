package com.formatChecker.document.parser;

import com.formatChecker.comparer.differ.DocumentDiffer;
import com.formatChecker.comparer.differ.SectionDiffer;
import com.formatChecker.comparer.model.Difference;
import com.formatChecker.config.model.Config;
import com.formatChecker.config.model.participants.Section;
import com.formatChecker.config.model.participants.Style;
import com.formatChecker.config.parser.ConfigParser;
import com.formatChecker.controller.ParagraphController;
import com.formatChecker.document.model.DocumentData;
import com.formatChecker.document.model.DocxDocument;
import com.formatChecker.document.parser.section.SectionParser;
import com.formatChecker.fixer.SectionFixer;
import org.docx4j.Docx4J;
import org.docx4j.jaxb.XPathBinderAssociationIsPartialException;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBException;
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
    List<String> headers;
    Section<Double> section;
    DocxDocument docxDocument;
    Difference difference;

    public DocxParser(String configPath, String docxPath) throws IOException, Docx4JException, JAXBException {
        this.docxPath = docxPath;

        this.wordMLPackage = Docx4J.load(new FileInputStream(docxPath));
        this.headers = getHeadersByTOC(wordMLPackage);
        this.documentData = getDocumentData(wordMLPackage);
        this.section = new SectionParser(documentData.getSectionProperties()).parseSection();

        this.config = new ConfigParser(configPath).parseConfig();
        this.docxDocument = new DocxDocument(new ArrayList<>());
        this.difference = new Difference(new ArrayList<>());
        this.shouldFix = config.getGenerateNewDocument();
        this.configStyles = getConfigStyles();
    }

    public Difference parseDocument() throws Docx4JException {

        docxDocument.setPages(documentData.getDocumentInfo().getPages());
        docxDocument.setSection(section);

        difference.setPages(new DocumentDiffer(docxDocument.getPages(), config.getPages()).comparePages());
        difference.setSection(new SectionDiffer(docxDocument.getSection(), config.getSection()).getSectionDifference());

        parseParagraphs();

//        String test = new DifferResultCollector(difference).getDifferenceAsString();

        if (shouldFix) {
            new SectionFixer(documentData.getSectionProperties(), section, config.getSection(), difference.getSection()).fixSection();

            wordMLPackage.save(new File(new File(docxPath).getParent() + "/test_fixed.docx"));
        }

        return difference;
    }

    DocumentData getDocumentData (WordprocessingMLPackage wordMLPackage) {
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
//        System.out.println(documentPart.getXML());
        Body body = documentPart.getJaxbElement().getBody();
        Styles styles = documentPart.getStyleDefinitionsPart().getJaxbElement();

        return new DocumentData(
                wordMLPackage.getDocPropsExtendedPart().getJaxbElement(),
                styles.getDocDefaults(),
                documentPart.getThemePart(),
                body.getSectPr(),
                styles,
                body.getContent()
        );
    }

    void parseParagraphs() throws Docx4JException {
        int count = 0;
        for (Object p : documentData.getParagraphs()) {
            if (p instanceof P) {
                P par = (P) p;
                if (!par.toString().equals("")) {
                    ++count;
                    new ParagraphController(count, par, difference, docxDocument, documentData, config, configStyles, headers)
                            .parseParagraph();
//                    System.out.println(docxDocument.getParagraphs().get(count-1).getText());
                }
            }
        }
    }

    Map<Integer, String> getConfigStyles() {
        if (config.getFindHeadersByToc())
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

    List<String> getHeadersByTOC(WordprocessingMLPackage wordMLPackage) throws JAXBException, XPathBinderAssociationIsPartialException {
        List<String> headers = new ArrayList<>();
        List<Object> TOCObjects = wordMLPackage.getMainDocumentPart().getJAXBNodesViaXPath(
                TOC_XPATH, false);

        for (Object o : TOCObjects) {
            headers.add(o.toString());
        }

        return headers;
    }
}
