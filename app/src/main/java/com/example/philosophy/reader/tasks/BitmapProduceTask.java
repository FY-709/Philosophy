package com.example.philosophy.reader.tasks;

import android.graphics.Bitmap;

import com.example.philosophy.reader.interfaces.ILoadListener;
import com.example.philosophy.reader.interfaces.IPage;
import com.example.philosophy.reader.interfaces.ITxtTask;
import com.example.philosophy.reader.main.TxtReaderContext;
import com.example.philosophy.reader.utils.ELogger;
import com.example.philosophy.reader.utils.TxtBitmapUtil;

/**
 * Created by bifan-wei
 * on 2017/11/27.
 */

public class BitmapProduceTask implements ITxtTask {
    private String tag = "BitmapProduceTask";

    @Override
    public void Run(ILoadListener callBack, TxtReaderContext readerContext) {
        ELogger.log(tag, "produce bitmap");
        callBack.onMessage("start to  produce bitmap");

        int[] rs = readerContext.getPageData().refreshTag;
        IPage[] pages = readerContext.getPageData().getPages();
        Bitmap[] bitmaps = readerContext.getBitmapData().getPages();

        int index = 0;
        for (int neeRefresh : rs) {
            IPage page = pages[index];
            if (neeRefresh == 1) {
                ELogger.log(tag, "page " + index + " neeRefresh");
                Bitmap bitmap;
                if (readerContext.getTxtConfig().VerticalPageMode) {
                    bitmap = TxtBitmapUtil.createVerticalPage(
                            readerContext.getBitmapData().getBgBitmap(),
                            readerContext.getPaintContext(),
                            readerContext.getPageParam(),
                            readerContext.getTxtConfig(), page);
                } else {
                    bitmap = TxtBitmapUtil.createHorizontalPage(
                            readerContext.getBitmapData().getBgBitmap(),
                            readerContext.getPaintContext(),
                            readerContext.getPageParam(),
                            readerContext.getTxtConfig(), page);
                }

                bitmaps[index] = bitmap;
            } else {
                ELogger.log(tag, "page " + index + " no neeRefresh");
                //no neeRefresh ,do not change
            }
            index++;
        }
        ELogger.log(tag, "already done ,call back success");
        callBack.onMessage("already done ,call back success");
        readerContext.setInitDone(true);
        //already done ,call back success
        callBack.onSuccess();
    }
}
