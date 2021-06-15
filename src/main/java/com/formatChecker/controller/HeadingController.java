package com.formatChecker.controller;

import com.formatChecker.comparer.differ.HeadingDiffer;
import com.formatChecker.comparer.model.Difference;
import com.formatChecker.document.model.DocxDocument;
import com.formatChecker.document.model.Heading;
import com.formatChecker.document.parser.headings.HeadingsParser;
import org.docx4j.jaxb.XPathBinderAssociationIsPartialException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import javax.xml.bind.JAXBException;
import java.util.List;

public class HeadingController {
    WordprocessingMLPackage wordMLPackage;

    List<Heading> headings, expectedHeadings;

    Difference difference;
    DocxDocument docxDocument;

    public HeadingController(WordprocessingMLPackage wordMLPackage, List<Heading> expectedHeadings,
                             Difference difference, DocxDocument docxDocument) throws JAXBException, XPathBinderAssociationIsPartialException {
        this.wordMLPackage = wordMLPackage;
        this.headings = parseHeadings();
        this.expectedHeadings = expectedHeadings;
        this.difference = difference;
        this.docxDocument = docxDocument;
    }

     List<Heading> parseHeadings() throws JAXBException, XPathBinderAssociationIsPartialException {
        return new HeadingsParser(wordMLPackage).getHeadings();
    }

    void compareHeadings() {
        difference.setHeadings(new HeadingDiffer(expectedHeadings, headings).getHeadingsDifference());
    }

    public List<Heading> getHeadings() {
        return headings;
    }
}
