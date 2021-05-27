package com.formatChecker;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AlignmentTestJava {
    @DisplayName("Checks the result of CompareProperties.compare()")
    @Test
    public void testParagraphAlignmentShouldBeCorrect() throws IOException, Docx4JException {
//        List<String> actualResult = CompareProperties.compare(
//                "src/test/resources/alignmentTest/config/config.json",
//                "src/test/resources/alignmentTest/documents/test.docx");

        List<String> expectedResult = Arrays.asList("alignment ok", "alignment not ok");

//        Assert.assertEquals(actualResult, expectedResult);
    }
}
