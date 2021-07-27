package com.risuplabs.ureport.surveyor.engine;


import com.risuplabs.ureport.surveyor.SurveyorException;

public class EngineException extends SurveyorException {
    public EngineException(Exception e) {
        super(e.getMessage(), e);
    }
}
