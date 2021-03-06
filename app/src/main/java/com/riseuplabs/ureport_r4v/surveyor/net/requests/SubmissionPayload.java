package com.riseuplabs.ureport_r4v.surveyor.net.requests;

import com.riseuplabs.ureport_r4v.utils.surveyor.RawJson;

import java.util.List;

public class SubmissionPayload {
    private final RawJson session;
    private final List<RawJson> modifiers;
    private final List<RawJson> events;

    public SubmissionPayload(RawJson session, List<RawJson> modifiers, List<RawJson> events) {
        this.session = session;
        this.modifiers = modifiers;
        this.events = events;
    }

    public RawJson getSession() {
        return session;
    }

    public List<RawJson> getModifiers() {
        return modifiers;
    }

    public List<RawJson> getEvents() {
        return events;
    }
}
