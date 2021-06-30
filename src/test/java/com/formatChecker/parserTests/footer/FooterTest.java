package com.formatChecker.parserTests.footer;

import com.formatChecker.config.model.Config;
import com.formatChecker.config.parser.ConfigParser;
import com.formatChecker.controller.DocumentController;
import com.formatChecker.document.model.DocxDocument;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static com.formatChecker.parserTests.constants.PathConstants.*;

public class FooterTest {
    @DisplayName("Checks the result of parsing a footer with page value aligned by center")
    @Test
    public void testParsePageFooter()
            throws Exception {
        DocxDocument docxDocument = new DocumentController(
                FOOTER_PAGE_CONFIG_PATH,
                FOOTER_PAGE_DOCUMENT_PATH)
                .getDocxDocument();

        Config config = new ConfigParser(FOOTER_PAGE_CONFIG_PATH).getConfig();

        Assert.assertEquals(config.getFooter().getType(), docxDocument.getFooter().getType());
        Assert.assertEquals(config.getFooter().getAlignment(), docxDocument.getFooter().getAlignment());
    }

    @DisplayName("Checks the result of parsing a footer with text value aligned by left")
    @Test
    public void testParseTextFooter()
            throws Exception {
        DocxDocument docxDocument = new DocumentController(
                FOOTER_TEXT_CONFIG_PATH,
                FOOTER_TEXT_DOCUMENT_PATH)
                .getDocxDocument();

        Config config = new ConfigParser(FOOTER_TEXT_CONFIG_PATH).getConfig();

        Assert.assertEquals(config.getFooter().getType(), docxDocument.getFooter().getType());
        Assert.assertEquals(config.getFooter().getAlignment(), docxDocument.getFooter().getAlignment());
    }
}
