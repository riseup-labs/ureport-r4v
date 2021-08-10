package com.risuplabs.ureport_r4v.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.risuplabs.ureport_r4v.model.story.ModelStory;

import java.util.List;

@Dao
public interface StoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStory(ModelStory story);

    @Query("SELECT * FROM table_story WHERE org_id = :org_id ORDER BY id DESC")
    LiveData<List<ModelStory>> getAllStories(int org_id);

    @Query("SELECT COUNT(*) FROM table_story WHERE org_id = :org_id ORDER BY id DESC")
    LiveData<Integer> getStoriesCount(int org_id);


}
