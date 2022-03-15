package com.example.philosophy.reader.main;

import com.example.philosophy.reader.bean.TxtMsg;
import com.example.philosophy.reader.interfaces.ILoadListener;

/**
 * Created by bifan-wei
 * on 2017/12/11.
 */

public class LoadListenerAdapter implements ILoadListener {
    @Override
    public void onSuccess() {
    }

    @Override
    public void onFail(TxtMsg txtMsg) {
    }

    @Override
    public void onMessage(String message) {
    }
}
