package com.formatChecker.comparerTests.section;

import com.formatChecker.comparer.collector.DifferResultCollector;
import com.formatChecker.controller.DocumentController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static com.formatChecker.fixerTests.constants.MessageConstants.OK_MESSAGE;
import static com.formatChecker.parserTests.constants.PathConstants.SECTION_CONFIG_PATH;
import static com.formatChecker.parserTests.constants.PathConstants.SECTION_DOCUMENT_PATH;

public class SectionTest {
    @DisplayName("Checks the result of comparing sections")
    @Test
    public void testCompareSections()
            throws Exception {

        String difference = new DifferResultCollector(
                new DocumentController(
                        SECTION_CONFIG_PATH,
                        SECTION_DOCUMENT_PATH)
                        .getDifference())
                .collectDifferenceAsString();

        Assert.assertEquals(OK_MESSAGE, difference);
    }
}
