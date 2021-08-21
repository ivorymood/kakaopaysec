package com.kpsec.test.advice;

import com.kpsec.test.exception.NotFoundException;
import com.kpsec.test.exception.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> notFoundException(NotFoundException e) {
        Map<String, Object> errorResultMap = new HashMap<>();
        ResponseCode responseCode = e.getResponseCode();
        errorResultMap.put("code", responseCode.getCode());
        errorResultMap.put("메세지", responseCode.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResultMap);
    }
}
