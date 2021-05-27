package com.formatChecker.compare.collector;

import com.formatChecker.compare.model.Difference;
import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.model.participants.Run;
import com.formatChecker.config.model.participants.Section;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DifferResultCollector {
    public String getDifferenceAsString(Difference difference) {
        String result = "Comparison results:\n";

        String pagesResult = getPageDifferenceAsString(difference.getPages());
        String sectionResult = getSectionDifferenceAsString(difference.getSection());
        StringBuilder paragraphsResult = getParagraphsDifferenceAsString(difference.getParagraphs());

        result += pagesResult + sectionResult + paragraphsResult;
        return result;
    }

    String getPageDifferenceAsString(String pagesDifference) {
        if (pagesDifference != null) return String.format("\nNumber of pages: %s", pagesDifference +"\n");
        return "";
    }

    String getSectionDifferenceAsString(Section<List<String>> sectionDifference) {
        String result = "\nSection properties: \n\t";
        if (sectionDifference.getOrientation() != null) result += sectionDifference.getOrientation() + "\n\t";
        if (sectionDifference.getMargins() != null) result += sectionDifference.getMargins().stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n\t")) + "\n\t";
        if (sectionDifference.getPageHeight() != null) result += sectionDifference.getPageHeight() + "\n\t";
        if (sectionDifference.getPageWidth() != null) result += sectionDifference.getPageWidth() + "\n\t";

        return result;
    }

    StringBuilder getParagraphsDifferenceAsString(List<Paragraph> paragraphs) {
        int count = 0;

        StringBuilder result = new StringBuilder("\nParagraphs :\n");

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
