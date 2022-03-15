package com.example.philosophy.reader.interfaces;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.philosophy.reader.bean.TxtChar;

import java.util.List;

/**
 * Created by bifan-wei
 * on 2017/12/4.
 */

public interface ITextSelectDrawer {
    void drawSelectedChar(TxtChar selectedChar, Canvas canvas, Paint paint);

    void drawSelectedLines(List<ITxtLine> selectedLines, Canvas canvas, Paint paint);
}
