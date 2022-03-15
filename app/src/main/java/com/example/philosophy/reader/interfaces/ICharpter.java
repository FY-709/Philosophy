package com.example.philosophy.reader.interfaces;

/**
 * Created by HP on 2017/11/15.
 */

public interface ICharpter {
    int getParagraphNum();

    int getStartParagraphIndex();

    void setStartParagraphIndex(int index);

    int getEndParagraphIndex();

    void setEndParagraphIndex(int index);

    String getTitle();
}
