package com.example.philosophy.reader.main;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.philosophy.reader.bean.FileReadRecordBean;
import com.example.philosophy.reader.bean.TxtChar;
import com.example.philosophy.reader.bean.TxtFileMsg;
import com.example.philosophy.reader.interfaces.IChapter;
import com.example.philosophy.reader.interfaces.ILoadListener;
import com.example.philosophy.reader.interfaces.IPage;
import com.example.philosophy.reader.interfaces.IReaderViewDrawer;
import com.example.philosophy.reader.interfaces.ITextSelectListener;
import com.example.philosophy.reader.interfaces.ITxtTask;
import com.example.philosophy.reader.tasks.DrawPrepareTask;
import com.example.philosophy.reader.tasks.TxtConfigInitTask;
import com.example.philosophy.reader.tasks.TxtPageLoadTask;
import com.example.philosophy.reader.utils.ELogger;
import com.example.philosophy.reader.utils.FileHashUtil;
import com.example.philosophy.reader.utils.TxtBitmapUtil;

import java.io.File;
import java.util.List;

import io.reactivex.annotations.Nullable;

/**
 * Created by bifa-wei
 * on 2017/11/28.
 */

public class TxtReaderView extends TxtReaderBaseView {
    private String tag = "TxtReaderView";
    private IReaderViewDrawer drawer = null;
    private ITextSelectListener textSelectListener;
    private ILoadListener actionLoadListener = new LoadListenerAdapter() {
        @Override
        public void onSuccess() {
            checkMoveState();
            postInvalidate();
            post(new Runnable() {
                @Override
                public void run() {
                    onPageProgress(readerContext.getPageData().MidPage());
                }
            });

        }
    };

    public TxtReaderView(Context context) {
        super(context);
    }

    public TxtReaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void drawLineText(Canvas canvas) {
        if (isPagePre() || isPageNext()) {
            if (isPagePre()) {
                //???????????????????????????????????????
                if (isFirstPage()) {
                    if (getTopPage() != null) {
                        canvas.drawBitmap(getTopPage(), 0, 0, null);
                    }
                } else {
                    //???????????????
                    if (getTopPage() != null) {
                        drawPagePreTopPage(canvas);
                    }
                    //???????????????
                    if (getBottomPage() != null) {
                        drawPagePreBottomPage(canvas);
                    }
                    //???????????????
                    drawPagePrePageShadow(canvas);
                }
            } else {
                if (getTopPage() != null) {
                    drawPageNextTopPage(canvas);
                }
                if (getBottomPage() != null) {
                    drawPageNextBottomPage(canvas);
                }
                drawPageNextPageShadow(canvas);
            }
        } else {
            //??????????????????????????????
            if (getTopPage() != null) {
                canvas.drawBitmap(getTopPage(), 0, 0, null);
            }
        }

    }

    private void drawPageNextPageShadow(Canvas canvas) {
        getDrawer().drawPageNextPageShadow(canvas);

    }

    private void drawPageNextBottomPage(Canvas canvas) {
        //??????????????????????????????????????????
        getDrawer().drawPageNextBottomPage(canvas);


    }

    private void drawPageNextTopPage(Canvas canvas) {
        //??????????????????????????????????????????
        getDrawer().drawPageNextTopPage(canvas);

    }

    private void drawPagePrePageShadow(Canvas canvas) {
        //????????????????????????????????????
        getDrawer().drawPagePrePageShadow(canvas);

    }

    private void drawPagePreBottomPage(Canvas canvas) {
        //??????????????????????????????????????????
        getDrawer().drawPagePreBottomPage(canvas);

    }

    private void drawPagePreTopPage(Canvas canvas) {
        //??????????????????????????????????????????
        getDrawer().drawPagePreTopPage(canvas);
    }

    @Override
    protected void startPageStateBackAnimation() {
        getDrawer().startPageStateBackAnimation();
    }

    @Override
    protected void startPageNextAnimation() {
        getDrawer().startPageNextAnimation();
    }

    @Override
    protected void startPagePreAnimation() {
        getDrawer().startPagePreAnimation();
    }

    @Override
    protected void onPageMove(MotionEvent event) {

        mTouch.x = event.getX();
        mTouch.y = event.getY();


        checkMoveState();

        if (getMoveDistance() > 0 && isFirstPage()) {
            ELogger.log(tag, "???????????????");
            return;
        }

        if (getMoveDistance() < 0 && isLastPage()) {
            ELogger.log(tag, "??????????????????");
            return;
        }
        invalidate();
    }

