package com.example.philosophy.reader.interfaces;


import com.example.philosophy.reader.bean.TxtMsg;

/*
 * create by bifan-wei
 * 2017-11-13
 */
public interface ILoadListener {
    void onSuccess();

    void onFail(TxtMsg txtMsg);

    void onMessage(String message);
}
