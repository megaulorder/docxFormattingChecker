package com.formatChecker.comparerTests.drawing;

import com.formatChecker.comparer.collector.DifferResultCollector;
import com.formatChecker.config.model.Config;
import com.formatChecker.config.model.participants.Run;
import com.formatChecker.config.parser.ConfigParser;
import com.formatChecker.controller.DocumentController;
import com.formatChecker.document.model.DocxDocument;
import com.formatChecker.document.model.participants.Drawing;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static com.formatChecker.fixerTests.constants.MessageConstants.OK_MESSAGE;
import static com.formatChecker.parserTests.constants.PathConstants.DRAWING_CONFIG_PATH;
import static com.formatChecker.parserTests.constants.PathConstants.DRAWING_DOCUMENT_PATH;

public class DrawingTest {
    @DisplayName("Checks the result of comparing a drawing with description")
    @Test
    public void testCompareDrawing()
            throws JAXBException, IOException, ParserConfigurationException, Docx4JException, SAXException {

        String difference = new DifferResultCollector(
                new DocumentController(DRAWING_CONFIG_PATH, DRAWING_DOCUMENT_PATH).getDifference())
                .collectDifferenceAsString();

        Assert.assertEquals(OK_MESSAGE, difference);
    }
}
