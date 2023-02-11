package com.ist.challenge.bayu.controller;

import com.ist.challenge.bayu.dto.MessageResponse;
import com.ist.challenge.bayu.exception.BadRequestException;
import com.ist.challenge.bayu.exception.ConflictException;
import com.ist.challenge.bayu.exception.ResourceNotFoundException;
import com.ist.challenge.bayu.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {

    // 400
    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<MessageResponse> badRequestException(BadRequestException exception) {
        return new ResponseEntity<>(exception.getMessageResponse(), HttpStatus.BAD_REQUEST);
    }

    // 401
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<MessageResponse> unauthorizedException(UnauthorizedException exception) {
        return new ResponseEntity<>(exception.getMessageResponse(), HttpStatus.UNAUTHORIZED);
    }

    // 404
    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<MessageResponse> resourceNotFoundHandler(ResourceNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessageResponse(), HttpStatus.NOT_FOUND);
    }

    // 409
    @ExceptionHandler(value = ConflictException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ResponseEntity<MessageResponse> conflictException(ConflictException exception) {
        return new ResponseEntity<>(exception.getApiResponse(), HttpStatus.CONFLICT);
    }

}
