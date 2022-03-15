package com.example.philosophy.reader.interfaces;

import com.example.philosophy.reader.bean.TxtChar;

/**
 * created by ： bifan-wei
 */

public interface ISliderListener {
    void onShowSlider(TxtChar txtChar);

    void onShowSlider(String CurrentSelectedText);

    void onReleaseSlider();
}
