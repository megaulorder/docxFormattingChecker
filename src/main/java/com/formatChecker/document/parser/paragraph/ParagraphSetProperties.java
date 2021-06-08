package com.formatChecker.document.parser.paragraph;

import com.formatChecker.document.converter.ValuesConverter;

public interface ParagraphSetProperties extends ValuesConverter {
    void setAlignment();
    void setFirstLineIndent();
    void setLeftIndent();
    void setRightIndent();
    void setLineSpacing();
    void setSpacingBefore();
    void setSpacingAfter();
}
