package com.example.philosophy.reader.main;

import android.content.Context;

import com.example.philosophy.reader.bean.TxtFileMsg;
import com.example.philosophy.reader.interfaces.IChapter;
import com.example.philosophy.reader.interfaces.IChapterMatcher;
import com.example.philosophy.reader.interfaces.IPageDataPipeline;
import com.example.philosophy.reader.interfaces.IParagraphData;

import java.util.List;

/*
 * create by bifan-wei
 * 2017-11-13
 */
public class TxtReaderContext {
    private final BitmapData bitmapData = new BitmapData();
    private final PageData pageData = new PageData();
    public Context context;
    private IParagraphData paragraphData;
    private PageParam pageParam;
    private TxtFileMsg fileMsg;
    private List<IChapter> chapters;
    private IPageDataPipeline pageDataPipeline;
    private PaintContext paintContext;
    private TxtConfig txtConfig;
    private Boolean InitDone = false;
    private IChapterMatcher chapterMatcher;

    public TxtReaderContext(Context context) {
        this.context = context;
    }

    public TxtFileMsg getFileMsg() {
        return fileMsg;
    }

    public void setFileMsg(TxtFileMsg fileMsg) {
        this.fileMsg = fileMsg;
    }

    public IParagraphData getParagraphData() {
        return paragraphData;
    }

    public void setParagraphData(IParagraphData paragraphData) {
        this.paragraphData = paragraphData;
    }

    public PageParam getPageParam() {
        return pageParam;
    }

    public void setPageParam(PageParam pageParam) {
        this.pageParam = pageParam;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<IChapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<IChapter> chapters) {
        this.chapters = chapters;
    }

    public IChapterMatcher getChapterMatcher() {
        return chapterMatcher;
    }

    public void setChapterMatcher(IChapterMatcher chapterMatcher) {
        this.chapterMatcher = chapterMatcher;
    }

    public PageData getPageData() {
        return pageData;
    }

    public IPageDataPipeline getPageDataPipeline() {
        if (pageDataPipeline == null) {
            if (getTxtConfig().VerticalPageMode) {
                pageDataPipeline = new VerticalPageDataPipeline(this);
            } else {
                pageDataPipeline = new PageDataPipeline(this);
            }
        }
        return pageDataPipeline;
    }

    public void setPageDataPipeline(IPageDataPipeline pageDataPipeline) {
        this.pageDataPipeline = pageDataPipeline;
    }

    public PaintContext getPaintContext() {
        if (paintContext == null) {
            paintContext = new PaintContext();
        }
        return paintContext;
    }

    public void setPaintContext(PaintContext paintContext) {
        this.paintContext = paintContext;
    }

    public TxtConfig getTxtConfig() {
        if (txtConfig == null) {
            txtConfig = new TxtConfig();
        }
        return txtConfig;
    }

    public void setTxtConfig(TxtConfig txtConfig) {
        this.txtConfig = txtConfig;
    }

    public BitmapData getBitmapData() {
        return bitmapData;
    }

    public Boolean InitDone() {
        return InitDone;
    }

    public void setInitDone(Boolean initDone) {
        InitDone = initDone;
    }

    public void Clear() {
        if (paragraphData != null) {
            paragraphData.Clear();
            paragraphData = null;
        }
        if (paintContext != null) {
            paintContext.onDestroy();
            paintContext = null;
        }
        if (chapters != null) {
            chapters.clear();
            chapters = null;
        }
        bitmapData.onDestroy();
        pageData.onDestroy();
        chapterMatcher = null;

    }

}
