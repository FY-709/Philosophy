package com.example.philosophy.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Users")
public class UsersEntity {
    //用户     序号  图片  手机号 密码  昵称   简介
    //Users    uNum  uPic  uTel  uPwd  uName  uProfile
    @PrimaryKey(autoGenerate = true)
    public int uNum;
    public String uPic;
    public String uTel;
    public String uPwd;
    public String uName;
    public String uProfile;

    public int getuNum() {
        return uNum;
    }

    public void setuNum(int uNum) {
        this.uNum = uNum;
    }

    public String getuPic() {
        return uPic;
    }

    public void setuPic(String uPic) {
        this.uPic = uPic;
    }

    public String getuTel() {
        return uTel;
    }

    public void setuTel(String uTel) {
        this.uTel = uTel;
    }

    public String getuPwd() {
        return uPwd;
    }

    public void setuPwd(String uPwd) {
        this.uPwd = uPwd;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuProfile() {
        return uProfile;
    }

    public void setuProfile(String uProfile) {
        this.uProfile = uProfile;
    }
}
