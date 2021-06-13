package com.formatChecker.comparer.differ;

import com.formatChecker.config.model.participants.Section;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SectionDiffer implements Differ {
    Section<Double> actualSection;
    Section<Double> expectedSection;

    public SectionDiffer(Section<Double> actualSection, Section<Double> expectedSection) {
        this.actualSection = actualSection;
        this.expectedSection = expectedSection;
    }

    public Section<String> getSectionDifference() {
        Section<String> sectionDifference = new Section<>();

        sectionDifference.setOrientation(checkStringParameter(
                actualSection.getOrientation(), expectedSection.getOrientation(), "orientation"));

        sectionDifference.setMargins(compareMargins());

        sectionDifference.setPageHeight(checkDoubleParameter(
                actualSection.getPageHeight(), expectedSection.getPageHeight(), "page height", "cm"));
        sectionDifference.setPageWidth(checkDoubleParameter(
                actualSection.getPageWidth(), expectedSection.getPageWidth(), "page width", "cm"));

        return sectionDifference;
    }

    List<String> compareMargins() {
        List<Double> actualMargins = actualSection.getMargins();
        List<Double> expectedMargins = expectedSection.getMargins();

        if (actualMargins.equals(expectedMargins)) return null;
        else {
            List<String> result = Arrays.asList(new String[4]);
            if (!actualMargins.get(0).equals(expectedMargins.get(0))) {
                result.set(0, String.format("change top margin from %.2fcm to %.2fcm",
                        actualMargins.get(0), expectedMargins.get((0))));
            }

            if (!actualMargins.get(1).equals(expectedMargins.get(1))) {
                result.set(1, String.format("change right margin from %.2fcm to %.2fcm",
                        actualMargins.get(1), expectedMargins.get((1))));
            }

            if (!actualMargins.get(2).equals(expectedMargins.get(2))) {
                result.set(2, String.format("change bottom margin from %.2fcm to %.2fcm",
                        actualMargins.get(2), expectedMargins.get((2))));
            }

            if (!actualMargins.get(3).equals(expectedMargins.get(3))) {
                result.set(3, String.format("change left margin from %.2fcm to %.2fcm",
                        actualMargins.get(3), expectedMargins.get((3))));
            }

            return result;
        }
    }
}
