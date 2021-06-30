package com.formatChecker.comparer.collector;

import com.formatChecker.comparer.model.Difference;
import com.formatChecker.comparer.model.participants.DrawingsList;
import com.formatChecker.comparer.model.participants.HeadingsList;
import com.formatChecker.config.model.participants.*;
import com.formatChecker.document.model.participants.Drawing;

import java.util.*;
import java.util.stream.Collectors;

public class DifferResultCollector {
    Difference difference;
    String pagesResult;
    String sectionResult;
    String footerResult;
    String headingResult;

    StringBuilder drawingsResult;
    StringBuilder paragraphsResult;

    Integer sectionErrors;
    Integer drawingErrors;
    Integer footerErrors;
    Integer paragraphErrors;
    Integer headingErrors;

    String differenceAsString;

    public DifferResultCollector(Difference difference) {
        this.difference = difference;
        this.differenceAsString = collectDifferenceAsString();
    }

    public String collectDifferenceAsString() {
        pagesResult = getPageDifferenceAsString();
        sectionResult = getSectionDifferenceAsString();
        footerResult = getFooterDifferenceAsString();
        headingResult = getHeadingDifferenceAsString();
        paragraphsResult = getParagraphsDifferenceAsString();
        drawingsResult = getDrawingsDifferenceAsString();

        String totalResult = pagesResult +
                sectionResult +
                footerResult +
                headingResult +
                paragraphsResult +
                drawingsResult;

        return totalResult.equals("") ?
                "Comparison results: everything ok" :
                "Comparison results:" + getResultsStats(totalResult);
    }

    String getResultsStats(String totalResult) {
        String total = String.format("\n\tTotal errors: %d",
                sectionErrors + drawingErrors + footerErrors + headingErrors + paragraphErrors);
        String section = String.format("\n\tErrors in section properties: %d", sectionErrors);
        String drawing = String.format("\n\tErrors in drawing properties: %d", drawingErrors);
        String footer = String.format("\n\tErrors in footer properties: %d", footerErrors);
        String heading = String.format("\n\tErrors in headings: %d", headingErrors);
        String paragraph = String.format("\n\tParagraphs with errors: %d", paragraphErrors);
        String mostCommon = String.format("\n\tMost common error: %s", getMostCommonError(totalResult));

        return total + section + drawing + footer + heading + paragraph + mostCommon + totalResult;
    }

    String getPageDifferenceAsString() {
        if (difference.getPages() != null)
            return String.format("\n\nNumber of pages: %s", difference.getPages() + "\n");

        return "";
    }

    String getSectionDifferenceAsString() {
        List<Section<String>> sectionsDifference = difference.getSections();

        sectionErrors = 0;

        StringBuilder result = new StringBuilder();

        int count = 0;
        for (Section<String> sectionDifference : sectionsDifference) {
            ++count;

            if (sectionDifference == null)
                return "";

            String sectionResult = "";

            if (sectionDifference.getOrientation() != null) {
                sectionResult += sectionDifference.getOrientation() + "\n\t";
                ++sectionErrors;
            }

            if (sectionDifference.getMargins() != null) {
                sectionResult += sectionDifference.getMargins().stream()
                        .filter(Objects::nonNull)
                        .map(String::valueOf)
                        .collect(Collectors.joining("\n\t")) + "\n\t";
                ++sectionErrors;
            }

            if (sectionDifference.getPageHeight() != null) {
                sectionResult += sectionDifference.getPageHeight() + "\n\t";
                ++sectionErrors;
            }

            if (sectionDifference.getPageWidth() != null) {
                sectionResult += sectionDifference.getPageWidth() + "\n\t";
                ++sectionErrors;
            }

            if (!sectionResult.equals("")) {
                result.append(String.format("\nSection #%d: \n\t", count)).append(sectionResult);
            }
        }

        return result.toString().equals("") ? "" : "\n\nSection properties: \n\t" + result;
    }

    String getFooterDifferenceAsString() {
        Footer differenceFooter = difference.getFooter();

        footerErrors = 0;

        if (differenceFooter != null) {
            String type = differenceFooter.getType();
            String alignment = differenceFooter.getAlignment();

            if (!differenceFooter.getErrorMessage().equals("")) {
                ++footerErrors;
                return String.format("\nNo footer found: %s\n", differenceFooter.getErrorMessage());
            }

            if (differenceFooter.getErrorMessage().equals("") && type == null && alignment == null)
                return "";

            String result = "\nFooter:\n\t";

            if (type != null) {
                result += type + "\n\t";
                ++footerErrors;
            }

            if (alignment != null) {
                result += alignment + "\n\t";
                ++footerErrors;
            }

            return result;
        }

        return "";
    }

