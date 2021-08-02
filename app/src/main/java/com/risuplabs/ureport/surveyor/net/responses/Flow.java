package com.risuplabs.ureport.surveyor.net.responses;

import java.util.List;

public class Flow {

    private boolean isSelected;

    private String uuid;
    private String name;
    private String type;
    private boolean archived;
    private int expires;
    private List<Labels> labels;

    public Flow(){}

    public Flow(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isArchived() {
        return archived;
    }

    public int getExpires() {
        return expires;
    }

    public List<Labels> getLabels() {
        return labels;
    }

    public class Labels{
        public String uuid;
        public String name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
