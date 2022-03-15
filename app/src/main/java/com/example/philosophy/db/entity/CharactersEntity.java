package com.example.philosophy.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Characters",
        foreignKeys = {
                @ForeignKey(entity = GenresEntity.class,
                        parentColumns = "gNum",
                        childColumns = "gNum",
                        onDelete = CASCADE)},
        indices = {@Index(value = "gNum")})
public class CharactersEntity {
    //人物          序号   图片  姓名   流派  人物简介  人物生平  哲学思想   个人著作    后世影响
    //Characters    cNum  cPic  cName  gNum  cIntro    cLife    cThoughts  cWritings  cInfluence
    @PrimaryKey(autoGenerate = true)
    public int cNum;
    public String cPic;
    public String cName;
    public int gNum;
    public String cIntro;
    public String cLife;
    public String cThoughts;
    public String cWritings;
    public String cInfluence;

    public int getcNum() {
        return cNum;
    }

    public void setcNum(int cNum) {
        this.cNum = cNum;
    }

    public String getcPic() {
        return cPic;
    }

    public void setcPic(String cPic) {
        this.cPic = cPic;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public int getgNum() {
        return gNum;
    }

    public void setgNum(int gNum) {
        this.gNum = gNum;
    }

    public String getcIntro() {
        return cIntro;
    }

    public void setcIntro(String cIntro) {
        this.cIntro = cIntro;
    }

    public String getcLife() {
        return cLife;
    }

    public void setcLife(String cLife) {
        this.cLife = cLife;
    }

    public String getcThoughts() {
        return cThoughts;
    }

    public void setcThoughts(String cThoughts) {
        this.cThoughts = cThoughts;
    }

    public String getcWritings() {
        return cWritings;
    }

    public void setcWritings(String cWritings) {
        this.cWritings = cWritings;
    }

    public String getcInfluence() {
        return cInfluence;
    }

    public void setcInfluence(String cInfluence) {
        this.cInfluence = cInfluence;
    }
}
