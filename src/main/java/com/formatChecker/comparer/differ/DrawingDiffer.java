package com.formatChecker.comparer.differ;

import com.formatChecker.config.model.participants.ConfigDrawing;
import com.formatChecker.config.model.participants.Run;
import com.formatChecker.document.model.participants.Drawing;

public class DrawingDiffer {
    private static final String DESCRIPTION_EXTRA_WORDS = "(ARABIC | SEQ| Рисунок \\\\\\*)";

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
            if (actualDrawing.getText() != null)
                drawing.setText(actualDrawing.getText().replaceAll(DESCRIPTION_EXTRA_WORDS, ""));

            drawing.setTextErrorMessage(compareText());

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
        String result = "";

        if (actualDrawing.getText() == null || actualDrawing.getText().equals(""))
            return "add a drawing description";

        if (!actualDrawing.getText().startsWith(expectedTextStart))
            result = "drawing description text should start with " + expectedTextStart;

        return result;
    }

    public Drawing<String, String> getDifferenceDrawing() {
        return differenceDrawing;
    }
}
