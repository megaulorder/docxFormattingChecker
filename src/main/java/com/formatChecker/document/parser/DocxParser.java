package com.formatChecker.document.parser;

import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.document.model.DocxDocument;
import com.formatChecker.document.parser.paragraph.ParagraphDirectParser;
import com.formatChecker.document.parser.section.SectionParser;
import org.docx4j.Docx4J;
import org.docx4j.docProps.extended.Properties;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.ThemePart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class DocxParser {
    public DocxDocument getDocumentProperties(String filePath) throws Docx4JException, FileNotFoundException {
        FileInputStream test = new FileInputStream(filePath);

        WordprocessingMLPackage wordMLPackage = Docx4J.load(test);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

        Document wmlDocumentEl = documentPart.getJaxbElement();
        Body body = wmlDocumentEl.getBody();

        Properties documentInfo = wordMLPackage.getDocPropsExtendedPart().getJaxbElement();

        Styles styles = documentPart.getStyleDefinitionsPart().getJaxbElement();
        DocDefaults docDefaults = styles.getDocDefaults();
        ThemePart themePart = documentPart.getThemePart();

        SectPr sectionProperties = body.getSectPr();

        List<Object> paragraphs = body.getContent();

        DocxDocument document = new DocxDocument(new ArrayList<>());

        document.setPages(documentInfo.getPages());
        document.setSection(new SectionParser().parseSection(sectionProperties));

        for (Object p : paragraphs) {
            P par = (P) p;
            Paragraph paragraph = new ParagraphDirectParser()
                    .parseParagraph(par, styles, docDefaults, themePart);
            document.addParagraph(paragraph);
        }

        return document;
    }
}
