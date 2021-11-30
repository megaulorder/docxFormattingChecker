package com.formatChecker.document.parser.paragraph;

import com.formatChecker.config.model.participants.Paragraph;
import org.docx4j.openpackaging.exceptions.Docx4JException;

public interface ParagraphPropertiesParser {
    Paragraph<Double, Boolean> parseParagraph() throws Docx4JException;
}
