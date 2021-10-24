package com.risuplabs.ureport_r4v.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.risuplabs.ureport_r4v.model.results.ModelPollCategory;
import com.risuplabs.ureport_r4v.model.results.ModelPolls;

import java.util.List;

@Dao
public interface ResultsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPolls(ModelPolls polls);

    @Query("SELECT DISTINCT category FROM table_polls WHERE org = :org_id ORDER BY id DESC")
    LiveData<List<ModelPollCategory>> getAllPollCategories(int org_id);

    @Query("SELECT DISTINCT COUNT(category) FROM table_polls WHERE org = :org_id ")
    LiveData<Integer> getAllPollCategoriesCount(int org_id);

    @Query("SELECT COUNT(*) FROM table_polls WHERE org = :org_id ")
    LiveData<Integer> getAllPollCount(int org_id);

    @Query("SELECT id,org,title,category_tag,created_on FROM table_polls WHERE category_tag = :tag AND org = :org_id")
    LiveData<List<ModelPolls>> getTitles(String tag,int org_id);

    @Query("SELECT id,org,title,category_tag,created_on,poll_date FROM table_polls WHERE  org = :org_id")
    LiveData<List<ModelPolls>> getResultCategories(int org_id);

    @Query("SELECT * FROM table_polls WHERE id = :id AND org = :org_id")
    LiveData<List<ModelPolls>> getQuestions(int id,int org_id);

    @Query("SELECT * FROM table_polls WHERE org = :org_id ORDER BY id DESC LIMIT 1")
    LiveData<List<ModelPolls>> getLatestQuestions(int org_id);

}
