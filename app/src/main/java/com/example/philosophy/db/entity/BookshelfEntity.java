package com.example.philosophy.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Bookshelf",
        foreignKeys = {
                @ForeignKey(entity = BooksEntity.class,
                        parentColumns = "bNum",
                        childColumns = "bNum",
                        onDelete = CASCADE)
        },
        indices = {@Index(value = "bNum")})
public class BookshelfEntity {
    //书架         序号   用户识别码   书籍  书籍哈希码  章节      进度
    //Bookshelf    sNum  sIdentifier  bNum  bHashName  sChapter  sProgress
    @PrimaryKey(autoGenerate = true)
    public int sNum;
    public String sIdentifier;
    public int bNum;
    public String bHashName;
    public int sChapter;
    public int sProgress;

    public int getsNum() {
        return sNum;
    }

    public void setsNum(int sNum) {
        this.sNum = sNum;
    }

    public String getsIdentifier() {
        return sIdentifier;
    }

    public void setsIdentifier(String sIdentifier) {
        this.sIdentifier = sIdentifier;
    }

    public int getbNum() {
        return bNum;
    }

    public void setbNum(int bNum) {
        this.bNum = bNum;
    }

    public String getbHashName() {
        return bHashName;
    }

    public void setbHashName(String bHashName) {
        this.bHashName = bHashName;
    }

    public int getsChapter() {
        return sChapter;
    }

    public void setsChapter(int sChapter) {
        this.sChapter = sChapter;
    }

    public int getsProgress() {
        return sProgress;
    }

    public void setsProgress(int sProgress) {
        this.sProgress = sProgress;
    }
}
