package com.formatChecker.document.parser.run;

import com.formatChecker.document.model.data.DocumentData;
import com.formatChecker.document.parser.ParserType;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.wml.R;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RunParserFactory {

    public static RunPropertiesParser makeParser(
            @NotNull ParserType parserType,
            @Nullable String styleId,
            @Nullable R run) throws Docx4JException {

        DocumentData documentData = DocumentData.getInstance();

        switch (parserType) {
            case DEFAULTS:
                return new RunDefaultsParser(documentData.getDocDefaults(), documentData.getThemePart());
            case STYLE:
                return new RunStyleParser(documentData.getDocDefaults(), documentData.getThemePart(), styleId, documentData.getStyles());
            case DIRECT:
                return new RunDirectParser(documentData.getDocDefaults(), documentData.getThemePart(), styleId, documentData.getStyles(), run);
        }
        return null;
    }
}
