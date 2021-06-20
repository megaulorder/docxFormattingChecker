package com.formatChecker.parserTests.footer;

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

import static com.formatChecker.parserTests.constants.PathConstants.*;

public class FooterTest {
    @DisplayName("Checks the result of parsing a footer with page value aligned by center")
    @Test
    public void testParsePageFooter()
            throws JAXBException, IOException, ParserConfigurationException, Docx4JException, SAXException {
        DocxDocument docxDocument = new DocumentController(
                PAGE_FOOTER_CONFIG_PATH,
                PAGE_FOOTER_DOCUMENT_PATH)
                .getDocxDocument();

        Config config = new ConfigParser(PAGE_FOOTER_CONFIG_PATH).getConfig();

        Assert.assertEquals(config.getFooter().getType(), docxDocument.getFooter().getType());
        Assert.assertEquals(config.getFooter().getAlignment(), docxDocument.getFooter().getAlignment());
    }

    @DisplayName("Checks the result of parsing a footer with text value aligned by left")
    @Test
    public void testParseTextFooter()
            throws JAXBException, IOException, ParserConfigurationException, Docx4JException, SAXException {
        DocxDocument docxDocument = new DocumentController(
                TEXT_FOOTER_CONFIG_PATH,
                TEXT_FOOTER_DOCUMENT_PATH)
                .getDocxDocument();

        Config config = new ConfigParser(TEXT_FOOTER_CONFIG_PATH).getConfig();

        Assert.assertEquals(config.getFooter().getType(), docxDocument.getFooter().getType());
        Assert.assertEquals(config.getFooter().getAlignment(), docxDocument.getFooter().getAlignment());
    }
}
