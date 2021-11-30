package com.riseuplabs.ureport_r4v.surveyor.net;


import com.riseuplabs.ureport_r4v.surveyor.SurveyorException;

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
