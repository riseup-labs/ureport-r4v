package com.riseuplabs.ureport_r4v.surveyor.engine;

import com.riseuplabs.ureport_r4v.surveyor.net.responses.Group;

public class GroupAsset {
    private final String uuid;
    private final String name;
    private final String query;

    public GroupAsset(String uuid, String name, String query) {
        this.uuid = uuid;
        this.name = name;
        this.query = query;
    }

    public static GroupAsset fromTemba(Group group) {
        return new GroupAsset(group.getUuid(), group.getName(), group.getQuery());
    }
}
