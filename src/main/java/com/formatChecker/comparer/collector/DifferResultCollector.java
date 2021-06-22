package com.formatChecker.comparer.collector;

import com.formatChecker.comparer.model.Difference;
import com.formatChecker.config.model.participants.Footer;
import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.model.participants.Run;
import com.formatChecker.config.model.participants.Section;
import com.formatChecker.document.model.participants.Drawing;
import com.formatChecker.document.model.participants.Heading;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
    Integer runErrors;
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
                sectionErrors + drawingErrors + footerErrors + headingErrors + paragraphErrors + runErrors);
        String section = String.format("\n\tErrors in section properties: %d", sectionErrors);
        String drawing = String.format("\n\tErrors in drawing properties: %d", drawingErrors);
        String footer = String.format("\n\tErrors in footer properties: %d", footerErrors);
        String heading = String.format("\n\tErrors in headings: %d", headingErrors);
        String paragraph = String.format("\n\tErrors in paragraph properties: %d", paragraphErrors);
        String run = String.format("\n\tErrors in font properties: %d", runErrors);

        return total + section + drawing + footer + heading + paragraph + run + totalResult;
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

        return result.toString().equals("") ? "" : "\nSection properties: \n\t" + result;
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
        List<Heading> headings = difference.getHeadings();

        headingErrors = 0;

        if (headings == null)
            return "";

        StringBuilder result = new StringBuilder();

        for (Heading heading : headings) {
            if (heading.getText() != null) {
                result.append(heading.getText()).append("\n\t");
                ++headingErrors;
            }
        }

        return result.toString().equals("") ? "" : "\nHeadings:\n\t" + result + "\n\t";
    }

    StringBuilder getDrawingsDifferenceAsString() {
        List<Drawing<String, String>> drawings = difference.getDrawings();

        drawingErrors = 0;

        StringBuilder result = new StringBuilder();

        int count = 0;
        for (Drawing<String, String> drawing : drawings) {
            if (drawing == null)
                return new StringBuilder();

            ++count;

            String text = drawing.getText();
            String drawingResult = text != null ? text + "\n\t" : "";

            if (!getParagraphDifferenceAsString(drawing.getDrawing()).equals("")) {
                drawingResult += "drawing: " + getParagraphDifferenceAsString(drawing.getDrawing());
                ++drawingErrors;
            }

            if (!getParagraphDifferenceAsString(drawing.getDescription()).equals("")) {
                drawingResult += "description: " + getParagraphDifferenceAsString(drawing.getDrawing());
                ++drawingErrors;
            }

            if (!drawingResult.equals(""))
                result
                        .append(String.format("\nDrawing #%d: \n\t", count))
                        .append(drawingResult);
        }

        return result.toString().equals("") ? new StringBuilder() : new StringBuilder("\nDrawings:\n" + result);
    }

    StringBuilder getParagraphsDifferenceAsString() {
        List<Paragraph<String, String>> paragraphs = difference.getParagraphs();

        StringBuilder result = new StringBuilder();

        paragraphErrors = 0;
        runErrors = 0;

        int count = 0;
        for (Paragraph p : paragraphs) {
            ++count;
            String text = p.getText();

            String paragraphDifference = getParagraphDifferenceAsString(p) + getRunsDifferenceAsString(p.getRuns());

            if (!paragraphDifference.equals(""))
                result
                        .append(String.format("\nParagraph #%d (%s...): \n\t",
                                count, text.substring(0, Math.min(text.length(), 100))))
                        .append(paragraphDifference);
        }

        return result.toString().equals("") ? new StringBuilder() : new StringBuilder("\nParagraphs:\n" + result);
    }

    String getParagraphDifferenceAsString(Paragraph<String, String> paragraph) {
        String paragraphResult = "";

        if (paragraph.getAlignment() != null) {
            paragraphResult += paragraph.getAlignment() + "\n\t";
            ++paragraphErrors;
        }

        if (paragraph.getFirstLineIndent() != null) {
            paragraphResult += paragraph.getFirstLineIndent() + "\n\t";
            ++paragraphErrors;
        }

        if (paragraph.getLeftIndent() != null) {
            paragraphResult += paragraph.getLeftIndent() + "\n\t";
            ++paragraphErrors;
        }

        if (paragraph.getRightIndent() != null) {
            paragraphResult += paragraph.getRightIndent() + "\n\t";
            ++paragraphErrors;
        }

        if (paragraph.getLineSpacing() != null) {
            paragraphResult += paragraph.getLineSpacing() + "\n\t";
            ++paragraphErrors;
        }

        if (paragraph.getSpacingBefore() != null) {
            paragraphResult += paragraph.getSpacingBefore() + "\n\t";
            ++paragraphErrors;
        }

        if (paragraph.getSpacingAfter() != null) {
            paragraphResult += paragraph.getSpacingAfter() + "\n\t";
            ++paragraphErrors;
        }

        if (paragraph.getPageBreakBefore() != null) {
            paragraphResult += paragraph.getPageBreakBefore() + "\n\t";
            ++paragraphErrors;
        }

        return paragraphResult;
    }

    String getRunsDifferenceAsString(List<Run<String, String>> runs) {
        Set<String> result = new HashSet<>();

        for (Run<String, String> r : runs) {
            if (r.getFontFamily() != null) {
                result.add(r.getFontFamily() + "\n\t");
                ++runErrors;
            }

            if (r.getFontSize() != null) {
                result.add(r.getFontSize() + "\n\t");
                ++runErrors;
            }

            if (r.getBold() != null) {
                result.add(r.getBold() + "\n\t");
                ++runErrors;
            }

            if (r.getItalic() != null) {
                result.add(r.getItalic() + "\n\t");
                ++runErrors;
            }

            if (r.getStrikethrough() != null) {
                result.add(r.getStrikethrough() + "\n\t");
                ++runErrors;
            }

            if (r.getUnderline() != null) {
                result.add(r.getUnderline() + "\n\t");
                ++runErrors;
            }

            if (r.getTextColor() != null) {
                result.add(r.getTextColor() + "\n\t");
                ++runErrors;
            }
        }

        return String.join("", result);
    }

    public String getDifferenceAsString() {
        return differenceAsString;
    }
}
