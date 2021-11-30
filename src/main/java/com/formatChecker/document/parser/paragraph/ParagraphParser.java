package com.formatChecker.document.parser.paragraph;

import com.formatChecker.config.model.participants.Paragraph;
import com.formatChecker.document.converter.ValuesConverter;
import org.docx4j.wml.DocDefaults;
import org.docx4j.wml.Jc;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase.Ind;
import org.docx4j.wml.PPrBase.Spacing;

import java.math.BigInteger;

public abstract class ParagraphParser implements ValuesConverter {
    protected final DocDefaults docDefaults;

    protected Paragraph<Double, Boolean> paragraph;
    protected PPr paragraphProperties;

    public ParagraphParser(DocDefaults docDefaults) {
        this.docDefaults = docDefaults;

        this.paragraph = new Paragraph<>();
    }

    protected String getAlignment(PPr paragraphProperties) {
        if (paragraphProperties == null)
            return null;
        else {
            Jc alignment = paragraphProperties.getJc();
            if (alignment == null)
                return null;
            else
                return convertAlignment(alignment.getVal().toString());
        }
    }

    protected Ind getIndent(PPr paragraphProperties) {
        if (paragraphProperties == null)
            return null;
        else
            return paragraphProperties.getInd();
    }

    protected Double getFirstLineIndent(Ind indent) {
        if (indent == null)
            return null;
        else {
            BigInteger firstLineIndent = indent.getFirstLine();
            return firstLineIndent == null ? null : twipsToCm(firstLineIndent);
        }
    }

    protected Double getLeftIndent(Ind indent) {
        if (indent == null)
            return null;
        else {
            BigInteger leftIndent = indent.getLeft();
            return leftIndent == null ? null : twipsToCm(leftIndent);
        }
    }

    protected Double getRightIndent(Ind indent) {
        if (indent == null)
            return null;
        else {
            BigInteger rightIndent = indent.getRight();
            return rightIndent == null ? null : twipsToCm(rightIndent);
        }
    }

    protected Spacing getSpacing(PPr paragraphProperties) {
        if (paragraphProperties == null)
            return null;
        else
            return paragraphProperties.getSpacing();
    }

    protected Double getLineSpacing(Spacing spacing) {
        if (spacing == null)
            return null;
        else {
            BigInteger lineSpacing = spacing.getLine();
            return lineSpacing == null ? null : lineSpacingValToCm(lineSpacing);
        }
    }

    protected Double getSpacingBefore(Spacing spacing) {
        if (spacing == null)
            return null;
        else {
            BigInteger spacingBefore = spacing.getBefore();
            return spacingBefore == null ? null : absUnitsToCm(spacingBefore);
        }
    }

    protected Double getSpacingAfter(Spacing spacing) {
        if (spacing == null)
            return null;
        else {
            BigInteger spacingAfter = spacing.getAfter();
            return spacingAfter == null ? null : absUnitsToCm(spacingAfter);
        }
    }

    protected Paragraph<Double, Boolean> getDefaultProperties(DocDefaults docDefaults) {
        return new ParagraphDefaultsParser(docDefaults).parseParagraph();
    }
}
