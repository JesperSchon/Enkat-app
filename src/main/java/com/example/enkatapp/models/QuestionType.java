package com.example.enkatapp.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum QuestionType {
    TEXT("text"),
    CHOICE("choice"),
    SCALE("scale");

    private final String value;

    QuestionType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }


    public static QuestionType fromValue(String value) {
        for (QuestionType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}