    protected void onActionUp(MotionEvent event) {
        super.onActionUp(event);
        if (CurrentMode == Mode.SelectMoveBack) {
            if (textSelectListener != null) {
                textSelectListener.onTextSelected(getCurrentSelectedText());
            }
        } else if (CurrentMode == Mode.SelectMoveForward) {
            if (textSelectListener != null) {
                textSelectListener.onTextSelected(getCurrentSelectedText());
            }
        }

    }

    @Override
    protected void drawNote(Canvas canvas) {
        getDrawer().drawNote(canvas);
    }

    @Override
    protected void drawSelectedText(Canvas canvas) {
        getDrawer().drawSelectedText(canvas);
    }

    @Override
    protected void onTextSelectMoveForward(MotionEvent event) {
        getDrawer().onTextSelectMoveForward(event);
        if (textSelectListener != null) {
            textSelectListener.onTextChanging(FirstSelectedChar, LastSelectedChar);
            textSelectListener.onTextChanging(getCurrentSelectedText());
        }
    }

    @Override
    protected void onTextSelectMoveBack(MotionEvent event) {
        getDrawer().onTextSelectMoveBack(event);
        if (textSelectListener != null) {
            textSelectListener.onTextChanging(FirstSelectedChar, LastSelectedChar);
            textSelectListener.onTextChanging(getCurrentSelectedText());
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        getDrawer().computeScroll();
    }

    private IReaderViewDrawer getDrawer() {
        if (drawer == null) {
            //  drawer = new SerialPageDrawer(this, readerContext, mScroller);
            drawer = new NormalPageDrawer(this, readerContext, mScroller);
        }
        return drawer;
    }

    public void setOnTextSelectListener(ITextSelectListener textSelectListener) {
        this.textSelectListener = textSelectListener;
    }

    public TxtReaderContext getTxtReaderContext() {
        return readerContext;
    }

    private void Reload() {
        saveProgress();
        readerContext.getPageData().refreshTag[0] = 1;
        readerContext.getPageData().refreshTag[1] = 1;
        readerContext.getPageData().refreshTag[2] = 1;
        TxtConfigInitTask configInitTask = new TxtConfigInitTask();
        configInitTask.Run(actionLoadListener, readerContext);
    }

    /**
     * ?????????????????? in px
     *
     * @return
     */
    public int getTextSize() {
        return readerContext.getTxtConfig().getTextSize(getContext());
    }

    /**
     * ??????????????????
     *
     * @param textSize min 25 max 70 in px
     */
    public void setTextSize(int textSize) {
        readerContext.getTxtConfig().saveTextSize(getContext(), textSize);
        Reload();
    }

    /**
     * ??????????????????
     *
     * @return
     */
    public int getBackgroundColor() {
        return readerContext.getTxtConfig().getBackgroundColor(getContext());
    }

    /**
     * ????????????
     *
     * @param backgroundColor
     * @param textColor
     */
    public void setStyle(int backgroundColor, int textColor) {
        saveProgress();
        TxtConfig.saveTextColor(getContext(), textColor);
        TxtConfig.saveBackgroundColor(getContext(), backgroundColor);
        readerContext.getTxtConfig().textColor = textColor;
        readerContext.getTxtConfig().backgroundColor = backgroundColor;
        if (readerContext.getBitmapData().getBgBitmap() != null) {
            readerContext.getBitmapData().getBgBitmap().recycle();
        }
        int width = readerContext.getPageParam().PageWidth;
        int height = readerContext.getPageParam().PageHeight;
        readerContext.getBitmapData().setBgBitmap(TxtBitmapUtil.createBitmap(backgroundColor, width, height));
        refreshCurrentView();
    }

    /**
     * ?????????????????????
     *
     * @param progress 0~100
     */
    public void loadFromProgress(float progress) {
        if (readerContext == null || readerContext.getParagraphData() == null) return;
        if (progress < 0) progress = 0;
        if (progress > 100) progress = 100;
        float p = progress / 100;
        int charNum = readerContext.getParagraphData().getCharNum();
        int charIndex = (int) (p * charNum);
        int paragraphNum = readerContext.getParagraphData().getParagraphNum();
        int paragraphIndex = readerContext.getParagraphData().findParagraphIndexByCharIndex(charIndex);

        if (progress == 100 || paragraphIndex >= paragraphNum) {
            paragraphIndex = paragraphNum - 1;
        }

        if (paragraphIndex < 0) {
            paragraphIndex = 0;
        }

        ELogger.log(tag, "loadFromProgress ,progress:" + progress + "/paragraphIndex:" + paragraphIndex + "/paragraphNum:" + paragraphNum);
        loadFromProgress(paragraphIndex, 0);
    }

    /**
     * ???????????????????????????????????????
     *
     * @param paragraphIndex
     * @param charIndex
     */
    public void loadFromProgress(final int paragraphIndex, final int charIndex) {
        refreshTag(1, 1, 1);
        TxtPageLoadTask txtPageLoadTask = new TxtPageLoadTask(paragraphIndex, charIndex);
        txtPageLoadTask.Run(new LoadListenerAdapter() {
            @Override
            public void onSuccess() {
                if (readerContext != null) {
                    checkMoveState();
                    postInvalidate();
                    post(new Runnable() {
                        @Override
                        public void run() {
                            //IPage midPage = readerContext.getPageData().MidPage();
                            //onPageProgress(midPage);
                            onProgressCallBack(getProgress(paragraphIndex, charIndex));
                            tryFetchFirstPage();
                        }
                    });
                }
            }
        }, readerContext);
    }

    private void tryFetchFirstPage() {
        IPage midPage = readerContext.getPageData().MidPage();
        IPage firstPage = null;
        onPageProgress(midPage);
        if (midPage != null && midPage.HasData()) {
            if (midPage.getFirstChar().ParagraphIndex == 0 && midPage.getFirstChar().CharIndex == 0) {
            } else {
                firstPage = readerContext.getPageDataPipeline().getPageEndToProgress(midPage.getFirstChar().ParagraphIndex, midPage.getFirstChar().CharIndex);
            }
        }
        if (firstPage != null && firstPage.HasData()) {
            if (!firstPage.isFullPage()) {
                //??????????????????????????????????????????
                refreshTag(1, 1, 1);
                loadFromProgress(0, 0);
            } else {
                refreshTag(1, 0, 0);
                readerContext.getPageData().setFirstPage(firstPage);
                ITxtTask task = new DrawPrepareTask();
                task.Run(actionLoadListener, readerContext);//?????????????????????
            }
        }
    }

    /**
     * ?????????????????????
     *
     * @return ??????????????????null, ???????????????????????????null
     */
    public IChapter getCurrentChapter() {
        List<IChapter> chapters = readerContext.getChapters();
        IPage midPage = readerContext.getPageData().MidPage();
        if (chapters == null || chapters.size() == 0 || midPage == null || !midPage.HasData()) {
            return null;
        }
        IChapter lastChapter = readerContext.getChapters().get(readerContext.getChapters().size() - 1);
        int currentP = midPage.getFirstChar().ParagraphIndex;
        int lastP = lastChapter.getStartParagraphIndex();
        int pre = 0;
        int next = 0;
        int index = 1;

        for (int i = 0; i < chapters.size(); i++) {
            int startP = chapters.get(i).getStartParagraphIndex();
            if (i == 0) {
                pre = startP;
            } else {
                next = startP;
                if (currentP >= pre && currentP < next) {
                    index = i;
                    break;
                } else {
                    pre = next;
                }
            }
        }
        if (currentP >= lastP) {
            return lastChapter;
        } else {
            return chapters.get(index - 1);
        }
    }

    /**
     * ??????????????????
     *
     * @return ????????????
     */
    public Boolean jumpToPreChapter() {
        IChapter currentChapter = getCurrentChapter();
        List<IChapter> chapters = getChapters();
        if (chapters == null || currentChapter == null) {
            ELogger.log(tag, "jumpToPreChapter false chapters == null or currentChapter == null");
            return false;
        }

        int index = currentChapter.getIndex();

        if (index == 0 || chapters.size() == 0) {
            ELogger.log(tag, "jumpToPreChapter false index == 0 or chapters.size() == 0");
            return false;
        }
        refreshTag(1, 1, 1);

        IChapter preChapter = chapters.get(index - 1);
        loadFromProgress(preChapter.getStartParagraphIndex(), 0);
        return true;
    }

    /**
     * ??????????????????
     *
     * @return ????????????
     */
    public Boolean jumpToNextChapter() {
        IChapter currentChapter = getCurrentChapter();
        List<IChapter> chapters = getChapters();
        if (chapters == null || currentChapter == null) {
            ELogger.log(tag, "jumpToNextChapter false chapters == null or currentChapter == null");
            return false;
        }
        int index = currentChapter.getIndex();
        if (index >= chapters.size() - 1 || chapters.size() == 0) {
            ELogger.log(tag, "jumpToNextChapter false  < chapters.size() - 1 or chapters.size() == 0");
            return false;
        }

        refreshTag(1, 1, 1);

        IChapter nextChapter = chapters.get(index + 1);
        loadFromProgress(nextChapter.getStartParagraphIndex(), 0);
        return true;
    }

    /**
     * ??????????????????
     */
    public void saveProgress() {
        IPage currentPage = readerContext.getPageData().MidPage();
        if (currentPage != null && currentPage.HasData() && readerContext.getFileMsg() != null) {
            TxtChar firstChar = currentPage.getFirstChar();
            readerContext.getFileMsg().PreParagraphIndex = firstChar.ParagraphIndex;
            readerContext.getFileMsg().PreCharIndex = firstChar.CharIndex;
            readerContext.getFileMsg().CurrentParagraphIndex = firstChar.ParagraphIndex;
            readerContext.getFileMsg().CurrentCharIndex = firstChar.CharIndex;
        }
    }

    /**
     * @param isBold ???????????????
     */
    public void setTextBold(boolean isBold) {
        TxtConfig.saveIsBold(getContext(), isBold);
        getTxtReaderContext().getTxtConfig().Bold = isBold;
        refreshCurrentView();
    }

    /**
     * ??????????????????
     */
    public void setPageSwitchByTranslate() {
        TxtConfig.saveSwitchByTranslate(getContext(), true);
        getTxtReaderContext().getTxtConfig().Page_Switch_Mode = TxtConfig.PAGE_SWITCH_MODE_SERIAL;
        drawer = new SerialPageDrawer(this, readerContext, mScroller);
    }

    /**
     * ??????????????????
     */
    public void setPageSwitchByShear() {
        TxtConfig.saveSwitchByTranslate(getContext(), true);
        getTxtReaderContext().getTxtConfig().Page_Switch_Mode = TxtConfig.PAGE_SWITCH_MODE_SHEAR;
        drawer = new ShearPageDrawer(this, readerContext, mScroller);
    }

    /**
     * ??????????????????
     */
    public void setPageSwitchByCover() {
        TxtConfig.saveSwitchByTranslate(getContext(), false);
        getTxtReaderContext().getTxtConfig().Page_Switch_Mode = TxtConfig.PAGE_SWITCH_MODE_COVER;
        drawer = new NormalPageDrawer(this, readerContext, mScroller);
    }

    /**
     * ??????????????????????????????????????????????????????
     */
    public void saveCurrentProgress() {
        TxtFileMsg fileMsg = getTxtReaderContext().getFileMsg();
        if (getTxtReaderContext().InitDone() && fileMsg != null) {
            String path = fileMsg.FilePath;
            if (path != null && new File(path).exists()) {
                //???????????????????????????
                IPage midPage = getTxtReaderContext().getPageData().MidPage();
                if (midPage != null && midPage.HasData()) {
                    FileReadRecordDB readRecordDB = new FileReadRecordDB(readerContext.getContext());
                    readRecordDB.createTable();
                    FileReadRecordBean r = new FileReadRecordBean();
                    r.fileName = fileMsg.FileName;
                    r.filePath = fileMsg.FilePath;
                    try {
                        r.fileHashName = FileHashUtil.getMD5Checksum(path);
                    } catch (Exception e) {
                        ELogger.log(tag, "saveCurrentProgress Exception:" + e.toString());
                        readRecordDB.closeTable();
                        return;
                    }

                    r.paragraphIndex = midPage.getFirstChar().ParagraphIndex;
                    r.chartIndex = midPage.getFirstChar().CharIndex;
                    readRecordDB.insertData(r);
                    readRecordDB.closeTable();
                } else {
                    ELogger.log(tag, "saveCurrentProgress midPage is false empty");
                }
            }

        }
    }

    private void refreshCurrentView() {
        refreshTag(1, 1, 1);
        ITxtTask task = new DrawPrepareTask();
        task.Run(actionLoadListener, readerContext);
    }

    /**
     * @return ??????????????????
     */
    public List<IChapter> getChapters() {
        return readerContext.getChapters();
    }

    /**
     * @param progress ?????????????????????????????????
     * @return
     */
    public IChapter getChapterFromProgress(int progress) {
        List<IChapter> chapters = getChapters();
        if (chapters != null && chapters.size() > 0) {
            int paragraphNum = getTxtReaderContext().getParagraphData().getParagraphNum();
            int terminalParagraphIndex = progress * paragraphNum / 100;

            if (terminalParagraphIndex == 0) {
                return chapters.get(0);
            }
            for (IChapter chapter : chapters) {
                int startIndex = chapter.getStartParagraphIndex();
                int endIndex = chapter.getEndParagraphIndex();
                ELogger.log("getChapterFromProgress", startIndex + "," + endIndex);
                if (terminalParagraphIndex >= startIndex && terminalParagraphIndex < endIndex) {
                    return chapter;
                }
            }
        }
        return null;
    }
}
