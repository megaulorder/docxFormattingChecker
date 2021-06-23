package com.formatChecker.fixerTests.paragraph;

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
import static com.formatChecker.fixerTests.constants.PathConstants.*;

public class ParagraphTest {
    @DisplayName("Checks the result of fixing paragraphs by index")
    @Test
    public void testFixParagraphsByIndex()
            throws JAXBException, IOException, ParserConfigurationException, Docx4JException, SAXException {
        new DocumentController(PARAGRAPH_BY_INDEX_CONFIG_PATH, PARAGRAPH_BY_INDEX_DOCUMENT_PATH);

        String difference = new DifferResultCollector(
                new DocumentController(PARAGRAPH_BY_INDEX_CONFIG_PATH, PARAGRAPH_BY_INDEX_FIXED_DOCUMENT_PATH)
                        .getDifference())
                .collectDifferenceAsString();

        Assert.assertEquals(OK_MESSAGE, difference);
    }

    @DisplayName("Checks the result of fixing paragraphs by heading")
    @Test
    public void testFixParagraphsByHeading()
            throws JAXBException, IOException, ParserConfigurationException, Docx4JException, SAXException {
        new DocumentController(PARAGRAPH_BY_HEADING_CONFIG_PATH, PARAGRAPH_BY_HEADING_DOCUMENT_PATH);

        String difference = new DifferResultCollector(
                new DocumentController(PARAGRAPH_BY_HEADING_CONFIG_PATH, PARAGRAPH_BY_HEADING_FIXED_DOCUMENT_PATH)
                        .getDifference())
                .collectDifferenceAsString();

        Assert.assertEquals(OK_MESSAGE, difference);
    }
}