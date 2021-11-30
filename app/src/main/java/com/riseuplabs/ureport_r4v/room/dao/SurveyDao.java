package com.riseuplabs.ureport_r4v.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.riseuplabs.ureport_r4v.model.surveyor.ModelSurvey;

import java.util.List;

@Dao
public interface SurveyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSurvey(ModelSurvey survey);

    @Query("SELECT * FROM table_survey WHERE org_id = :org_id")
    LiveData<List<ModelSurvey>> getAllSurvey(int org_id);

    @Query("SELECT COUNT(*) FROM table_story WHERE org_id = :org_id")
    LiveData<Integer> getSurveyCount(int org_id);


}
