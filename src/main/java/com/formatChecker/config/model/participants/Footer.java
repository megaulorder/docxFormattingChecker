package com.formatChecker.config.model.participants;

public class Footer {
    String type;
    String alignment;

    Boolean isPresent;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public Boolean getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(Boolean present) {
        isPresent = present;
    }
}
