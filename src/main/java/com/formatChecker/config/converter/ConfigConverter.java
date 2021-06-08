package com.formatChecker.config.converter;

import org.docx4j.wml.JcEnumeration;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface ConfigConverter {
    default JcEnumeration convertAlignment(String alignment) {
        switch(alignment) {
            case "center":
                return JcEnumeration.CENTER;
            case "right":
                return JcEnumeration.RIGHT;
            case "justify":
                return JcEnumeration.BOTH;
            default:
                return JcEnumeration.LEFT;
        }
    }

    default BigInteger cmToTwips(Double value) {
        return BigDecimal.valueOf(value * 567).toBigInteger();
    }

    default BigInteger ptToHalfPt(Double value) {
        return BigDecimal.valueOf(value * 2).toBigInteger();
    }

    default BigInteger cmToAbsUnits(Double value) {
        return BigDecimal.valueOf(value * 20).toBigInteger();
    }

    default BigInteger cmToLineSpacing(Double value) {
        return BigDecimal.valueOf(value * 240).toBigInteger();
    }

    default BigInteger cmToPageVal(Double value) {
        return BigDecimal.valueOf(value * 0.0017638889).toBigInteger();
    }
}
