package com.example.api.model;

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VucemRestResponse {
    private static ObjectMapper om = new ObjectMapper();
    private HttpStatus httpStatus;
    private String jsonResponse;

    public VucemRestResponse(){}

    public String getJsonResponse() {
        return jsonResponse;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    } 
    public static VucemRestResponse createVucemRestResponse(String data) {
        try {
            return om.readValue(data, VucemRestResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
    public VucemRestResponse(HttpStatus httpStatus, Object response){
        this.httpStatus = httpStatus;
        String target = convertObjectToString(response);
        if(target==null) {
            VucemResResponseError error = new VucemResResponseError();
            String converted = convertObjectToString(error);
            this.jsonResponse = toBase64(converted);
        } else {
            this.jsonResponse = toBase64(target);
        }
    }    
    public static VucemResResponseError getError(String result) {
        try {
            return om.readValue(fromBase64(result), VucemResResponseError.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }     
    // solo para pruebas:  
    public static <T> T convertStringToObject(String result, Class<T> tipo) { 
        try {
            return om.readValue(fromBase64(result), tipo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }    
    public boolean hasErrors() {
        return !(HttpStatus.ACCEPTED.equals(this.httpStatus) || HttpStatus.OK.equals(this.httpStatus));
    }    
    private String convertObjectToString(Object response) {
        try {
            return om.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }    
    private static String toBase64(String jsonResponse) {
        return new String(java.util.Base64.getEncoder().encode(jsonResponse.getBytes()));
    }
    private static String fromBase64(String codedJsonString) {
        return new String(java.util.Base64.getDecoder().decode(codedJsonString.getBytes()));
    }
}
