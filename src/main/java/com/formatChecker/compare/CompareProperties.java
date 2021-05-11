package com.formatChecker.compare;

import com.formatChecker.parseConfig.parser.ConfigParser;
import com.formatChecker.parserDocx.parser.DocxParser;
import org.docx4j.openpackaging.exceptions.Docx4JException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompareProperties {
    public static List<String> compare(String configPath, String documentPath) throws Docx4JException, IOException {
        List<String> propertyValue = DocxParser.getAlignment(documentPath);
        String configValue = ConfigParser.getAlignment(configPath);
        List<String> result = new ArrayList<>();

        propertyValue.forEach(value -> {
            if (value.equals(configValue)) {
                result.add("alignment ok");
            } else {
                result.add("alignment not ok");
            }
        });

        return result;
    }
}
