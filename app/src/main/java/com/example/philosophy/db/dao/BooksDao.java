package com.example.philosophy.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.philosophy.db.entity.BooksEntity;

import java.util.List;

@Dao
public interface BooksDao {
    @Insert
    long insert(BooksEntity book);

    @Update
    int update(BooksEntity book);

    @Query("DELETE FROM Books WHERE bNum= :bNum")
    int deleteByNum(int bNum);

    @Query("SELECT * FROM Books")
    List<BooksEntity> query();

    @Query("SELECT bNum FROM Books WHERE bName= :bName")
    int queryNumByName(String bName);

    @Query("SELECT * FROM Books WHERE bNum= :bNum")
    BooksEntity queryByNum(int bNum);

    @Query("SELECT * FROM Books WHERE bName= :bName")
    BooksEntity queryByName(String bName);

    @Query("SELECT * FROM Books WHERE bName LIKE '%' || :bName || '%'")
    BooksEntity queryLikeByName(String bName);

    @Query("SELECT * FROM Books WHERE bName LIKE '%' || :bName || '%'")
    List<BooksEntity> queryListByName(String bName);

    @Query("SELECT * FROM Books WHERE cNum= :cNum")
    List<BooksEntity> queryByCnum(int cNum);

    @Query("SELECT * FROM Books WHERE bNum > 2 LIMIT 3 ")
    List<BooksEntity> queryLimit();
}
