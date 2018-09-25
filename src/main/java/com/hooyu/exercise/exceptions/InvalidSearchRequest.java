package com.hooyu.exercise.exceptions;

public class InvalidSearchRequest extends RuntimeException {
    public InvalidSearchRequest(String msg)
    {
        super(msg);
    }
}
