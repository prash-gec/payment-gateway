package com.checkout.utils;

import com.checkout.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonResponse {

    public static ResponseEntity<?> getNotFoundResponse(String message){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), message));
    }
}
