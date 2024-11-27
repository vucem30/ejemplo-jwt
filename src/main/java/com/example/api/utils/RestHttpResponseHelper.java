package com.example.api.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.api.model.exceptions.V30Error;

public class RestHttpResponseHelper {
    
    private RestHttpResponseHelper(){}

    public static final ResponseEntity<Object> getReturnValue(HttpStatus status, Object response, V30Error error) {
        if(HttpStatus.OK == status) 
            return ResponseEntity
                .status(HttpStatus.OK) // 200
                .header("objType", response.getClass().getCanonicalName()) // add header. "objType" : "com.example.api.model.User"
                .body(response);
        
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR) // 200
            .body(error);
    }
}
