package com.riseuplabs.ureport_r4v.surveyor;

public class SurveyorException extends Exception {
    public SurveyorException(String message) {
        super(message);
    }

    public SurveyorException(String message, Exception e) {
        super(message, e);
    }
}
