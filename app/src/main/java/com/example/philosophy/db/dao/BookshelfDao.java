package com.example.philosophy.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.philosophy.db.entity.BooksEntity;
import com.example.philosophy.db.entity.BookshelfEntity;

import java.util.List;

@Dao
public interface BookshelfDao {
    @Insert
    long insert(BookshelfEntity bookshelf);

    @Query("DELETE FROM Bookshelf WHERE sIdentifier = :identifier AND bNum = :bNum")
    int deleteBookshelf(String identifier, int bNum);

    @Query("SELECT * FROM Bookshelf WHERE sIdentifier= :identifier AND bNum = :bNum")
    BookshelfEntity queryBookshelf(String identifier, int bNum);

    @Query("SELECT b.* FROM Books b, Bookshelf s WHERE b.bNum = (SELECT s.bNum WHERE s.sIdentifier = :identifier)")
    List<BooksEntity> queryBooks(String identifier);
}
