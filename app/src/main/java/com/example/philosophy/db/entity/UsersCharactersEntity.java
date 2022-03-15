package com.example.philosophy.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "UsersCharacters",
        primaryKeys = {"uNum", "cNum"},
        foreignKeys = {
                @ForeignKey(entity = UsersEntity.class,
                        parentColumns = "uNum",
                        childColumns = "uNum",
                        onDelete = CASCADE),
                @ForeignKey(entity = CharactersEntity.class,
                        parentColumns = "cNum",
                        childColumns = "cNum",
                        onDelete = CASCADE)
        },
        indices = {@Index(value = "uNum"), @Index(value = "cNum")})
public class UsersCharactersEntity {
    //关注人物           用户   人物
    //UsersCharacters    uNum  cNum
    public int uNum;
    public int cNum;

    public int getuNum() {
        return uNum;
    }

    public void setuNum(int uNum) {
        this.uNum = uNum;
    }

    public int getcNum() {
        return cNum;
    }

    public void setcNum(int cNum) {
        this.cNum = cNum;
    }
}
