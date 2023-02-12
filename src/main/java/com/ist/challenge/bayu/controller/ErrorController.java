package com.ist.challenge.bayu.controller;

import com.ist.challenge.bayu.dto.ApiResponse;
import com.ist.challenge.bayu.dto.MessageResponse;
import com.ist.challenge.bayu.exception.BadRequestException;
import com.ist.challenge.bayu.exception.ConflictException;
import com.ist.challenge.bayu.exception.ResourceNotFoundException;
import com.ist.challenge.bayu.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE, "Bad Request Validation", errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<MessageResponse> badRequestException(BadRequestException exception) {
        MessageResponse messageResponse = MessageResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .success(Boolean.FALSE)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<MessageResponse> unauthorizedException(UnauthorizedException exception) {
        MessageResponse messageResponse = MessageResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .success(Boolean.FALSE)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(messageResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<MessageResponse> resourceNotFoundHandler(ResourceNotFoundException exception) {
        MessageResponse messageResponse = MessageResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .success(Boolean.FALSE)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(messageResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ConflictException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ResponseEntity<MessageResponse> conflictException(ConflictException exception) {
        MessageResponse messageResponse = MessageResponse.builder()
                .code(HttpStatus.CONFLICT.value())
                .success(Boolean.FALSE)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(messageResponse, HttpStatus.CONFLICT);
    }

}
