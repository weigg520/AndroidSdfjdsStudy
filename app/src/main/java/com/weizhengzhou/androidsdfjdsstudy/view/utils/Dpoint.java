package com.weizhengzhou.androidsdfjdsstudy.view.utils;

import android.graphics.Point;

/**
 * Created by 75213 on 2018/1/18.
 * //自定义点
 */

public class Dpoint extends Point {
    /**
     * true:Bessel curve filling point
     */
    public boolean isFill = false;

    /**
     * true:The eraser in addition to the point
     */
    public boolean isDelete = false;

    public Dpoint(int x, int y) {
        super(x, y);
    }

    /**
     *
     * @param x
     * @param y
     * @param f 是否为贝塞尔曲线补点
     */
    public Dpoint(int x, int y, boolean f) {
        super(x, y);
        isFill = f;
    }

    public void set(int x, int y) {
        super.set(x, y);
    }
}
