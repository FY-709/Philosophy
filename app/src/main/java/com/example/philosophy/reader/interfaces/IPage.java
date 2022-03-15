package com.example.philosophy.reader.interfaces;

import com.example.philosophy.reader.bean.TxtChar;

import java.util.List;


/*
 * create by bifan-wei
 * 2017-11-13
 */
public interface IPage {
    ITxtLine getLine(int index);

    void addLine(ITxtLine line);

    void addLineTo(ITxtLine line, int index);

    TxtChar getFirstChar();

    TxtChar getLastChar();

    ITxtLine getFirstLine();

    ITxtLine getLastLine();

    List<ITxtLine> getLines();

    void setLines(List<ITxtLine> lines);

    ICursor<ITxtLine> getLineCursor();

    int getLineNum();

    boolean isFullPage();//是否满页了

    void setFullPage(boolean fullPage);

    int CurrentIndex();

    Boolean HasData();
}
