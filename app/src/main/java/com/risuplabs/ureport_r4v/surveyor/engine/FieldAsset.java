package com.risuplabs.ureport_r4v.surveyor.engine;

import com.risuplabs.ureport_r4v.surveyor.net.responses.Field;

public class FieldAsset {
    private final String key;
    private final String name;
    private final String type;

    public FieldAsset(String key, String name, String type) {
        this.key = key;
        this.name = name;
        this.type = type;
    }

    public static FieldAsset fromTemba(Field field) {
        String type = field.getValueType().equals("numeric") ? "number" : field.getValueType();
        return new FieldAsset(field.getKey(), field.getLabel(), type);
    }
}
