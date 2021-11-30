package com.formatChecker.document.parser.run;

import com.formatChecker.config.model.participants.Run;
import org.docx4j.openpackaging.exceptions.Docx4JException;

public interface RunPropertiesParser {
    Run<Boolean, Double> parseRun() throws Docx4JException;
}
