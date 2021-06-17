package com.formatChecker.comparer.collector;

import com.formatChecker.comparer.model.Difference;
import com.formatChecker.config.model.participants.Footer;
import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.model.participants.Run;
import com.formatChecker.config.model.participants.Section;
import com.formatChecker.document.model.Heading;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DifferResultCollector {
    Difference difference;
    String result, pagesResult, sectionResult, footerResult, headingResult;
    StringBuilder paragraphsResult;

    public DifferResultCollector(Difference difference) {
        this.difference = difference;
    }

    public String getDifferenceAsString() {
        pagesResult = getPageDifferenceAsString();
        sectionResult = getSectionDifferenceAsString();
        footerResult = getFooterDifferenceAsString();
        headingResult = getHeadingDifferenceAsString();
        paragraphsResult = getParagraphsDifferenceAsString();

        result = "Comparison results:\n" +
                pagesResult +
                sectionResult +
                footerResult +
                headingResult +
                paragraphsResult;

        return result;
    }

    String getPageDifferenceAsString() {
        if (difference.getPages() != null)
            return String.format("\nNumber of pages: %s", difference.getPages() + "\n");

        return "";
    }

    String getSectionDifferenceAsString() {
        List<Section<String>> sectionsDifference = difference.getSections();

        StringBuilder result = new StringBuilder("\nSection properties: \n\t");

        int count = 0;
        for (Section<String> sectionDifference : sectionsDifference) {
            ++count;

            String sectionResult = String.format("\nSection #%d: \n\t", count);

            if (sectionDifference.getOrientation() != null)
                sectionResult += sectionDifference.getOrientation() + "\n\t";
            if (sectionDifference.getMargins() != null) sectionResult += sectionDifference.getMargins().stream()
                    .filter(Objects::nonNull)
                    .map(String::valueOf)
                    .collect(Collectors.joining("\n\t")) + "\n\t";
            if (sectionDifference.getPageHeight() != null) sectionResult += sectionDifference.getPageHeight() + "\n\t";
            if (sectionDifference.getPageWidth() != null) sectionResult += sectionDifference.getPageWidth() + "\n\t";

            result.append(sectionResult);
        }

        return result.toString();
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

    StringBuilder getParagraphsDifferenceAsString() {
        List<Paragraph<String, String>> paragraphs = difference.getParagraphs();

        int count = 0;

        StringBuilder result = new StringBuilder("\nParagraphs:\n");

        for (Paragraph p : paragraphs) {
            ++count;
            String text = p.getText();
            String paragraphResult = String.format("\nParagraph #%d (%s...): \n\t",
                    count, text.substring(0, Math.min(text.length(), 100)));

            if (p.getAlignment() != null) paragraphResult += p.getAlignment() + "\n\t";
            if (p.getFirstLineIndent() != null) paragraphResult += p.getFirstLineIndent() + "\n\t";
            if (p.getLeftIndent() != null) paragraphResult += p.getLeftIndent() + "\n\t";
            if (p.getRightIndent() != null) paragraphResult += p.getRightIndent() + "\n\t";
            if (p.getLineSpacing() != null) paragraphResult += p.getLineSpacing() + "\n\t";
            if (p.getSpacingBefore() != null) paragraphResult += p.getSpacingBefore() + "\n\t";
            if (p.getSpacingAfter() != null) paragraphResult += p.getSpacingAfter() + "\n\t";
            if (p.getPageBreakBefore() != null) paragraphResult += p.getPageBreakBefore() + "\n\t";

            result.append(paragraphResult);

            String runResult = getRunsDifferenceAsString(p.getRuns());
            result.append(runResult);
        }

        return result;
    }

    String getRunsDifferenceAsString(List<Run> runs) {
        Set<String> result = new HashSet<>();

        for (Run r : runs) {
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
}
