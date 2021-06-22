package com.formatChecker.comparerTests.section;

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

public class SectionTest {
    @DisplayName("Checks the result of comparing sections")
    @Test
    public void testCompareSections()
            throws JAXBException, IOException, ParserConfigurationException, Docx4JException, SAXException {

        String difference = new DifferResultCollector(
                new DocumentController(
                        SECTION_CONFIG_PATH,
                        SECTION_DOCUMENT_PATH)
                        .getDifference())
                .collectDifferenceAsString();

        Assert.assertEquals(OK_MESSAGE, difference);
    }
}
