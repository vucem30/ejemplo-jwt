package net.ultrasist.api.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import net.ultrasist.api.model.exceptions.V30Error;

public class RestHttpResponseHelper {
    
    private RestHttpResponseHelper(){}

    public static final ResponseEntity<Object> getReturnValue(HttpStatus status, Object response, V30Error error) {
        if(HttpStatus.OK == status) 
            return ResponseEntity
                .status(HttpStatus.OK) // 200
                .header("objType", response.getClass().getCanonicalName()) // add header. "objType" : "net.ultrasist.api.model.User"
                .body(response);
        
        return ResponseEntity
            .status(status) // 200
            .body(error);
    }
}
