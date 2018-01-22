package com.weizhengzhou.androidsdfjdsstudy.view.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by 75213 on 2018/1/16.
 */

public class SkyworthTextView extends AppCompatTextView {
    public SkyworthTextView(Context context) {
        super(context);
    }

    public SkyworthTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        TextPaint textPaint = getPaint();
        float textPaintWidth = textPaint.measureText(getText().toString());
        float mWidth = getMeasuredWidth();
        float mHeight = getMeasuredHeight();
        canvas.rotate(45 , mWidth / 2, mHeight/2);
        Log.e("SkyworthTextView" ,"w:"  + mWidth  + " h" + mHeight + " t:" + textPaintWidth);
        super.onDraw(canvas);
    }
}
