package com.example.philosophy.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.philosophy.db.entity.CharactersEntity;

import java.util.List;

@Dao
public interface CharactersDao {
    @Insert
    long insert(CharactersEntity character);

    @Update
    int update(CharactersEntity character);

    @Query("DELETE FROM Characters WHERE cNum= :cNum")
    int deleteByNum(int cNum);

    @Query("SELECT * FROM Characters")
    List<CharactersEntity> query();

    @Query("SELECT cNum FROM Characters WHERE cName = :cName")
    int queryNumByName(String cName);

    @Query("SELECT cName FROM Characters WHERE cNum = :cNum")
    String queryNameByNum(int cNum);

    @Query("SELECT * FROM Characters WHERE cNum = :cNum")
    CharactersEntity queryByNum(int cNum);

    @Query("SELECT * FROM Characters WHERE cName = :cName")
    CharactersEntity queryByName(String cName);

    @Query("SELECT * FROM Characters WHERE cName LIKE '%' || :cName|| '%'")
    CharactersEntity queryLikeByName(String cName);

    @Query("SELECT * FROM Characters WHERE cName LIKE '%' || :cName|| '%'")
    List<CharactersEntity> queryListByName(String cName);

    @Query("SELECT * FROM Characters WHERE gNum = :gNum LIMIT 1")
    CharactersEntity queryCharacterByGenre(int gNum);

    @Query("SELECT * FROM Characters WHERE gNum = :gNum")
    List<CharactersEntity> queryByGenre(int gNum);

    @Query("SELECT * FROM Characters WHERE cNum > 2 LIMIT 3")
    List<CharactersEntity> queryLimit();
}
