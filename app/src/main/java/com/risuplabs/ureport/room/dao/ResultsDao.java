package com.risuplabs.ureport.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.risuplabs.ureport.model.results.ModelPollCategory;
import com.risuplabs.ureport.model.results.ModelPolls;
import com.risuplabs.ureport.model.results.ModelQuestion;
import com.risuplabs.ureport.model.story.ModelStory;

import java.util.List;

@Dao
public interface ResultsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPolls(ModelPolls polls);

    @Query("SELECT DISTINCT category FROM table_polls WHERE org = :org_id ORDER BY id DESC")
    LiveData<List<ModelPollCategory>> getAllPollCategories(int org_id);

    @Query("SELECT DISTINCT COUNT(category) FROM table_polls WHERE org = :org_id ")
    LiveData<Integer> getAllPollCategoriesCount(int org_id);

    @Query("SELECT id,org,title FROM table_polls WHERE category_tag = :tag AND org = :org_id")
    LiveData<List<ModelPolls>> getTitles(String tag,int org_id);

    @Query("SELECT * FROM table_polls WHERE id = :id AND org = :org_id")
    LiveData<List<ModelPolls>> getQuestions(int id,int org_id);

}