package com.example.philosophy.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.philosophy.db.entity.GenresEntity;
import com.example.philosophy.db.entity.UsersGenresEntity;

import java.util.List;

@Dao
public interface UsersGenresDao {
    @Insert
    long insert(UsersGenresEntity usersGenres);

    @Query("DELETE FROM UsersGenres WHERE uNum = :uNum AND gNum = :gNum")
    int deleteUsersGenres(int uNum, int gNum);

    @Query("SELECT * FROM UsersGenres WHERE uNum = :uNum AND gNum = :gNum")
    UsersGenresEntity queryUsersGenres(int uNum, int gNum);

    @Query("SELECT g.* FROM Genres g, UsersGenres ug WHERE g.gNum = (SELECT ug.gNum WHERE ug.uNum = :uNum)")
    List<GenresEntity> queryFollowedGenres(int uNum);
}
