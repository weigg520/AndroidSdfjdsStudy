package com.weizhengzhou.androidsdfjdsstudy.view;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;

import com.weizhengzhou.androidsdfjdsstudy.R;
import com.weizhengzhou.androidsdfjdsstudy.view.utils.PenHelper;

import java.io.File;

/**
 * Created by 75213 on 2018/1/17.
 */

public class AppConstants {
    //获取实例
    public static AppConstants instance = new AppConstants();
    //画笔默认大小
    private float defaultPenWidth = 2.0f;
    //默认颜色
    private int defaultPenColor = Color.BLACK;
    //默认背景颜色
    private int defaultBackgroundColor = Color.WHITE;
    //花纹
    private int defaultTextureId = R.drawable.texture1;
    private String defaultTexturePath = null;
    public Paint defaultPaint = new Paint();

    public static final int INSERT_IMAGE = 1;
    public static final int LOAD_FILE = 2;
    public static final int SAVE_RESULT = 3;
    public static final int SAVE_AND_LOAD_RESULT = 4;
    public static final int SAVE_AND_NEW_RESULT = 5;
    public static final int INSERT_VIDEO = 6;
    public static final int SETTINGS = 7;
    public static final int INSERT_OFFICE = 8;
    public static final int INSERT_PDF = 9;
    public static final int SAVE_AND_QUIT = 10;
    public static final String CAPTURE_PATH = "capture_path";
    public static final int DISPLAY_STAND_QEQUEST = 20;
    public static final int DISPLAY_STAND_RESULT = 21;

    public static final float DIS_COLLISION = 7.5f;
    /**
     * 当前页图片插入上限
     */
    public static final int INSERT_IMAGE_COUNT = 5;

    public static final String ERASER_GESTURE_START = "com.skyworthgd.action.starterasemode";
    public static final String ERASER_GESTURE_END = "com.skyworthgd.action.finisherasemode";

    private AppConstants() {
        //设置画笔颜色
        defaultPaint.setColor(defaultPenColor);
        //设置画笔宽度
        defaultPaint.setStrokeWidth(defaultPenWidth);
        /**
         * 设置画笔类型
         * Paint.Style.STROKE 只绘制图形轮廓（描边）
         * Paint.Style.FILL 只绘制图形内容
         * Paint.Style.FILL_AND_STROKE 既绘制轮廓也绘制内容
         */
        defaultPaint.setStyle(Paint.Style.STROKE);
        /**
         * 设置线冒样式
         * 取值有
         * Cap.ROUND(圆形线冒)
         * Cap.SQUARE(方形线冒)
         * Paint.Cap.BUTT(无线冒)
         */
        defaultPaint.setStrokeCap(Paint.Cap.ROUND);
        /**
         * 设置结合处的样子
         * Miter:结合处为锐角
         * Round:结合处为圆弧
         * BEVEL：结合处为直线
         */
        defaultPaint.setStrokeJoin(Paint.Join.ROUND);
        //抗锯齿
        defaultPaint.setAntiAlias(true);
        //防止抖动
        defaultPaint.setDither(true);
    }

    public static AppConstants getInstance() {
        return instance;
    }

    public int getDefaultTextureId() {
        return defaultTextureId;
    }

    public String getDefaultTexturePath() {
        return defaultTexturePath;
    }

    public boolean isCustomTexture() {
        if (defaultTexturePath == null)
            return false;

        File file = new File(defaultTexturePath);
        if (file.exists())
            return true;
        else
            return false;
    }

    public void setDefaultTextureId(Resources res, int defaultTextureId) {
        this.defaultTextureId = defaultTextureId;
        this.defaultTexturePath = null;
        Shader shader = PenHelper.getInstance().getBitmapShader(res, defaultTextureId);
        this.defaultPaint.setShader(shader);
    }

    public void setDefaultTextureId(String texturePath) {
        this.defaultTexturePath = texturePath;
        Shader shader = PenHelper.getInstance().getBitmapShader(texturePath);
        this.defaultPaint.setShader(shader);
    }

    public int getDefaultBackgroundColor() {
        return defaultBackgroundColor;
    }

    public void setDefaultBackgroundColor(int defaultBackgroundColor) {
        this.defaultBackgroundColor = defaultBackgroundColor;
    }

    public int getDefaultPenColor() {
        return defaultPenColor;
    }

    public void setDefaultPenColor(int defaultPenColor) {
        this.defaultPenColor = defaultPenColor;
        this.defaultPaint.setColor(this.defaultPenColor);
        this.defaultPaint.setShader(null);
    }

    public float getDefaultPenWidth() {
        return defaultPenWidth;
    }

    public void setDefaultPenWidth(int defaultPenWidth) {
        this.defaultPenWidth = defaultPenWidth;
        this.defaultPaint.setStrokeWidth(this.defaultPenWidth);
    }
}
