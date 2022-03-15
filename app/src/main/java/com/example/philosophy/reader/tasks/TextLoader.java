package com.example.philosophy.reader.tasks;

import com.example.philosophy.reader.interfaces.IChapter;
import com.example.philosophy.reader.interfaces.ILoadListener;
import com.example.philosophy.reader.interfaces.IParagraphData;
import com.example.philosophy.reader.interfaces.ITxtTask;
import com.example.philosophy.reader.main.ParagraphData;
import com.example.philosophy.reader.main.TxtReaderContext;
import com.example.philosophy.reader.utils.ELogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bifan-wei
 * on 2018/1/28.
 */

public class TextLoader {
    private String tag = "FileDataLoadTask";

    public void load(String text, TxtReaderContext readerContext, ILoadListener callBack) {
        IParagraphData paragraphData = new ParagraphData();
        List<IChapter> chapter = new ArrayList<>();
        callBack.onMessage("start read text");
        ELogger.log(tag, "start read text");
        paragraphData.addParagraph(text + "");
        readerContext.setParagraphData(paragraphData);
        readerContext.setChapters(chapter);
        ITxtTask txtTask = new TxtConfigInitTask();
        txtTask.Run(callBack, readerContext);
    }
}
