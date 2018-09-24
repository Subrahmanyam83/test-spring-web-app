package com.hooyu.exercise.exceptions;

import com.hooyu.exercise.customers.dao.CustomerNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.jws.WebResult;
import java.util.Optional;

/**
 * Created by Subrahmanyam on 23/09/2018.
 */

@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler{


    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleConflict(
                RuntimeException ex, WebRequest request) {
            String bodyOfResponse = " Invalid Customer! Please go to Login page and enter valid credentials";
            return handleExceptionInternal(ex, bodyOfResponse,
                    new HttpHeaders(), HttpStatus.CONFLICT,request);
        }


    @ExceptionHandler(InvalidSearchRequest.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleInvalidSearchRequest(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = " No records Matching the provided Surname and Postcode!";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT,request);
    }


    @ExceptionHandler(ChargingException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    protected ResponseEntity<Object> handleChargingException(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Credits out of balance. Please topup";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT,request);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    protected ResponseEntity<Object> handleNullPointerException(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Null Pointer Exception raised";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT,request);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    protected ResponseEntity<Object> handleAllExceptions(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Oops! Something went wrong..";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT,request);
    }
}

