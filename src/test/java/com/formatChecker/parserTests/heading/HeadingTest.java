package com.formatChecker.parserTests.heading;

import com.formatChecker.config.model.Config;
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

import static com.formatChecker.parserTests.constants.PathConstants.HEADING_CONFIG_PATH;
import static com.formatChecker.parserTests.constants.PathConstants.HEADING_DOCUMENT_PATH;

public class HeadingTest {
    @DisplayName("Checks the result of parsing headings")
    @Test
    public void testParseHeadings()
            throws JAXBException, IOException, ParserConfigurationException, Docx4JException, SAXException {
        DocxDocument docxDocument = new DocumentController(
                HEADING_CONFIG_PATH,
                HEADING_DOCUMENT_PATH)
                .getDocxDocument();

        Config config = new ConfigParser(HEADING_CONFIG_PATH).getConfig();

        for (int i = 0; i < docxDocument.getParagraphs().size(); ++i) {
            Assert.assertEquals(
                    java.util.Optional.ofNullable(config.getRequiredHeadings().get(i).getLevel()),
                    java.util.Optional.ofNullable(docxDocument.getParagraphs().get(i).getHeadingLevel()));

            Assert.assertEquals(
                    java.util.Optional.ofNullable(config.getRequiredHeadings().get(i).getText()),
                    java.util.Optional.ofNullable(docxDocument.getParagraphs().get(i).getText()));
        }
    }
}