    String getHeadingDifferenceAsString() {
        HeadingsList headings = difference.getHeadings();

        String warningMessage = "Warning : you might need to update Table of Contents manually\n\t";

        headingErrors = 0;

        if (headings == null)
            return "";

        StringBuilder result = new StringBuilder();

        if (headings.getWarningMessage() != null)
            return "\nHeadings:\n\t" + headings.getWarningMessage() + "\n\t";

        for (Heading heading : headings.getHeadings()) {
            if (heading.getText() != null) {
                result.append(heading.getText()).append("\n\t");
                ++headingErrors;
            }
        }

        return result.toString().equals("") ? "" : "\nHeadings:\n\t" + warningMessage + result + "\n\t";
    }

    StringBuilder getDrawingsDifferenceAsString() {
        DrawingsList drawings = difference.getDrawings();

        drawingErrors = 0;

        StringBuilder result = new StringBuilder();

        if (drawings.getErrorMessage() != null) {
            ++drawingErrors;
            return new StringBuilder("\nDrawings:\n\t" + drawings.getErrorMessage() + "\n\t");
        }

        int count = 0;
        for (Drawing<String, String> drawing : drawings.getDrawings()) {
            if (drawing == null)
                return new StringBuilder();

            ++count;

            String drawingResult = "";

            if (!drawing.getTextErrorMessage().equals(""))
                drawingResult += drawing.getTextErrorMessage() + "\n\t";

            if (!getParagraphDifferenceAsString(drawing.getDrawing()).equals(""))
                drawingResult += "drawing: " + getParagraphDifferenceAsString(drawing.getDrawing());

            if (!getParagraphDifferenceAsString(drawing.getDescription()).equals(""))
                drawingResult += "description: " + getParagraphDifferenceAsString(drawing.getDrawing());

            if (!drawingResult.equals("")) {
                ++drawingErrors;
                result
                        .append(String.format("\nDrawing #%d: (%s)\n\t", count, drawing.getText()))
                        .append(drawingResult);
            }
        }

        return result.toString().equals("") ? new StringBuilder() : new StringBuilder("\nDrawings:\n" + result);
    }

    StringBuilder getParagraphsDifferenceAsString() {
        List<Paragraph<String, String>> paragraphs = difference.getParagraphs();

        StringBuilder result = new StringBuilder();

        paragraphErrors = 0;

        int count = 0;
        for (Paragraph p : paragraphs) {
            ++count;
            String text = p.getText();

            String paragraphDifference = getParagraphDifferenceAsString(p) + getRunsDifferenceAsString(p.getRuns());

            if (!paragraphDifference.equals("")) {
                ++paragraphErrors;

                result
                        .append(String.format("\nParagraph #%d (%s...): \n\t",
                                count, text.substring(0, Math.min(text.length(), 100))))
                        .append(paragraphDifference);
            }
        }

        return result.toString().equals("") ? new StringBuilder() : new StringBuilder("\nParagraphs:\n" + result);
    }

    String getParagraphDifferenceAsString(Paragraph<String, String> paragraph) {
        String paragraphResult = "";

        if (paragraph.getAlignment() != null)
            paragraphResult += paragraph.getAlignment() + "\n\t";

        if (paragraph.getFirstLineIndent() != null)
            paragraphResult += paragraph.getFirstLineIndent() + "\n\t";

        if (paragraph.getLeftIndent() != null)
            paragraphResult += paragraph.getLeftIndent() + "\n\t";

        if (paragraph.getRightIndent() != null)
            paragraphResult += paragraph.getRightIndent() + "\n\t";

        if (paragraph.getLineSpacing() != null)
            paragraphResult += paragraph.getLineSpacing() + "\n\t";

        if (paragraph.getSpacingBefore() != null)
            paragraphResult += paragraph.getSpacingBefore() + "\n\t";

        if (paragraph.getSpacingAfter() != null)
            paragraphResult += paragraph.getSpacingAfter() + "\n\t";

        if (paragraph.getPageBreakBefore() != null)
            paragraphResult += paragraph.getPageBreakBefore() + "\n\t";

        return paragraphResult;
    }

    String getRunsDifferenceAsString(List<Run<String, String>> runs) {
        Set<String> result = new HashSet<>();

        for (Run<String, String> r : runs) {
            if (r.getFontFamily() != null)
                result.add(r.getFontFamily() + "\n\t");

            if (r.getFontSize() != null)
                result.add(r.getFontSize() + "\n\t");

            if (r.getBold() != null)
                result.add(r.getBold() + "\n\t");

            if (r.getItalic() != null)
                result.add(r.getItalic() + "\n\t");

            if (r.getStrikethrough() != null)
                result.add(r.getStrikethrough() + "\n\t");

            if (r.getUnderline() != null)
                result.add(r.getUnderline() + "\n\t");

            if (r.getTextColor() != null)
                result.add(r.getTextColor() + "\n\t");
        }

        return String.join("", result);
    }

    public String getMostCommonError(String p) {
        Map<String, Integer> count = new HashMap<>();
        List<String> words = Arrays.stream(p.split("\\n\\t+"))
                .filter(s -> !s.contains("\n"))
                .collect(Collectors.toList());

        for (String w : words)
            count.put(w, count.getOrDefault(w, 0) + 1);

        return Collections.max(count.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public String getDifferenceAsString() {
        return differenceAsString;
    }
}
