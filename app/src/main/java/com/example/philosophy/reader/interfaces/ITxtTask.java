package com.example.philosophy.reader.interfaces;

import com.example.philosophy.reader.main.TxtReaderContext;

/*
 * create by bifan-wei
 * 2017-11-13
 */
public interface ITxtTask {
    void Run(ILoadListener callBack, TxtReaderContext readerContext);
}
