package com.example.philosophy.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.philosophy.db.entity.DiscussionsEntity;

import java.util.List;

@Dao
public interface DiscussionsDao {
    @Insert
    long insert(DiscussionsEntity discussion);

    @Query("SELECT * FROM Discussions WHERE dLocation= :dLocation")
    List<DiscussionsEntity> queryByLocation(String dLocation);
}
