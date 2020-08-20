package com.logesh.app.api.datapoints.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectIdException(DataPointNotFoundException ex, WebRequest request) {

        DataPointNotFoundExceptionResponse exceptionResponse = new DataPointNotFoundExceptionResponse(ex.getMessage());

        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectIdException(DataPointAlreadyExistException ex, WebRequest request) {

        DataPointAlreadyExistExceptionResponse exceptionResponse = new DataPointAlreadyExistExceptionResponse(ex.getMessage());

        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);

    }
}
