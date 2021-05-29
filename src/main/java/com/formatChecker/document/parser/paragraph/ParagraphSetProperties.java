package com.formatChecker.document.parser.paragraph;

import com.formatChecker.document.converter.Converter;

public interface ParagraphSetProperties extends Converter {
    void setAlignment();
    void setFirstLineIndent();
    void setLeftIndent();
    void setRightIndent();
    void setLineSpacing();
    void setSpacingBefore();
    void setSpacingAfter();
}
