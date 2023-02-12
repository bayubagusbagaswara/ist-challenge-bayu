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

    // 400
    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<MessageResponse> badRequestException(BadRequestException exception) {
        return new ResponseEntity<>(new MessageResponse(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE, exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // 401
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<MessageResponse> unauthorizedException(UnauthorizedException exception) {
        return new ResponseEntity<>(new MessageResponse(HttpStatus.UNAUTHORIZED.value(), Boolean.FALSE, exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    // 404
    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<MessageResponse> resourceNotFoundHandler(ResourceNotFoundException exception) {
        return new ResponseEntity<>(new MessageResponse(HttpStatus.NOT_FOUND.value(), Boolean.FALSE, exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    // 409
    @ExceptionHandler(value = ConflictException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ResponseEntity<MessageResponse> conflictException(ConflictException exception) {
//        return new ResponseEntity<>(new MessageResponse(HttpStatus.CONTINUE.value(), Boolean.FALSE, exception.getMessage()), HttpStatus.CONFLICT);
        return new ResponseEntity<>(exception.getApiResponse(), HttpStatus.CONFLICT);
    }

}
