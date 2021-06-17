package com.formatChecker.document.parser.styles;

import org.docx4j.wml.Style;
import org.docx4j.wml.Style.BasedOn;
import org.docx4j.wml.Styles;

import java.util.Optional;

public interface StyleHierarchy {
    default Optional<Style> getStyleById(String styleId, Styles styles) {
        return styles.getStyle().stream().filter(s -> s.getStyleId().equals(styleId)).findFirst();
    }

    default Optional<Style> getParentStyle(Optional<Style> style, Styles styles) {
        if (!style.isPresent())
            return null;
        else {
            BasedOn parentStyle = style.get().getBasedOn();
            return parentStyle == null ? Optional.empty() :
                    getStyleById(style.get().getBasedOn().getVal(), styles);
        }

    }
}
