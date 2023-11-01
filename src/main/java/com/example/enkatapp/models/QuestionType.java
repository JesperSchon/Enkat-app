package com.example.enkatapp.models;

public enum QuestionType {
    TEXT("text"),
    CHOICE("choice"),
    SCALE("scale");

    private String value;

    QuestionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static QuestionType fromValue(String value) {
        for (QuestionType type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}

