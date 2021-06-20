package com.formatChecker.controller;

import com.formatChecker.comparer.differ.DrawingDiffer;
import com.formatChecker.comparer.model.Difference;
import com.formatChecker.config.model.participants.ConfigDrawing;
import com.formatChecker.document.model.DocxDocument;
import com.formatChecker.document.model.data.DocumentData;
import com.formatChecker.document.model.participants.Drawing;
import com.formatChecker.document.model.participants.raw.DrawingRaw;
import com.formatChecker.document.parser.paragraph.ParagraphDirectParser;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.parts.ThemePart;
import org.docx4j.wml.DocDefaults;
import org.docx4j.wml.Styles;

public class DrawingController {
    DrawingRaw drawingRaw;
    Drawing<Double, Boolean> drawing;
    ConfigDrawing expectedDrawing;
    Difference difference;
    DocxDocument docxDocument;

    DocDefaults docDefaults;
    Styles styles;
    ThemePart themePart;

    public DrawingController(DrawingRaw drawingRaw,
                             ConfigDrawing expectedDrawing,
                             Difference difference,
                             DocxDocument docxDocument,
                             DocumentData documentData) throws Docx4JException {
        this.docDefaults = documentData.getDocDefaults();
        this.styles = documentData.getStyles();
        this.themePart = documentData.getThemePart();

        this.drawingRaw = drawingRaw;
        this.drawing = parseDrawingFromRaw();
        this.expectedDrawing = expectedDrawing;
        this.difference = difference;
        this.docxDocument = docxDocument;
    }

    Drawing<Double, Boolean> parseDrawingFromRaw() throws Docx4JException {
        Drawing<Double, Boolean> drawing = new Drawing<>();

        drawing.setDrawing(new ParagraphDirectParser(
                docDefaults,
                styles,
                themePart,
                drawingRaw.getDrawing())
                .parseParagraph());

        if (drawingRaw.getDescription() != null) {
            drawing.setDescription(new ParagraphDirectParser(
                    docDefaults,
                    styles,
                    themePart,
                    drawingRaw.getDescription())
                    .parseParagraph());

            drawing.setText(drawing.getDescription().getText());
        }

        return drawing;
    }

    void parseDrawing() {
        docxDocument.addDrawing(drawing);

        difference.addDrawing(new DrawingDiffer(drawing, expectedDrawing).getDifferenceDrawing());
    }
}
