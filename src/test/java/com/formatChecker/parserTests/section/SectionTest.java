package com.formatChecker.parserTests.section;

import com.formatChecker.config.model.Config;
import com.formatChecker.config.model.participants.Section;
import com.formatChecker.config.parser.ConfigParser;
import com.formatChecker.controller.DocumentController;
import com.formatChecker.document.model.DocxDocument;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static com.formatChecker.parserTests.constants.PathConstants.SECTION_CONFIG_PATH;
import static com.formatChecker.parserTests.constants.PathConstants.SECTION_DOCUMENT_PATH;

public class SectionTest {
    @DisplayName("Checks the result of parsing sections")
    @Test
    public void testParseSections()
            throws Exception {
        DocxDocument docxDocument = new DocumentController(
                SECTION_CONFIG_PATH,
                SECTION_DOCUMENT_PATH)
                .getDocxDocument();

        Config config = new ConfigParser(SECTION_CONFIG_PATH).getConfig();

        for (Section<Double> section : docxDocument.getSections()) {
            Assert.assertEquals(
                    config.getSection().getPageWidth(),
                    section.getPageWidth());

            Assert.assertEquals(
                    config.getSection().getPageHeight(),
                    section.getPageHeight());

            Assert.assertEquals(
                    config.getSection().getOrientation(),
                    section.getOrientation());


            for (int j = 0; j < section.getMargins().size(); ++j) {
                Assert.assertEquals(
                        config.getSection().getMargins().get(j),
                        section.getMargins().get(j));
            }
        }
    }
}
