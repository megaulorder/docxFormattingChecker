package com.formatChecker.document.parser.run;

import com.formatChecker.document.converter.Converter;
import org.docx4j.openpackaging.exceptions.Docx4JException;

public interface RunSetProperties extends Converter {
    void setFontFamily() throws Docx4JException;
    void setFontSize();
    void setBold();
    void setItalic();
    void setStrikethrough();
    void setUnderline();
    void setTextColor();
}
