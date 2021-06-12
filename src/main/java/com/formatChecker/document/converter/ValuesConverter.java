package com.formatChecker.document.converter;

import java.math.BigInteger;

public interface ValuesConverter {
    default Double halfPtToPt(BigInteger halfPt) {
        return round(halfPt.floatValue() / 2, 2);
    }

    default Double twipsToCm(BigInteger twips) {
        return round(twips.floatValue() / 567, 2);
    }

    default Double absUnitsToCm(BigInteger absUnits) {
        return round(absUnits.floatValue() / 20, 2);
    }

    default Double lineSpacingValToCm(BigInteger lineSpacingVal) {
        return round(lineSpacingVal.floatValue() / 240, 2);
    }

    default Double pageValToCm(BigInteger pageVal) {
        return round(pageVal.floatValue() * 0.0017638889, 1);
    }

    default String convertAlignment(String alignment) {
        alignment = alignment.toLowerCase();
        if (alignment.equals("both"))
            return "justify";
        else
            return alignment;
    }

    default Double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}
