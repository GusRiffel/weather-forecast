package com.example.weatherforecast.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ServiceException extends RuntimeException {

    private int statusCode;

    public ServiceException (String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}