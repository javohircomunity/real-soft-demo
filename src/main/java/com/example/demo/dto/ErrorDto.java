package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Getter
public class ErrorDto {

    private final String code;

    private final List<Serializable> params;
}
