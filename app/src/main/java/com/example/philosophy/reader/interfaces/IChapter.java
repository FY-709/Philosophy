package com.example.philosophy.reader.interfaces;

/**
 * Created by HP on 2017/11/15.
 */

public interface IChapter {

    int getIndex();

    int getStartParagraphIndex();

    void setStartParagraphIndex(int index);

    int getEndParagraphIndex();

    void setEndParagraphIndex(int index);

    int getStartCharIndex();

    int getEndCharIndex();

    int getStartIndex();

    String getTitle();

}
