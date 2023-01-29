package com.azamakram.github.springbootjpadocker.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MessageNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MessageNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public MessageNotFoundException(String message) {
        super(message);
    }
}