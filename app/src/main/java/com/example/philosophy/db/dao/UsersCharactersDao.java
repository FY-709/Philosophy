package com.example.philosophy.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.philosophy.db.entity.CharactersEntity;
import com.example.philosophy.db.entity.UsersCharactersEntity;

import java.util.List;

@Dao
public interface UsersCharactersDao {
    @Insert
    long insert(UsersCharactersEntity usersCharacters);

    @Query("DELETE FROM UsersCharacters WHERE uNum = :uNum AND cNum = :cNum")
    int deleteUsersCharacters(int uNum, int cNum);

    @Query("SELECT * FROM UsersCharacters WHERE uNum = :uNum AND cNum = :cNum")
    UsersCharactersEntity queryUsersCharacters(int uNum, int cNum);

    @Query("SELECT c.* FROM Characters c, UsersCharacters uc WHERE c.cNum = (SELECT uc.cNum WHERE uc.uNum = :uNum)")
    List<CharactersEntity> queryFollowedCharacters(int uNum);
}
