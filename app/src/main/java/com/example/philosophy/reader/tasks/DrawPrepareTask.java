package com.example.philosophy.reader.tasks;

import android.content.Context;
import android.graphics.Color;

import com.example.philosophy.reader.interfaces.ILoadListener;
import com.example.philosophy.reader.interfaces.ITxtTask;
import com.example.philosophy.reader.main.PaintContext;
import com.example.philosophy.reader.main.TxtConfig;
import com.example.philosophy.reader.main.TxtReaderContext;
import com.example.philosophy.reader.utils.ELogger;

/**
 * Created by bifan-wei
 * on 2017/11/27.
 */

public class DrawPrepareTask implements ITxtTask {
    private String tag = "DrawPrepareTask";

    @Override
    public void Run(ILoadListener callBack, TxtReaderContext readerContext) {
        callBack.onMessage("start do DrawPrepare");
        ELogger.log(tag, "do DrawPrepare");
        initPainContext(readerContext.context, readerContext.getPaintContext(), readerContext.getTxtConfig());
        readerContext.getPaintContext().textPaint.setColor(Color.WHITE);
        ITxtTask txtTask = new BitmapProduceTask();
        txtTask.Run(callBack, readerContext);
    }

    private void initPainContext(Context context, PaintContext paintContext, TxtConfig txtConfig) {
        TxtConfigInitTask.initPainContext(context, paintContext, txtConfig);
    }
}
