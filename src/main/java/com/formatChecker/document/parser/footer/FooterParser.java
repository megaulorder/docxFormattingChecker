package com.formatChecker.document.parser.footer;

import com.formatChecker.config.model.participants.Footer;
import org.docx4j.model.structure.HeaderFooterPolicy;
import org.docx4j.openpackaging.parts.WordprocessingML.FooterPart;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FooterParser {
    private static final String DEFAULT_ALIGNMENT = "left";
    private static final String DEFAULT_TYPE = "unknown";
    private static final String PAGE_REGEXP = "\\s+";

    private final String type;
    private final String alignment;
    private final HeaderFooterPolicy headersAndFooters;
    private final Document defaultFooter;
    private final Footer footer;

    public FooterParser(HeaderFooterPolicy headersAndFooters)
            throws ParserConfigurationException, IOException, SAXException {
        this.headersAndFooters = headersAndFooters;
        this.defaultFooter = parseDefaultFooter();

        this.alignment = getAlignment();
        this.type = getType();

        this.footer = parseFooter();
    }

    private Document parseDefaultFooter() throws ParserConfigurationException, IOException, SAXException {
        FooterPart footer = headersAndFooters.getDefaultFooter();
        if (footer == null)
            return null;
        else
            return convertStringToXMLDocument(footer.getXML());
    }

    private Footer parseFooter() {
        Footer footer = new Footer();

        if (alignment == null && type == null)
            return null;

        footer.setAlignment(alignment);
        footer.setType(type);

        return footer;
    }

    private String getAlignment() {
        if (defaultFooter != null) {
            String alignment = DEFAULT_ALIGNMENT;
            Element element;

            element = getElement("w:framePr");
            if (element != null)
                alignment = element.getAttribute("w:xAlign");

            element = getElement("w:ptab");
            if (element != null)
                alignment = element.getAttribute("w:alignment");

            element = getElement("w:jc");
            if (element != null)
                alignment = element.getAttribute("w:val");

            return alignment;
        }
        else
            return null;
    }

    private String getType() {
        if (defaultFooter != null) {
            String type = DEFAULT_TYPE;
            Element element;

            element = getElement("w:instrText");
            if (element != null) {
                type = element.getTextContent().toLowerCase().replaceAll(PAGE_REGEXP, "");

                if (type.contains("page"))
                    return "page";
            }

            element = getElement("w:t");
            if (element != null) {
                String value = element.getTextContent().toLowerCase().replaceAll(PAGE_REGEXP, "");

                if (value.matches("[0-9]+"))
                    return "page";
                else
                    return "text";
            }
        }
        return null;
    }

    private Document convertStringToXMLDocument(String xmlString)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = factory.newDocumentBuilder();
        return db.parse(new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8)));
    }

    private Element getElement(String tag) {
        return (Element) defaultFooter.adoptNode(defaultFooter.getElementsByTagName(tag).item(0));
    }

    public Footer getFooter() {
        return footer;
    }
}
