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
    private static final String headerStyleName = "header";
    private static final String bodyStyleName = "body";

    public Difference compare(String configPath, String documentPath) throws Docx4JException, IOException {

        DocxDocument document = new DocxParser().parseDocument(documentPath, configPath);

        Config config = new ConfigParser(configPath).parseConfig();

        Difference difference = new Difference(new ArrayList<>());
        difference.setPages(comparePages(document.getPages(), config.getPages()));
        difference.setSection(new SectionDiffer(document.getSection(), config.getSection()).getSectionDifference());

        List<Paragraph> paragraphs = new ArrayList<>();

        if (!config.getFinByToc()) {
            paragraphs = Arrays.asList(new Paragraph[document.getParagraphs().size()]);

            for (Map.Entry<String, Style> entry : config.getStyles().entrySet()) {
                List<Integer> paragraphIds = entry.getValue().getParagraphIndexes();
                for (Integer id: paragraphIds) {
                    Style style = config.getStyles().get(entry.getKey());
                    paragraphs.set(id - 1, new ParagraphDiffer(document.getParagraphs().get(id - 1),
                            style.getParagraph(), style.getRun()).getParagraphDifference());
                }
            }
        }
        else {
            for (Paragraph p : document.getParagraphs()) {
                if (p.getIsHeader()) {
                    Style headerStyle = config.getStyles().get(headerStyleName);
                    paragraphs.add(new ParagraphDiffer(p, headerStyle.getParagraph(), headerStyle.getRun()).
                            getParagraphDifference());
                } else {
                    Style bodyStyle = config.getStyles().get(bodyStyleName);
                    paragraphs.add(new ParagraphDiffer(p, bodyStyle.getParagraph(), bodyStyle.getRun()).
                            getParagraphDifference());
                }
            }
        }

        for (Paragraph p: paragraphs) {
            difference.addParagraph(p);
        }

        String test = new DifferResultCollector(difference).getDifferenceAsString();

        return difference;
    }

    public String comparePages(Integer actualPages, Pages expectedPages) {
        if (actualPages >= expectedPages.getMin() && actualPages < expectedPages.getMax()) {
            return null;
        } else {
            return String.format("document has %s pages, should have %d-%d pages",
                    actualPages, expectedPages.getMin(), expectedPages.getMax());
        }
    }
}
