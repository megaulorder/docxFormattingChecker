package com.formatChecker.parserDocx.parser;

import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Body;
import org.docx4j.wml.Document;
import org.docx4j.wml.Jc;
import org.docx4j.wml.P;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DocxParser {
    public static ArrayList<String> getAlignment(String filePath) throws Docx4JException {
        File file = new File(filePath);

        WordprocessingMLPackage wordMLPackage = Docx4J.load(file);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

        Document wmlDocumentEl = documentPart.getJaxbElement();
        Body body = wmlDocumentEl.getBody();
        List<Object> paragraphs = body.getContent();

        ArrayList<String> alignments = new ArrayList<>();
        paragraphs.forEach(
                p -> {
                    Jc alignment = ((P) p).getPPr().getJc();
                    if (alignment == null) {
                        alignments.add("none");

                    } else {
                        alignments.add(alignment.getVal().toString().toLowerCase());
                    }
                }
        );

        return alignments;
    }
}
