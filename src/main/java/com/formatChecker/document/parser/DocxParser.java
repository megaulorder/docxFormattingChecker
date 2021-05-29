package com.formatChecker.document.parser;

import com.formatChecker.compare.collector.DifferResultCollector;
import com.formatChecker.compare.differ.DocumentDiffer;
import com.formatChecker.compare.differ.SectionDiffer;
import com.formatChecker.compare.model.Difference;
import com.formatChecker.config.model.Config;
import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.model.participants.Style;
import com.formatChecker.config.parser.ConfigParser;
import com.formatChecker.controller.ParagraphController;
import com.formatChecker.document.model.DocumentData;
import com.formatChecker.document.model.DocxDocument;
import com.formatChecker.document.parser.paragraph.ParagraphDirectParser;
import com.formatChecker.document.parser.section.SectionParser;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.ThemePart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DocxParser {
    private static final String headerStyleName = "header";
    private static final String bodyStyleName = "body";

    public DocxDocument parseDocument(String docxPath, String configPath) throws Docx4JException, IOException {
        FileInputStream file = new FileInputStream(docxPath);

        WordprocessingMLPackage wordMLPackage = Docx4J.load(file);
        DocumentData documentData = getDocumentData(wordMLPackage);

        final Styles styles = documentData.getStyles();
        final DocDefaults docDefaults = documentData.getDocDefaults();
        final ThemePart themePart = documentData.getThemePart();

        Config config = new ConfigParser(configPath).parseConfig();
        DocxDocument document = new DocxDocument(new ArrayList<>());
        Difference difference = new Difference(new ArrayList<>());

        document.setPages(documentData.getDocumentInfo().getPages());
        difference.setPages(new DocumentDiffer().comparePages(document.getPages(), config.getPages()));

        document.setSection(new SectionParser(documentData.getSectionProperties()).parseSection());
        difference.setSection(new SectionDiffer(document.getSection(), config.getSection()).getSectionDifference());

        Boolean shouldFix = config.getGenerateNewDocument();

        int count = 0;
        for (Object p : documentData.getParagraphs()) {
            if (p instanceof P) {
                ++count;

                P par = (P) p;
                Paragraph paragraph = new ParagraphDirectParser(docDefaults, styles, themePart, par).parseParagraph();
                document.addParagraph(paragraph);



                if (!config.getFinByToc()) {
                    for (Map.Entry<String, Style> entry : config.getStyles().entrySet()) {
                        List<Integer> paragraphIds = entry.getValue().getParagraphIndexes();

                        for (Integer id : paragraphIds) {
                            if (id == count) {
                                Style style = config.getStyles().get(entry.getKey());

                                ParagraphController paragraphController = new ParagraphController(par, paragraph,
                                        style.getParagraph(), style.getRun(), difference, shouldFix);

                                paragraphController.compareParagraph();
                            }
                        }
                    }
                }
                else {
                    if (paragraph.getIsHeader()) {
                        Style headerStyle = config.getStyles().get(headerStyleName);

                        ParagraphController paragraphController = new ParagraphController(par, paragraph,
                                headerStyle.getParagraph(), headerStyle.getRun(), difference, shouldFix);

                        paragraphController.compareParagraph();
                    } else {
                        Style bodyStyle = config.getStyles().get(bodyStyleName);

                        ParagraphController paragraphController = new ParagraphController(par, paragraph,
                                bodyStyle.getParagraph(), bodyStyle.getRun(), difference, shouldFix);

                        paragraphController.compareParagraph();
                    }
                }
            }
        }

        String test = new DifferResultCollector(difference).getDifferenceAsString();

        return document;
    }

    DocumentData getDocumentData (WordprocessingMLPackage wordMLPackage) {
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
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
}
