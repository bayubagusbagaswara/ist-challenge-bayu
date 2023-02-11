package com.ist.challenge.bayu.exception;

import com.ist.challenge.bayu.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private final MessageResponse messageResponse;


    public ResourceNotFoundException(MessageResponse messageResponse) {
        this.messageResponse = messageResponse;
    }

    public ResourceNotFoundException(String message, MessageResponse messageResponse) {
        super(message);
        this.messageResponse = messageResponse;
    }

    public ResourceNotFoundException(String message, Throwable cause, MessageResponse messageResponse) {
        super(message, cause);
        this.messageResponse = messageResponse;
    }

    public MessageResponse getMessageResponse() {
        return messageResponse;
    }
}
