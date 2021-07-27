package com.risuplabs.ureport.surveyor.net.responses;


import com.risuplabs.ureport.utils.surveyor.RawJson;

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
