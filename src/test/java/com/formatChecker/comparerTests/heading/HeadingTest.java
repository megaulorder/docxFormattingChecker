package com.formatChecker.comparerTests.heading;

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
import static com.formatChecker.parserTests.constants.PathConstants.HEADING_CONFIG_PATH;
import static com.formatChecker.parserTests.constants.PathConstants.HEADING_DOCUMENT_PATH;

public class HeadingTest {
    @DisplayName("Checks the result of comparing headings")
    @Test
    public void testCompareHeadings()
            throws JAXBException, IOException, ParserConfigurationException, Docx4JException, SAXException {

        String difference = new DifferResultCollector(
                new DocumentController(HEADING_CONFIG_PATH, HEADING_DOCUMENT_PATH).getDifference())
                .collectDifferenceAsString();

        Assert.assertEquals(OK_MESSAGE, difference);
    }
}
