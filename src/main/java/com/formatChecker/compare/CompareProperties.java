package com.formatChecker.compare;

import com.formatChecker.config.model.Config;
import com.formatChecker.config.parser.ConfigParser;
import com.formatChecker.document.model.DocxDocument;
import com.formatChecker.document.parser.DocxParser;
import org.docx4j.openpackaging.exceptions.Docx4JException;

import java.io.IOException;

public class CompareProperties {
    public static String compare(String configPath, String documentPath) throws Docx4JException, IOException {

        DocxDocument document = new DocxParser().getDocumentProperties(documentPath);

        Config config = ConfigParser.parseConfig(configPath);

        return "placeholder";
    }
}
