package com.formatChecker.parserTests.paragraphAndRun;

import com.formatChecker.config.model.Config;
import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.parser.ConfigParser;
import com.formatChecker.controller.DocumentController;
import com.formatChecker.document.model.DocxDocument;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static com.formatChecker.parserTests.constants.PathConstants.*;

public class ParagraphAndRunTest {
    @DisplayName("Checks the result of parsing paragraphs and runs by indexes")
    @Test
    public void testParseParagraphsAndRunsByIndexes()
            throws JAXBException, IOException, ParserConfigurationException, Docx4JException, SAXException {
        DocxDocument docxDocument = new DocumentController(
                INDEX_PARAGRAPH_CONFIG_PATH,
                INDEX_PARAGRAPH_DOCUMENT_PATH)
                .getDocxDocument();

        Config config = new ConfigParser(INDEX_PARAGRAPH_CONFIG_PATH).getConfig();

        Paragraph<Double, Boolean> paragraph1 = docxDocument.getParagraphs().get(0);
        Paragraph<Double, Boolean> paragraph2 = docxDocument.getParagraphs().get(1);

        Assert.assertEquals(
                paragraph1.getAlignment(),
                config.getStyles().get("style1").getParagraph().getAlignment());

        Assert.assertEquals(
                paragraph2.getFirstLineIndent(),
                config.getStyles().get("style2").getParagraph().getFirstLineIndent());

        for (int i = 0; i < paragraph1.getRuns().size(); ++i) {
            Assert.assertEquals(
                    paragraph1.getRuns().get(i).getFontFamily(),
                    config.getStyles().get("style1").getRun().getFontFamily());

            Assert.assertEquals(
                    paragraph1.getRuns().get(i).getBold(),
                    config.getStyles().get("style1").getRun().getBold());
        }
        for (int i = 0; i < paragraph2.getRuns().size(); ++i) {
            Assert.assertEquals(
                    paragraph2.getRuns().get(i).getFontSize(),
                    config.getStyles().get("style2").getRun().getFontSize());
        }
    }

    @DisplayName("Checks the result of parsing paragraphs and runs by headings")
    @Test
    public void testParseParagraphsAndRunsByHeadings()
            throws JAXBException, IOException, ParserConfigurationException, Docx4JException, SAXException {
        DocxDocument docxDocument = new DocumentController(
                HEADING_PARAGRAPH_CONFIG_PATH,
                HEADING_PARAGRAPH_DOCUMENT_PATH)
                .getDocxDocument();

        Config config = new ConfigParser(HEADING_PARAGRAPH_CONFIG_PATH).getConfig();

        Paragraph<Double, Boolean> headingParagraph = new Paragraph<>();
        Paragraph<Double, Boolean> bodyParagraph = new Paragraph<>();

        for (Paragraph<Double, Boolean> paragraph : docxDocument.getParagraphs()) {
            if (paragraph.getHeadingLevel() > 0)
                headingParagraph = paragraph;
            else
                bodyParagraph = paragraph;
        }

        Assert.assertEquals(
                headingParagraph.getAlignment(),
                config.getStyles().get("heading1").getParagraph().getAlignment());

        Assert.assertEquals(
                bodyParagraph.getFirstLineIndent(),
                config.getStyles().get("body").getParagraph().getFirstLineIndent());

        for (int i = 0; i < headingParagraph.getRuns().size(); ++i) {
            Assert.assertEquals(
                    headingParagraph.getRuns().get(i).getFontFamily(),
                    config.getStyles().get("heading1").getRun().getFontFamily());

            Assert.assertEquals(
                    headingParagraph.getRuns().get(i).getBold(),
                    config.getStyles().get("heading1").getRun().getBold());
        }
        for (int i = 0; i < bodyParagraph.getRuns().size(); ++i) {
            Assert.assertEquals(
                    bodyParagraph.getRuns().get(i).getFontSize(),
                    config.getStyles().get("body").getRun().getFontSize());
        }
    }
}
