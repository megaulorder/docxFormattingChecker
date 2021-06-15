package com.formatChecker.document.parser.headings;

import com.formatChecker.document.model.Heading;
import org.docx4j.jaxb.XPathBinderAssociationIsPartialException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.P;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

public class HeadingsParser {
    private final static String TOC_XPATH = "//w:sdtContent/w:p[w:hyperlink[starts-with(@w:anchor, '_Toc')]]";
    private final static String TOC_ELEMENT_XPATH = "//w:p[w:bookmarkStart[starts-with(@w:name,'_Toc')]]";

    WordprocessingMLPackage wordMLPackage;

    List<Heading> headings;

    public HeadingsParser(WordprocessingMLPackage wordMLPackage) throws JAXBException, XPathBinderAssociationIsPartialException {
        this.wordMLPackage = wordMLPackage;
        this.headings = parseHeadings();
    }

    List<Heading> parseHeadings() throws JAXBException, XPathBinderAssociationIsPartialException {
        List<Object> TOCObjects = wordMLPackage.getMainDocumentPart().getJAXBNodesViaXPath(TOC_XPATH, false);
        List<Object> TOCElementObjects = wordMLPackage.getMainDocumentPart().getJAXBNodesViaXPath(TOC_ELEMENT_XPATH, false);

        List<Heading> headings = new ArrayList<>();

        for (int i = 0; i < TOCObjects.size(); ++i) {
            Heading heading = new Heading();
            heading.setLevel(Integer.parseInt(((P) TOCObjects.get(i)).getPPr().getPStyle().getVal()) / 10);
            heading.setText(TOCElementObjects.get(i).toString());
            headings.add(heading);
        }

        return headings;
    }

    public List<Heading> getHeadings() {
        return headings;
    }
}
