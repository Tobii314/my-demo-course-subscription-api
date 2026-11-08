package com.tobi.mydemocoursesubscriptionapi.controller;

import com.tobi.mydemocoursesubscriptionapi.exception.SubscriptionLimitException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> handleSubscriptionLimit(SubscriptionLimitException exception) {
        System.out.println("There are limit exception " + exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

}
