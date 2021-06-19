package com.formatChecker.controller;

import com.formatChecker.comparer.differ.FooterDiffer;
import com.formatChecker.comparer.model.Difference;
import com.formatChecker.config.model.participants.Footer;
import com.formatChecker.document.model.DocxDocument;
import com.formatChecker.document.parser.footer.FooterParser;
import org.docx4j.model.structure.HeaderFooterPolicy;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class FooterController {
    HeaderFooterPolicy headersAndFooters;
    Footer actualFooter;
    Footer expectedFooter;
    Difference difference;
    DocxDocument docxDocument;

    public FooterController(HeaderFooterPolicy headersAndFooters,
                            Footer expectedFooter,
                            DocxDocument docxDocument,
                            Difference difference) throws ParserConfigurationException, IOException, SAXException {
        this.headersAndFooters = headersAndFooters;
        this.expectedFooter = expectedFooter;
        this.actualFooter = new FooterParser(headersAndFooters).getFooter();
        this.docxDocument = docxDocument;
        this.difference = difference;
    }

    public void parseFooter() {
        docxDocument.setFooter(actualFooter);

        difference.setFooter(new FooterDiffer(actualFooter, expectedFooter).getDifferenceFooter());
    }
}
