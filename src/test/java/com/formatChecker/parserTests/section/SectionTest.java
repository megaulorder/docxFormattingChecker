package com.formatChecker.parserTests.section;

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

public class SectionTest {
    @DisplayName("Checks the result of parsing sections")
    @Test
    public void testParseSections()
            throws JAXBException, IOException, ParserConfigurationException, Docx4JException, SAXException {
        DocxDocument docxDocument = new DocumentController(
                SECTION_CONFIG_PATH,
                SECTION_DOCUMENT_PATH)
                .getDocxDocument();

        Config config = new ConfigParser(SECTION_CONFIG_PATH).getConfig();

        for (int i = 0; i < docxDocument.getSections().size(); ++i) {
            Assert.assertEquals(
                    docxDocument.getSections().get(i).getPageWidth(),
                    config.getSection().getPageWidth());

            Assert.assertEquals(
                    docxDocument.getSections().get(i).getPageHeight(),
                    config.getSection().getPageHeight());

            Assert.assertEquals(
                    docxDocument.getSections().get(i).getOrientation(),
                    config.getSection().getOrientation());


            for (int j = 0; j < docxDocument.getSections().get(i).getMargins().size(); ++j) {
                Assert.assertEquals(
                        docxDocument.getSections().get(i).getMargins().get(j),
                        config.getSection().getMargins().get(j));
            }
        }
    }
}
