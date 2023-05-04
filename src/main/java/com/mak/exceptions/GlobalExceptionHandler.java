package com.mak.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex,
            WebRequest request) {

        return new ResponseEntity<>(getErrorMessage(ex, "Server Error", request), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(getErrorMessage(ex, "Validation failed", request), status);

    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(getErrorMessage(ex, "No mapping found", request), status);

    }

    private String getFieldName(ObjectError error) {
        try {
            int index = error.getArguments()[0].toString().lastIndexOf("[");
            if (index > 0) {
                return error.getArguments()[0].toString().substring(index) + " - ";
            }
        } catch (Exception e) {
            // TODO handle exception
        }
        return "";
    }

    private ExceptionResponse getErrorMessage(Exception ex, String message, WebRequest request) {

        boolean isError = false;
        String uri = null;
        List<String> details = new ArrayList<>();

        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException castedEx = (MethodArgumentNotValidException) ex;
            uri = ((ServletWebRequest) request).getRequest().getRequestURI();
            for (ObjectError error : castedEx.getBindingResult().getAllErrors()) {
                details.add(getFieldName(error) + error.getDefaultMessage());
            }

        } else if (ex instanceof NoHandlerFoundException) {
            uri = ((NoHandlerFoundException) ex).getRequestURL();
            details.add(ex.getLocalizedMessage());
        } else {
            isError = true;
            if (request != null) {
                uri = ((ServletWebRequest) request).getRequest().getRequestURI();
            }
            details.add(ex.getLocalizedMessage());

        }

        ExceptionResponse error = new ExceptionResponse(uri, message, details);
        if (isError) {
            log.error(error.toString());
        } else {
            log.warn(error.toString());
        }

        return error;

    }

}
