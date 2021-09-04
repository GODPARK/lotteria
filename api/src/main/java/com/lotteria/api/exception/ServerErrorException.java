package com.lotteria.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ServerErrorException extends RuntimeException{
    public ServerErrorException(String msg) {
        super(msg);
    }
}
