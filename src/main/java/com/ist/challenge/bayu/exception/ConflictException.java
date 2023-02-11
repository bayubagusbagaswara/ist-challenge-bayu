package com.ist.challenge.bayu.exception;

import com.ist.challenge.bayu.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

    private final MessageResponse messageResponse;

    public ConflictException(MessageResponse messageResponse) {
        this.messageResponse = messageResponse;
    }

    public MessageResponse getApiResponse() {
        return messageResponse;
    }
}
