package com.logesh.app.api.datapoints.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataPointAlreadyExistException extends  RuntimeException{

    public DataPointAlreadyExistException(String message) {
        super(message);
    }
}
