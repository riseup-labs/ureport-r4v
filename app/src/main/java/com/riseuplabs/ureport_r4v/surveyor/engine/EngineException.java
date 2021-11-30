package com.riseuplabs.ureport_r4v.surveyor.engine;


import com.riseuplabs.ureport_r4v.surveyor.SurveyorException;

public class EngineException extends SurveyorException {
    public EngineException(Exception e) {
        super(e.getMessage(), e);
    }
}
