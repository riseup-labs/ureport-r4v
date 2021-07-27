package com.risuplabs.ureport.surveyor.net;


import com.risuplabs.ureport.surveyor.SurveyorException;

/**
 * Exceptions that come from Temba API requests
 */
public class TembaException extends SurveyorException {
    public TembaException(String message) {
        super(message);
    }

    public TembaException(String message, Exception e) {
        super(message, e);
    }
}
