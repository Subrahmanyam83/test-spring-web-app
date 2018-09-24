package com.hooyu.exercise.exceptions;

/**
 * Created by Subrahmanyam on 24/09/2018.
 */
public class InvalidSearchRequest extends RuntimeException {
    public InvalidSearchRequest(String msg)
    {
        super(msg);
    }
}
