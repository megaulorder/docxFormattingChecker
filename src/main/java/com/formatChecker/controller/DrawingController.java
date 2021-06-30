package com.formatChecker.controller;

import com.formatChecker.comparer.differ.DrawingDiffer;
import com.formatChecker.comparer.model.Difference;
import com.formatChecker.comparer.model.participants.DrawingsList;
import com.formatChecker.config.model.participants.ConfigDrawing;
import com.formatChecker.document.model.DocxDocument;
import com.formatChecker.document.model.data.DocumentData;
import com.formatChecker.document.model.participants.Drawing;
import com.formatChecker.document.model.participants.raw.DrawingRaw;
import com.formatChecker.document.model.participants.raw.DrawingsRawList;
import com.formatChecker.document.parser.paragraph.ParagraphDirectParser;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.parts.ThemePart;
import org.docx4j.wml.DocDefaults;
import org.docx4j.wml.Styles;


public class DrawingController {
    DrawingsRawList drawingsRawList;
    DrawingsList drawingsList;
    ConfigDrawing expectedDrawing;
    Difference difference;
    DocxDocument docxDocument;

    DocDefaults docDefaults;
    Styles styles;
    ThemePart themePart;

    public DrawingController(DrawingsRawList drawingsRawList,
                             ConfigDrawing expectedDrawing,
                             Difference difference,
                             DocxDocument docxDocument,
                             DocumentData documentData) throws Docx4JException {
        this.docDefaults = documentData.getDocDefaults();
        this.styles = documentData.getStyles();
        this.themePart = documentData.getThemePart();

        this.drawingsRawList = drawingsRawList;
        this.drawingsList = parseDrawingFromRaw();
        this.expectedDrawing = expectedDrawing;
        this.difference = difference;
        this.docxDocument = docxDocument;
    }

    DrawingsList parseDrawingFromRaw() throws Docx4JException {
        DrawingsList drawingsList = new DrawingsList();

        if (drawingsRawList.getErrorMessage() != null) {
            drawingsList.setErrorMessage(drawingsRawList.getErrorMessage());
            return drawingsList;
        }

        for (DrawingRaw drawingRaw : drawingsRawList.getDrawingsRaw()) {
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

            drawingsList.addDrawing(drawing);
        }

        return drawingsList;
    }

    void parseDrawing() {
        docxDocument.setDrawings(drawingsList);

        DrawingsList list = new DrawingsList();
        list.setErrorMessage(drawingsList.getErrorMessage());

        for (Drawing<Double, Boolean> drawing: drawingsList.getDrawings()) {
            Drawing<String, String> differenceDrawing = new DrawingDiffer(drawing, expectedDrawing)
                    .getDifferenceDrawing();

            list.addDrawing(differenceDrawing);
        }

        difference.setDrawings(list);
    }
}
