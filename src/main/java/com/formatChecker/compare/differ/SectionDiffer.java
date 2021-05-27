package com.formatChecker.compare.differ;

import com.formatChecker.config.model.participants.Section;

import java.util.ArrayList;
import java.util.List;

public class SectionDiffer implements Differ {
    public Section<List<String>> getSectionDifference(Section<List<Double>> actualSection, Section<List<Double>> expectedSection) {
        Section<List<String>> sectionDifference = new Section<>();

        sectionDifference.setOrientation(checkStringParameter(
                actualSection.getOrientation(), expectedSection.getOrientation(), "orientation"));
        sectionDifference.setMargins(compareMargins(actualSection.getMargins(), expectedSection.getMargins()));
        sectionDifference.setPageHeight(checkStringParameter(
                actualSection.getPageHeight(), expectedSection.getPageHeight(), "page height", "cm"));
        sectionDifference.setPageWidth(checkStringParameter(
                actualSection.getPageWidth(), expectedSection.getPageWidth(), "page width", "cm"));

        return sectionDifference;
    }

    List<String> compareMargins(List<Double> actualMargins, List<Double> expectedMargins) {
        if (actualMargins.equals(expectedMargins)) return null;
        else {
            List<String> result = new ArrayList<>();
            if (!actualMargins.get(0).equals(expectedMargins.get(0))) {
                result.add(String.format("change top margin from %.2fcm to %.2fcm",
                        actualMargins.get(0), expectedMargins.get((0))));
            }

            if (!actualMargins.get(1).equals(expectedMargins.get(1))) {
                result.add(String.format("change right margin from %.2fcm to %.2fcm",
                        actualMargins.get(0), expectedMargins.get((0))));
            }

            if (!actualMargins.get(2).equals(expectedMargins.get(2))) {
                result.add(String.format("change bottom margin from %.2fcm to %.2fcm",
                        actualMargins.get(0), expectedMargins.get((0))));
            }

            if (!actualMargins.get(3).equals(expectedMargins.get(3))) {
                result.add(String.format("change left margin from %.2fcm to %.2fcm",
                        actualMargins.get(0), expectedMargins.get((0))));
            }
            return result;
        }
    }
}
