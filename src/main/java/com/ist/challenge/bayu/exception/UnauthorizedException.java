package com.ist.challenge.bayu.exception;

import com.ist.challenge.bayu.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    private final MessageResponse messageResponse;

    public UnauthorizedException(MessageResponse messageResponse) {
        this.messageResponse = messageResponse;
    }

    public UnauthorizedException(String message, MessageResponse messageResponse) {
        super(message);
        this.messageResponse = messageResponse;
    }

    public MessageResponse getMessageResponse() {
        return messageResponse;
    }
}
