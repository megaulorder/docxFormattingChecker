package com.formatChecker.comparerTests.footer;

import com.formatChecker.comparer.collector.DifferResultCollector;
import com.formatChecker.controller.DocumentController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static com.formatChecker.fixerTests.constants.MessageConstants.OK_MESSAGE;
import static com.formatChecker.parserTests.constants.PathConstants.*;

public class FooterTest {
    @DisplayName("Checks the result of comparing a footer with page content")
    @Test
    public void testComparePageFooter()
            throws Exception {

        String difference = new DifferResultCollector(
                new DocumentController(FOOTER_PAGE_CONFIG_PATH, FOOTER_PAGE_DOCUMENT_PATH).getDifference())
                .collectDifferenceAsString();

        Assert.assertEquals(OK_MESSAGE, difference);
    }

    @DisplayName("Checks the result of comparing a footer with text content")
    @Test
    public void testCompareTextFooter()
            throws Exception {

        String difference = new DifferResultCollector(
                new DocumentController(FOOTER_TEXT_CONFIG_PATH, FOOTER_TEXT_DOCUMENT_PATH).getDifference())
                .collectDifferenceAsString();

        Assert.assertEquals(OK_MESSAGE, difference);
    }
}
