package com.example.philosophy.db.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.philosophy.db.entity.UsersEntity;

import java.util.List;

@Dao
public interface UsersDao {
    @Insert
    long insert(UsersEntity user);

    @Update
    int update(UsersEntity user);

    @Query("SELECT * FROM Users")
    List<UsersEntity> query();

    @Query("SELECT * FROM Users WHERE uNum= :uNum")
    UsersEntity queryByNum(int uNum);

    @Query("SELECT * FROM Users WHERE uTel= :uTel")
    Cursor queryByTelCursor(String uTel);

    @Query("SELECT uNum FROM Users WHERE uTel= :uTel")
    int queryNumByTel(String uTel);

    @Query("SELECT * FROM Users WHERE uTel= :uTel")
    UsersEntity queryByTel(String uTel);

    @Query("SELECT * FROM Users WHERE uTel= :uTel AND uPwd = :uPwd")
    UsersEntity queryByTelPwd(String uTel, String uPwd);

    @Query("SELECT * FROM Users WHERE uTel= :uTel AND uPwd = :uPwd")
    Cursor queryByTelPwdCursor(String uTel, String uPwd);
}