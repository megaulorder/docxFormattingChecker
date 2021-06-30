package com.formatChecker.parserTests.drawing;

import com.formatChecker.config.model.Config;
import com.formatChecker.config.model.participants.Run;
import com.formatChecker.config.parser.ConfigParser;
import com.formatChecker.controller.DocumentController;
import com.formatChecker.document.model.DocxDocument;
import com.formatChecker.document.model.participants.Drawing;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static com.formatChecker.parserTests.constants.PathConstants.DRAWING_CONFIG_PATH;
import static com.formatChecker.parserTests.constants.PathConstants.DRAWING_DOCUMENT_PATH;

public class DrawingTest {
    @DisplayName("Checks the result of parsing a drawing with description")
    @Test
    public void testParseDrawing()
            throws Exception {
        DocxDocument docxDocument = new DocumentController(
                DRAWING_CONFIG_PATH,
                DRAWING_DOCUMENT_PATH)
                .getDocxDocument();

        Config config = new ConfigParser(DRAWING_CONFIG_PATH).getConfig();

        for (Drawing<Double, Boolean> drawing : docxDocument.getDrawings().getDrawings()) {
            Assert.assertEquals(
                    config.getDrawing().getDrawingPosition().getAlignment(),
                    drawing.getDrawing().getAlignment());

            Assert.assertEquals(
                    config.getDrawing().getDescription().getParagraph().getAlignment(),
                    drawing.getDescription().getAlignment());

            Assert.assertTrue(
                    drawing.getDescription().getText().contains(
                            config.getDrawing().getTextStartsWith()));

            for (Run run : drawing.getDescription().getRuns()) {
                Assert.assertEquals(
                        config.getDrawing().getDescription().getRun().getFontSize(),
                        run.getFontSize());
            }
        }
    }
}
