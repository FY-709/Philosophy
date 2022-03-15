package com.example.philosophy.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Discussions",
        foreignKeys = {
                @ForeignKey(entity = UsersEntity.class,
                        parentColumns = "uNum",
                        childColumns = "uNum",
                        onDelete = CASCADE)
        },
        indices = {@Index(value = "uNum")})
public class DiscussionsEntity {
    //讨论           序号  用户   位置       时间   内容
    //Discussions    dNum  uNum  dLocation  dTime  dContent
    @PrimaryKey(autoGenerate = true)
    public int dNum;
    public int uNum;
    public String dLocation;
    public Date dTime;
    public String dContent;

    public int getdNum() {
        return dNum;
    }

    public void setdNum(int dNum) {
        this.dNum = dNum;
    }

    public int getuNum() {
        return uNum;
    }

    public void setuNum(int uNum) {
        this.uNum = uNum;
    }

    public String getdLocation() {
        return dLocation;
    }

    public void setdLocation(String dLocation) {
        this.dLocation = dLocation;
    }

    public Date getdTime() {
        return dTime;
    }

    public void setdTime(Date dTime) {
        this.dTime = dTime;
    }

    public String getdContent() {
        return dContent;
    }

    public void setdContent(String dContent) {
        this.dContent = dContent;
    }
}
