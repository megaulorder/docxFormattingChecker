package com.formatChecker.document.parser.paragraph;

import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.document.converter.Converter;
import org.docx4j.wml.DocDefaults;
import org.docx4j.wml.Jc;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase.Spacing;
import org.docx4j.wml.PPrBase.Ind;

import java.math.BigInteger;

public interface ParagraphParser extends Converter {
    default String getAlignment(PPr paragraphProperties) {
        if (paragraphProperties == null) { return null; }
        else {
            Jc alignment = paragraphProperties.getJc();
            return alignment == null ? null : alignment.getVal().toString().toLowerCase();
        }
    }

    default Ind getIndent(PPr paragraphProperties) {
        if (paragraphProperties == null) { return null; }
        else { return paragraphProperties.getInd(); }
    }

    default String getFirstLineIndent(Ind indent) {
        if (indent == null) { return null; }
        else {
            BigInteger firstLineIndent = indent.getFirstLine();
            return firstLineIndent == null ? null : twipsToCm(firstLineIndent);
        }
    }

    default String getLeftIndent(Ind indent) {
        if (indent == null) { return null; }
        else {
            BigInteger leftIndent = indent.getLeft();
            return leftIndent == null ? null : twipsToCm(leftIndent);
        }
    }

    default String getRightIndent(Ind indent) {
        if (indent == null) { return null; }
        else {
            BigInteger rightIndent = indent.getRight();
            return rightIndent == null ? null : twipsToCm(rightIndent);
        }
    }

    default Spacing getSpacing(PPr paragraphProperties) {
        if (paragraphProperties == null) { return null; }
        else { return paragraphProperties.getSpacing(); }
    }

    default String getLineSpacing(Spacing spacing) {
        if (spacing == null) { return null; }
        else {
            BigInteger lineSpacing = spacing.getLine();
            return lineSpacing == null ? null : lineSpacingValToCm(lineSpacing);
        }
    }

    default String getSpacingBefore(Spacing spacing) {
        if (spacing == null) { return null; }
        else {
            BigInteger spacingBefore = spacing.getBefore();
            return spacingBefore == null ? null : absUnitsToCm(spacingBefore);
        }
    }

    default String getSpacingAfter(Spacing spacing) {
        if (spacing == null) { return null; }
        else {
            BigInteger spacingAfter = spacing.getAfter();
            return spacingAfter == null ? null : absUnitsToCm(spacingAfter);
        }
    }

    default Paragraph getDefaultProperties(DocDefaults docDefaults) {
        return new ParagraphDefaultsParser().parseParagraph(docDefaults);
    }
}
