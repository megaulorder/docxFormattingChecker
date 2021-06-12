package com.formatChecker.controller;

import org.docx4j.wml.R;
import org.docx4j.wml.Text;

import javax.xml.bind.JAXBElement;
import java.util.List;

public interface RunHelper {
    default String getText(R r) {
        List<Object> contents = r.getContent();
        if (contents.size() == 0)
            return "";
        else {
            for (Object content : contents) {
                if (content instanceof JAXBElement) {
                    JAXBElement obj = ((JAXBElement) content);
                    if (obj.getValue() instanceof Text)
                        return ((Text) obj.getValue()).getValue();
                }
            }
        }
        return "";
    }
}
