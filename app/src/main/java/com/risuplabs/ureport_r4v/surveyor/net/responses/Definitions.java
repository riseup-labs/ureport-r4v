package com.risuplabs.ureport_r4v.surveyor.net.responses;


import com.risuplabs.ureport_r4v.utils.surveyor.RawJson;

import java.util.List;

public class Definitions {
    private String version;
    private String site;
    private List<RawJson> flows;

    public String getVersion() {
        return version;
    }

    public String getSite() {
        return site;
    }

    public List<RawJson> getFlows() {
        return flows;
    }
}
