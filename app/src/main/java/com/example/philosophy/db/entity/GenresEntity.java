package com.example.philosophy.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Genres")
public class GenresEntity {
    //流派      序号  图片   名字   简介
    //Genres    gNum  gPic  gName  gIntro
    @PrimaryKey(autoGenerate = true)
    public int gNum;
    public String gPic;
    public String gName;
    public String gIntro;

    public int getgNum() {
        return gNum;
    }

    public void setgNum(int gNum) {
        this.gNum = gNum;
    }

    public String getgPic() {
        return gPic;
    }

    public void setgPic(String gPic) {
        this.gPic = gPic;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public String getgIntro() {
        return gIntro;
    }

    public void setgIntro(String gIntro) {
        this.gIntro = gIntro;
    }
}
