package com.example.api.model;

import lombok.Data;

@Data
public class VucemResResponseError {
    private int id;
    private String shortDesc;
    private String longDesc;
    private String internalCode;
    private String[] params;
}
