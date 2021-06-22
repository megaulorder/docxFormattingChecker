package com.formatChecker.comparer.differ;

import com.formatChecker.config.model.participants.ConfigDrawing;
import com.formatChecker.config.model.participants.Run;
import com.formatChecker.document.model.participants.Drawing;

public class DrawingDiffer {
    Drawing<Double, Boolean> actualDrawing;
    ConfigDrawing expectedDrawing;
    Drawing<String, String> differenceDrawing;

    public DrawingDiffer(Drawing<Double, Boolean> actualDrawing, ConfigDrawing expectedDrawing) {
        this.actualDrawing = actualDrawing;
        this.expectedDrawing = expectedDrawing;
        this.differenceDrawing = getDifference();
    }

    Drawing<String, String> getDifference() {
        Drawing<String, String> drawing = new Drawing<>();

        if (actualDrawing == null && expectedDrawing != null)
            return null;
        else if (actualDrawing != null && expectedDrawing == null)
            return  null;
        else {
            drawing.setText(compareText());

            drawing.setDrawing(new ParagraphDiffer(
                    actualDrawing.getDrawing(),
                    expectedDrawing.getDrawingPosition())
                    .getParagraphDifference());

            drawing.setDescription(new ParagraphDiffer(
                    actualDrawing.getDescription(),
                    expectedDrawing.getDescription().getParagraph())
                    .getParagraphDifference());

            for (Run<Boolean, Double> run : actualDrawing.getDescription().getRuns()) {
                drawing.getDescription().addRun(new RunDiffer(
                        run,
                        expectedDrawing.getDescription().getRun())
                        .getRunDifference());
            }

            return drawing;
        }
    }

    String compareText() {
        String expectedTextStart = expectedDrawing.getTextStartsWith();
        String result = null;

        if (!actualDrawing.getText().contains(expectedTextStart))
            result = "drawing description text should start with " + expectedTextStart;

        return result;
    }

    public Drawing<String, String> getDifferenceDrawing() {
        return differenceDrawing;
    }
}
