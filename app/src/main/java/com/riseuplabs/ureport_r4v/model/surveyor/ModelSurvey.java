package com.riseuplabs.ureport_r4v.model.surveyor;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "table_survey",primaryKeys = {"flow_id"})
public class ModelSurvey {

    public ModelSurvey(@NonNull String flow_id, String flow_type, String org_id) {
        this.flow_id = flow_id;
        this.flow_type = flow_type;
        this.org_id = org_id;
    }

    @NonNull
    public String flow_id;

    public String flow_type;

    public String org_id;

}
