package com.formatChecker.comparer.differ;

public interface ParametersChecker {
    default String checkStringParameter(String actualParameter, String expectedParameter, String parameterName) {
        if (expectedParameter == null)
            return null;
        else {
            return actualParameter.equalsIgnoreCase(expectedParameter) ? null :
                    String.format("change %s from %s to %s", parameterName, actualParameter, expectedParameter);
        }
    }

    default String checkDoubleParameter(Double actualParameter, Double expectedParameter, String parameterName,
                                        String measurementUnit) {
        if (expectedParameter == null)
            return null;
        else {
            return actualParameter.equals(expectedParameter) ? null :
                    String.format("change %s from %s%s to %s%s", parameterName, actualParameter, measurementUnit,
                            expectedParameter, measurementUnit);
        }
    }

    default String checkBooleanParameter(Boolean actualParameter, Boolean expectedParameter, String parameterName) {
        if (expectedParameter == null)
            return null;
        else {
            if (actualParameter.equals(expectedParameter))
                return null;
            else {
                if (!actualParameter)
                    return String.format("add %s", parameterName);
                else
                    return String.format("remove %s", parameterName);
            }
        }
    }
}
