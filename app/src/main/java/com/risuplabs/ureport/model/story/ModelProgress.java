package com.risuplabs.ureport.model.story;

public class ModelProgress {

    public int progress;
    public int max;
    public String status;

    public ModelProgress(int progress, int max, String status) {
        this.progress = progress;
        this.max = max;
        this.status = status;
    }
}
