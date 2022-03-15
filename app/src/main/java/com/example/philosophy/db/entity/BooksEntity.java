package com.example.philosophy.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Books",
        foreignKeys = {
                @ForeignKey(entity = CharactersEntity.class,
                        parentColumns = "cNum",
                        childColumns = "cNum",
                        onDelete = CASCADE)
        },
        indices = {@Index(value = "cNum")})
public class BooksEntity {
    //书籍     序号  图片  名字    作者  简介    内容
    //Books    bNum  bPic  bName  cNum  bIntro  bContent
    @PrimaryKey(autoGenerate = true)
    public int bNum;
    public String bPic;
    public String bName;
    public int cNum;
    public String bIntro;
    public String bContent;

    public int getbNum() {
        return bNum;
    }

    public void setbNum(int bNum) {
        this.bNum = bNum;
    }

    public String getbPic() {
        return bPic;
    }

    public void setbPic(String bPic) {
        this.bPic = bPic;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public int getcNum() {
        return cNum;
    }

    public void setcNum(int cNum) {
        this.cNum = cNum;
    }

    public String getbIntro() {
        return bIntro;
    }

    public void setbIntro(String bIntro) {
        this.bIntro = bIntro;
    }

    public String getbContent() {
        return bContent;
    }

    public void setbContent(String bContent) {
        this.bContent = bContent;
    }
}
