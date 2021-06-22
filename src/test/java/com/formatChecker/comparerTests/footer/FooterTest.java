package com.formatChecker.comparerTests.footer;

import com.formatChecker.comparer.collector.DifferResultCollector;
import com.formatChecker.controller.DocumentController;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static com.formatChecker.fixerTests.constants.MessageConstants.OK_MESSAGE;
import static com.formatChecker.parserTests.constants.PathConstants.*;

public class FooterTest {
    @DisplayName("Checks the result of comparing a footer with page content")
    @Test
    public void testComparePageFooter()
            throws JAXBException, IOException, ParserConfigurationException, Docx4JException, SAXException {

        String difference = new DifferResultCollector(
                new DocumentController(FOOTER_PAGE_CONFIG_PATH, FOOTER_PAGE_DOCUMENT_PATH).getDifference())
                .collectDifferenceAsString();

        Assert.assertEquals(OK_MESSAGE, difference);
    }

    @DisplayName("Checks the result of comparing a footer with text content")
    @Test
    public void testCompareTextFooter()
            throws JAXBException, IOException, ParserConfigurationException, Docx4JException, SAXException {

        String difference = new DifferResultCollector(
                new DocumentController(FOOTER_TEXT_CONFIG_PATH, FOOTER_TEXT_DOCUMENT_PATH).getDifference())
                .collectDifferenceAsString();

        Assert.assertEquals(OK_MESSAGE, difference);
    }
}
