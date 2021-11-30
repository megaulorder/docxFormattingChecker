package com.formatChecker.controller;

import com.formatChecker.comparer.differ.ParagraphDiffer;
import com.formatChecker.comparer.model.Difference;
import com.formatChecker.comparer.model.participants.HeadingsList;
import com.formatChecker.config.model.Config;
import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.config.model.participants.Run;
import com.formatChecker.controller.helper.RunHelper;
import com.formatChecker.document.model.DocxDocument;
import com.formatChecker.document.model.data.DocumentData;
import com.formatChecker.document.parser.paragraph.ParagraphDirectParser;
import com.formatChecker.fixer.ParagraphFixer;
import org.apache.commons.lang3.StringUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.parts.ThemePart;
import org.docx4j.wml.DocDefaults;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.Styles;

import java.util.List;
import java.util.Map;

public class ParagraphController implements RunHelper {
    private static final String HEADING_STYLE_NAME = "heading";
    private static final String BODY_STYLE_NAME = "body";

    P documentParagraph;
    Paragraph<Double, Boolean> actualParagraph;
    Paragraph<Double, Boolean> expectedParagraph;
    Difference difference;
    Boolean shouldFix;
    Integer index;

    Config config;
    DocumentData documentData;
    HeadingsList headings;
    List<String> paragraphsOnNewPages;
    DocxDocument docxDocument;
    Map<Integer, String> configStyles;

    Styles styles;
    DocDefaults docDefaults;
    ThemePart themePart;


    public ParagraphController(Integer index,
                               P documentParagraph,
                               Difference difference,
                               DocxDocument docxDocument,
                               DocumentData documentData,
                               Config config,
                               Map<Integer, String> configStyles,
                               HeadingsList headings,
                               List<String> paragraphsOnNewPages) throws Docx4JException {
        this.index = index;
        this.documentParagraph = documentParagraph;

        this.difference = difference;
        this.documentData = documentData;
        this.headings = headings;
        this.paragraphsOnNewPages = paragraphsOnNewPages;
        this.docxDocument = docxDocument;
        this.config = config;
        this.configStyles = configStyles;
        this.shouldFix = config.getGenerateNewDocument();

        this.styles = documentData.getStyles();
        this.docDefaults = documentData.getDocDefaults();
        this.themePart = documentData.getThemePart();

        this.actualParagraph = new ParagraphDirectParser(docDefaults, styles, themePart, documentParagraph, headings, paragraphsOnNewPages)
                .parseParagraph();
    }

    public void parseParagraph() {
        docxDocument.addParagraph(actualParagraph);

        if (configStyles == null) {
            if (actualParagraph.getHeadingLevel() > 0) {
                expectedParagraph = config.getStyles().get(HEADING_STYLE_NAME + actualParagraph.getHeadingLevel())
                        .getParagraph();
            } else {
                expectedParagraph = config.getStyles().get(BODY_STYLE_NAME).getParagraph();
            }
        } else {
            expectedParagraph = config.getStyles().get(configStyles.get(index)).getParagraph();
        }

        compareParagraph();
    }

    void compareParagraph() {
        Paragraph<String, String> differenceParagraph = new ParagraphDiffer(actualParagraph, expectedParagraph)
                .getParagraphDifference();
        difference.addParagraph(differenceParagraph);

        if (shouldFix) {
            new ParagraphFixer(documentParagraph, actualParagraph, expectedParagraph, differenceParagraph)
                    .fixParagraph();
        }

        int count = 0;
        for (Object o : documentParagraph.getContent()) {
            if (o instanceof R) {
                R r = (R) o;
                if (!StringUtils.isBlank(getText(r))) {
                    ++count;

                    Run<Boolean, Double> actualRun = (Run<Boolean, Double>) actualParagraph.getRuns().get(count - 1);
                    new RunController(index, r, actualRun, differenceParagraph, configStyles,
                            actualParagraph.getHeadingLevel(), config, shouldFix).parse();
                }
            }
        }
    }
}
