package com.formatChecker.comparerTests.paragraph;

import com.formatChecker.comparer.collector.DifferResultCollector;
import com.formatChecker.controller.DocumentController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static com.formatChecker.fixerTests.constants.MessageConstants.OK_MESSAGE;
import static com.formatChecker.parserTests.constants.PathConstants.*;

public class ParagraphTest {
    @DisplayName("Checks the result of comparing paragraphs by indexes")
    @Test
    public void testCompareParagraphsByIndexes()
            throws Exception {

        String difference = new DifferResultCollector(
                new DocumentController(
                        PARAGRAPH_BY_INDEX_CONFIG_PATH,
                        PARAGRAPH_BY_INDEX_DOCUMENT_PATH)
                        .getDifference())
                .collectDifferenceAsString();

        Assert.assertEquals(OK_MESSAGE, difference);
    }

    @DisplayName("Checks the result of comparing paragraphs by headings")
    @Test
    public void testCompareParagraphsByHeadings()
            throws Exception {

        String difference = new DifferResultCollector(
                new DocumentController(
                        PARAGRAPH_BY_HEADING_CONFIG_PATH,
                        PARAGRAPH_BY_HEADING_DOCUMENT_PATH)
                        .getDifference())
                .collectDifferenceAsString();

        Assert.assertEquals(OK_MESSAGE, difference);
    }
}
