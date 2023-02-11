package com.ist.challenge.bayu.exception;

import com.ist.challenge.bayu.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private final MessageResponse messageResponse;

    public BadRequestException(MessageResponse messageResponse) {
        this.messageResponse = messageResponse;
    }

    public BadRequestException(String message, MessageResponse messageResponse) {
        super(message);
        this.messageResponse = messageResponse;
    }

    public MessageResponse getMessageResponse() {
        return messageResponse;
    }
}
