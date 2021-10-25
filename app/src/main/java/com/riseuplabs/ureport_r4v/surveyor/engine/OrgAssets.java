package com.riseuplabs.ureport_r4v.surveyor.engine;

import com.riseuplabs.ureport_r4v.surveyor.data.Flow;
import com.riseuplabs.ureport_r4v.surveyor.net.responses.Boundary;
import com.riseuplabs.ureport_r4v.surveyor.net.responses.Field;
import com.riseuplabs.ureport_r4v.surveyor.net.responses.Group;
import com.riseuplabs.ureport_r4v.utils.surveyor.RawJson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class OrgAssets {
    private final List<FieldAsset> fields;
    private final List<GroupAsset> groups;
    private final List<LocationAsset> locations;
    private final List<RawJson> flows;

    /**
     * Constructs a new set of org assets (fields, groups, locations and flows)
     */
    private OrgAssets(List<FieldAsset> fields, List<GroupAsset> groups, List<LocationAsset> locations, List<RawJson> flows) {
        this.fields = fields;
        this.groups = groups;
        this.locations = locations;
        this.flows = flows;
    }

    /**
     * Constructs a new set of org assets from the data returned from the Temba API
     */
    public static OrgAssets fromTemba(List<Field> fields, List<Group> groups, List<Boundary> boundaries, List<RawJson> flows) {
        List<FieldAsset> fieldAssets = new ArrayList<>(fields.size());
        for (Field field : fields) {
            fieldAssets.add(FieldAsset.fromTemba(field));
        }

        List<GroupAsset> groupAssets = new ArrayList<>(groups.size());
        for (Group group : groups) {
            groupAssets.add(GroupAsset.fromTemba(group));
        }

        List<LocationAsset> locationAssets = new ArrayList<>();
        if (boundaries.size() > 0) {
            LocationAsset location = LocationAsset.fromTemba(boundaries);
            locationAssets = Collections.singletonList(location);
        }

        return new OrgAssets(fieldAssets, groupAssets, locationAssets, flows);
    }

    /**
     * Extract the flow summaries from this set of org assets
     */
    public List<Flow> getFlows() {
        List<Flow> summaries = new ArrayList<>(this.flows.size());
        for (RawJson flow : this.flows) {
            summaries.add(Flow.extract(flow));
        }
        return summaries;
    }
}
