package com.example.philosophy.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.philosophy.db.converter.DateConverter;
import com.example.philosophy.db.dao.BooksDao;
import com.example.philosophy.db.dao.BookshelfDao;
import com.example.philosophy.db.dao.CharactersDao;
import com.example.philosophy.db.dao.DiscussionsDao;
import com.example.philosophy.db.dao.GenresDao;
import com.example.philosophy.db.dao.UsersCharactersDao;
import com.example.philosophy.db.dao.UsersDao;
import com.example.philosophy.db.dao.UsersGenresDao;
import com.example.philosophy.db.entity.BooksEntity;
import com.example.philosophy.db.entity.BookshelfEntity;
import com.example.philosophy.db.entity.CharactersEntity;
import com.example.philosophy.db.entity.DiscussionsEntity;
import com.example.philosophy.db.entity.GenresEntity;
import com.example.philosophy.db.entity.UsersCharactersEntity;
import com.example.philosophy.db.entity.UsersEntity;
import com.example.philosophy.db.entity.UsersGenresEntity;

@Database(entities = {BooksEntity.class, BookshelfEntity.class, CharactersEntity.class,
        DiscussionsEntity.class, GenresEntity.class, UsersEntity.class,
        UsersCharactersEntity.class, UsersGenresEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "Philosophy.db";

    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
                }
            }
        }
        return sInstance;
    }

    public abstract BooksDao booksDao();

    public abstract BookshelfDao bookshelfDao();

    public abstract CharactersDao charactersDao();

    public abstract DiscussionsDao discussionsDao();

    public abstract GenresDao genresDao();

    public abstract UsersDao usersDao();

    public abstract UsersCharactersDao usersCharactersDao();

    public abstract UsersGenresDao usersGenresDao();
}
