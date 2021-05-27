package com.formatChecker.compare.differ;

public interface Differ {
    default String checkStringParameter(String actualParameter, String expectedParameter, String parameterName) {
        if (expectedParameter == null) return null;
        else {
            return actualParameter.equalsIgnoreCase(expectedParameter) ? null :
                    String.format("change %s from %s to %s", parameterName, actualParameter, expectedParameter);
        }
    }

    default String checkStringParameter(String actualParameter, String expectedParameter, String parameterName,
                                        String measurementUnit) {
        if (expectedParameter == null) return null;
        else {
            return actualParameter.equalsIgnoreCase(expectedParameter) ? null :
                    String.format("change %s from %s%s to %s%s", parameterName, actualParameter, measurementUnit,
                            expectedParameter, measurementUnit);
        }
    }

    default String checkBooleanParameter(Boolean actualParameter, Boolean expectedParameter, String parameterName) {
        if (!actualParameter && expectedParameter == null) { return null; }
        else {
            if (actualParameter.equals(expectedParameter)) return null;
            else {
                if (actualParameter) return String.format("change parameter %s to true", parameterName);
                else return String.format("change parameter %s to false", parameterName);
            }
        }
    }
}
