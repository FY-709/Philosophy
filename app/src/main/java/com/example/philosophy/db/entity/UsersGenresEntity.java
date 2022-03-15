package com.example.philosophy.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "UsersGenres",
        primaryKeys = {"uNum", "gNum"},
        foreignKeys = {
                @ForeignKey(entity = UsersEntity.class,
                        parentColumns = "uNum",
                        childColumns = "uNum",
                        onDelete = CASCADE),
                @ForeignKey(entity = GenresEntity.class,
                        parentColumns = "gNum",
                        childColumns = "gNum",
                        onDelete = CASCADE)
        },
        indices = {@Index(value = "uNum"), @Index(value = "gNum")})
public class UsersGenresEntity {
    //关注流派       用户   流派
    //UsersGenres    uNum  gNum
    public int uNum;
    public int gNum;

    public int getuNum() {
        return uNum;
    }

    public void setuNum(int uNum) {
        this.uNum = uNum;
    }

    public int getgNum() {
        return gNum;
    }

    public void setgNum(int gNum) {
        this.gNum = gNum;
    }
}
