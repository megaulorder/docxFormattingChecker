package com.formatChecker.comparerTests.heading;

import com.formatChecker.comparer.collector.DifferResultCollector;
import com.formatChecker.controller.DocumentController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static com.formatChecker.fixerTests.constants.MessageConstants.OK_MESSAGE;
import static com.formatChecker.parserTests.constants.PathConstants.HEADING_CONFIG_PATH;
import static com.formatChecker.parserTests.constants.PathConstants.HEADING_DOCUMENT_PATH;

public class HeadingTest {
    @DisplayName("Checks the result of comparing headings")
    @Test
    public void testCompareHeadings()
            throws Exception {

        String difference = new DifferResultCollector(
                new DocumentController(HEADING_CONFIG_PATH, HEADING_DOCUMENT_PATH).getDifference())
                .collectDifferenceAsString();

        Assert.assertEquals(OK_MESSAGE, difference);
    }
}
