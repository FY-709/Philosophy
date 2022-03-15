package com.example.philosophy.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class CircleImageView extends AppCompatImageView {

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path mPath = new Path();
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mPath.addCircle(width / 2, height / 2, height / 2, Path.Direction.CW);
        canvas.clipPath(mPath);
        super.onDraw(canvas);

        /*Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(getResources().getColor(R.color.LTGRAY));
        canvas.drawPath(mPath, paint);*/
    }
}