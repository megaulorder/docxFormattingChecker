package com.formatChecker.compare.differ;

import com.formatChecker.compare.collector.DifferResultCollector;
import com.formatChecker.compare.model.Difference;
import com.formatChecker.config.model.Config;
import com.formatChecker.config.model.participants.Pages;
import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.model.participants.Style;
import com.formatChecker.config.parser.ConfigParser;
import com.formatChecker.document.model.DocxDocument;
import com.formatChecker.document.parser.DocxParser;
import org.docx4j.openpackaging.exceptions.Docx4JException;

import java.io.IOException;
import java.util.*;

public class DocumentDiffer {
    public Difference compare(String configPath, String documentPath) throws Docx4JException, IOException {

        DocxDocument document = new DocxParser().getDocumentProperties(documentPath);

        Config config = ConfigParser.parseConfig(configPath);

        Difference difference = new Difference(new ArrayList<>());
        difference.setPages(comparePages(document.getPages(), config.getPages()));
        difference.setSection(new SectionDiffer().getSectionDifference(document.getSection(), config.getSection()));

        List<Paragraph> paragraphs = Arrays.asList(new Paragraph[document.getParagraphs().size()]);

        for (Map.Entry<String, Style> entry : config.getStyles().entrySet()) {
            List<Integer> paragraphIds = entry.getValue().getParagraphIndexes();
            for (Integer id: paragraphIds) {
                Style style = config.getStyles().get(entry.getKey());
                paragraphs.set(id - 1, new ParagraphDiffer().getParagraphDifference(
                        document.getParagraphs().get(id - 1), style.getParagraph(), style.getRun()));
            }
        }

        for (Paragraph p: paragraphs) {
            difference.addParagraph(p);
        }

        return difference;
    }

    String comparePages(Integer actualPages, Pages expectedPages) {
        if (actualPages >= expectedPages.getMin() && actualPages < expectedPages.getMax()) {
            return null;
        } else {
            return String.format("document has %s pages, should have %d-%d pages",
                    actualPages, expectedPages.getMin(), expectedPages.getMax());
        }
    }
}
