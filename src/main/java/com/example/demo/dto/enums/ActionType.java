package com.example.demo.dto.enums;

public enum ActionType {

    IN("In"),
    OUT("Out");

    public final String code;

    ActionType(String code) {
        this.code = code;
    }
}
