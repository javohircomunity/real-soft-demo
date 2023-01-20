package com.example.demo.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.Serializable;

public interface AbstractRequest extends Serializable {

    @SneakyThrows
    default String toJsonString() {
        return new ObjectMapper().writeValueAsString(this);
    }
}
