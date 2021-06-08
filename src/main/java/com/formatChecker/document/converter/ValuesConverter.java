package com.formatChecker.document.converter;

import java.math.BigInteger;

public interface ValuesConverter {
    default Double halfPtToPt(BigInteger halfPt) {
        return (double) (halfPt.floatValue() / 2);
    }

    default Double twipsToCm(BigInteger twips) {
        return (double) (twips.floatValue() / 567);
    }

    default Double absUnitsToCm(BigInteger absUnits) {
        return (double) (absUnits.floatValue() / 20);
    }

    default Double lineSpacingValToCm(BigInteger lineSpacingVal) {
        return (double) (lineSpacingVal.floatValue() / 240);
    }

    default Double pageValToCm(BigInteger pageVal) {
        return round(pageVal.floatValue() * 0.0017638889, 2);
    }

    default Double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}
