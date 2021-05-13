package com.formatChecker.document.converter;

import java.math.BigInteger;

public interface Converter {
    default String halfPtToPt(BigInteger halfPt) {
        return Float.toString(halfPt.floatValue() / 2);
    }

    default String twipsToCm(BigInteger twips) {
        return Float.toString(twips.floatValue() / 567);
    }

    default String absUnitsToCm(BigInteger absUnits) {
        return Float.toString(absUnits.floatValue() / 20);
    }

    default String lineSpacingValToCm(BigInteger lineSpacingVal) {
        return Float.toString(lineSpacingVal.floatValue() / 240);
    }

    default Double pageValToCm(BigInteger pageVal) {
        return round(pageVal.floatValue() * 0.0017638889, 2);
    }

    default Double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}
