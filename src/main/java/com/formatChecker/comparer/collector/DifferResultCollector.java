package com.formatChecker.comparer.collector;

import com.formatChecker.comparer.model.Difference;
import com.formatChecker.config.model.participants.Footer;
import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.model.participants.Run;
import com.formatChecker.config.model.participants.Section;
import com.formatChecker.document.model.participants.Drawing;
import com.formatChecker.document.model.participants.Heading;

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

        return totalResult.equals("") ? "Comparison results: everything ok" : "Comparison results:\n" + totalResult;
    }

    String getPageDifferenceAsString() {
        if (difference.getPages() != null)
            return String.format("\nNumber of pages: %s", difference.getPages() + "\n");

        return "";
    }

    String getSectionDifferenceAsString() {
        List<Section<String>> sectionsDifference = difference.getSections();

        StringBuilder result = new StringBuilder("");

        int count = 0;
        for (Section<String> sectionDifference : sectionsDifference) {
            ++count;

            String sectionResult = "";

            if (sectionDifference.getOrientation() != null)
                sectionResult += sectionDifference.getOrientation() + "\n\t";
            if (sectionDifference.getMargins() != null) sectionResult += sectionDifference.getMargins().stream()
                    .filter(Objects::nonNull)
                    .map(String::valueOf)
                    .collect(Collectors.joining("\n\t")) + "\n\t";
            if (sectionDifference.getPageHeight() != null) sectionResult += sectionDifference.getPageHeight() + "\n\t";
            if (sectionDifference.getPageWidth() != null) sectionResult += sectionDifference.getPageWidth() + "\n\t";

            if (!sectionResult.equals(""))
                result.append(String.format("\nSection #%d: \n\t", count)).append(sectionResult);
        }

        return result.toString().equals("") ? "" : "\nSection properties: \n\t" + result;
    }

    String getFooterDifferenceAsString() {
        Footer differenceFooter = difference.getFooter();

        if (differenceFooter != null) {
            String type = differenceFooter.getType();
            String alignment = differenceFooter.getAlignment();

            if (!differenceFooter.getErrorMessage().equals(""))
                return String.format("\nNo footer found: %s\n", differenceFooter.getErrorMessage());

            if (differenceFooter.getErrorMessage().equals("") && type == null && alignment == null)
                return "";

            String result = "\nFooter:\n\t";

            if (type != null)
                result += type + "\n\t";
            if (alignment != null)
                result += alignment + "\n\t";

            return result;
        }

        return "";
    }

    String getHeadingDifferenceAsString() {
        List<Heading> headings = difference.getHeadings();
        if (headings == null)
            return "";
        else
            return "\nHeadings:\n\t" + headings
                    .stream()
                    .map(Heading::getText)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining("\n\t")) + "\n";
    }

    StringBuilder getDrawingsDifferenceAsString() {
        List<Drawing<String, String>> drawings = difference.getDrawings();

        StringBuilder result = new StringBuilder("");

        int count = 0;
        for (Drawing<String, String> drawing : drawings) {
            if (drawing == null)
                return new StringBuilder();

            ++count;

            String text = drawing.getText();
            String drawingResult = String.format("\nDrawing #%d: \n\t", count);

            result
                    .append(drawingResult)
                    .append(text != null ? text + "\n\t" : "")
                    .append(getParagraphDifferenceAsString(drawing.getDrawing()))
                    .append(getParagraphDifferenceAsString(drawing.getDescription()));
        }

        return result.toString().equals("") ? new StringBuilder() : new StringBuilder("\nDrawings:\n" + result);
    }

    StringBuilder getParagraphsDifferenceAsString() {
        List<Paragraph<String, String>> paragraphs = difference.getParagraphs();

        StringBuilder result = new StringBuilder();

        int count = 0;
        for (Paragraph p : paragraphs) {
            ++count;
            String text = p.getText();
            String paragraphResult = "";

            result
                    .append(paragraphResult)
                    .append(getParagraphDifferenceAsString(p))
                    .append(getRunsDifferenceAsString(p.getRuns()));

            if (!result.toString().equals(""))
                return new StringBuilder(String.format("\nParagraph #%d (%s...): \n\t",
                        count, text.substring(0, Math.min(text.length(), 100))) + result);
            else
                return new StringBuilder();
        }

        return result.toString().equals("") ? new StringBuilder() : new StringBuilder("\nParagraphs:\n" + result);
    }

    String getParagraphDifferenceAsString(Paragraph<String, String> paragraph) {
        String paragraphResult = "";

        if (paragraph.getAlignment() != null) paragraphResult += paragraph.getAlignment() + "\n\t";
        if (paragraph.getFirstLineIndent() != null) paragraphResult += paragraph.getFirstLineIndent() + "\n\t";
        if (paragraph.getLeftIndent() != null) paragraphResult += paragraph.getLeftIndent() + "\n\t";
        if (paragraph.getRightIndent() != null) paragraphResult += paragraph.getRightIndent() + "\n\t";
        if (paragraph.getLineSpacing() != null) paragraphResult += paragraph.getLineSpacing() + "\n\t";
        if (paragraph.getSpacingBefore() != null) paragraphResult += paragraph.getSpacingBefore() + "\n\t";
        if (paragraph.getSpacingAfter() != null) paragraphResult += paragraph.getSpacingAfter() + "\n\t";
        if (paragraph.getPageBreakBefore() != null) paragraphResult += paragraph.getPageBreakBefore() + "\n\t";

        return paragraphResult;
    }

    String getRunsDifferenceAsString(List<Run<String, String>> runs) {
        Set<String> result = new HashSet<>();

        for (Run<String, String> r : runs) {
            if (r.getFontFamily() != null) result.add(r.getFontFamily() + "\n\t");
            if (r.getFontSize() != null) result.add(r.getFontSize() + "\n\t");
            if (r.getBold() != null) result.add(r.getBold() + "\n\t");
            if (r.getItalic() != null) result.add(r.getItalic() + "\n\t");
            if (r.getStrikethrough() != null) result.add(r.getStrikethrough() + "\n\t");
            if (r.getUnderline() != null) result.add(r.getUnderline() + "\n\t");
            if (r.getTextColor() != null) result.add(r.getTextColor() + "\n\t");
        }

        return String.join("", result);
    }

    public String getDifferenceAsString() {
        return differenceAsString;
    }
}
