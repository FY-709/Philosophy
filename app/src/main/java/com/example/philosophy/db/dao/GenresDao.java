package com.example.philosophy.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.philosophy.db.entity.GenresEntity;

import java.util.List;

@Dao
public interface GenresDao {
    @Insert
    long insert(GenresEntity genre);

    @Update
    int update(GenresEntity genre);

    @Query("DELETE FROM Genres WHERE gNum= :gNum")
    int deleteByNum(int gNum);

    @Query("SELECT * FROM Genres")
    List<GenresEntity> query();

    @Query("SELECT gName FROM Genres WHERE gNum= :gNum")
    String queryNameByNum(int gNum);

    @Query("SELECT gNum FROM Genres WHERE gName= :gName")
    int queryNumByName(String gName);

    @Query("SELECT * FROM Genres WHERE gNum= :gNum")
    GenresEntity queryByNum(int gNum);

    @Query("SELECT * FROM Genres WHERE gName= :gName")
    GenresEntity queryByName(String gName);

    @Query("SELECT * FROM Genres WHERE gName LIKE '%' || :gName|| '%'")
    GenresEntity queryLikeByName(String gName);

    @Query("SELECT * FROM Genres WHERE gName LIKE '%' || :gName|| '%'")
    List<GenresEntity> queryListByName(String gName);

    @Query("SELECT * FROM Genres  WHERE gNum > 2 LIMIT 3 ")
    List<GenresEntity> queryLimit();
}
