package com.weizhengzhou.androidsdfjdsstudy.view.data;

import android.graphics.Paint;
import android.graphics.Paint.Style;

/**
 * Created by 75213 on 2018/1/18.
 */

public class PaintModel {
    private float strokeWidth;
    private int color ;
    private Style fillStyle;
    private int fillColor;

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Style getFillStyle() {
        return fillStyle;
    }

    public void setFillStyle(Style fillStyle) {
        this.fillStyle = fillStyle;
    }

    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    public Paint toPaint(){
        Paint result = new Paint();
        result.setColor(this.color);
        result.setStrokeWidth(this.strokeWidth);
        result.setStyle(this.fillStyle);
        result.setStrokeCap(Paint.Cap.ROUND);
        result.setStrokeJoin(Paint.Join.ROUND);
        result.setAntiAlias(true);
        result.setDither(true);
        return result;
    }
}
