package com.formatChecker.config.converter;

import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.STPageOrientation;
import org.docx4j.wml.UnderlineEnumeration;

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
        return BigDecimal.valueOf(value / 0.0017638889).toBigInteger();
    }

    default STPageOrientation convertOrientation (String orientation) {
        if (orientation.equals("landscape"))
            return STPageOrientation.LANDSCAPE;
        else
            return STPageOrientation.PORTRAIT;
    }

    default UnderlineEnumeration convertUnderline(String underline) {
        underline = underline.toLowerCase();
        switch (underline) {
            case "single":
                return UnderlineEnumeration.SINGLE;
            case "words":
                return UnderlineEnumeration.WORDS;
            case "double":
                return UnderlineEnumeration.DOUBLE;
            case "thick":
                return UnderlineEnumeration.THICK;
            case "dotted":
                return UnderlineEnumeration.DOTTED;
            case "dottedheavy":
                return UnderlineEnumeration.DOTTED_HEAVY;
            case "dash":
                return UnderlineEnumeration.DASH;
            case "dashedheavy":
                return UnderlineEnumeration.DASHED_HEAVY;
            case "dashlong":
                return UnderlineEnumeration.DASH_LONG;
            case "dashlongheavy":
                return UnderlineEnumeration.DASH_LONG_HEAVY;
            case "dotdash":
                return UnderlineEnumeration.DOT_DASH;
            case "dashdotheavy":
                return UnderlineEnumeration.DASH_DOT_HEAVY;
            case "dotdotdash":
                return UnderlineEnumeration.DOT_DOT_DASH;
            case "dashdotdotheavy":
                return UnderlineEnumeration.DASH_DOT_DOT_HEAVY;
            case "wave":
                return UnderlineEnumeration.WAVE;
            case "waveheavy":
                return UnderlineEnumeration.WAVY_HEAVY;
            case "weavyDouble":
                return UnderlineEnumeration.WAVY_DOUBLE;
            default:
                return null;
        }
    }
}
