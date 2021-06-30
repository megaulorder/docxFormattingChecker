package com.formatChecker.comparerTests.drawing;

import com.formatChecker.comparer.collector.DifferResultCollector;
import com.formatChecker.controller.DocumentController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static com.formatChecker.fixerTests.constants.MessageConstants.OK_MESSAGE;
import static com.formatChecker.parserTests.constants.PathConstants.DRAWING_CONFIG_PATH;
import static com.formatChecker.parserTests.constants.PathConstants.DRAWING_DOCUMENT_PATH;

public class DrawingTest {
    @DisplayName("Checks the result of comparing a drawing with description")
    @Test
    public void testCompareDrawing()
            throws Exception {

        String difference = new DifferResultCollector(
                new DocumentController(DRAWING_CONFIG_PATH, DRAWING_DOCUMENT_PATH).getDifference())
                .collectDifferenceAsString();

        Assert.assertEquals(OK_MESSAGE, difference);
    }
}
