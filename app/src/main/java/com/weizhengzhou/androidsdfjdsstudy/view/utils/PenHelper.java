package com.weizhengzhou.androidsdfjdsstudy.view.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Shader;
import android.media.ThumbnailUtils;

/**
 * Created by 75213 on 2018/1/17.
 */

public class PenHelper {
    private static PenHelper instance = new PenHelper();

    private PenHelper() {
    }

    public static PenHelper getInstance() {

        return instance;
    }

    //浮雕效果
    public MaskFilter getEmbossMaskFilter() {
        return new EmbossMaskFilter(new float[] { 1, 1, 1 }, 0.4f, 6, 3.5f);
    }

    //模糊效果
    public MaskFilter getBlurMaskFilter() {
        return new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
    }

    /**
     * TileMode：（一共有三种）
     * CLAMP  ：如果渲染器超出原始边界范围，会复制范围内边缘染色。
     * REPEAT ：横向和纵向的重复渲染器图片，平铺。
     * MIRROR ：横向和纵向的重复渲染器图片，这个和REPEAT 重复方式不一样，他是以镜像方式平铺。
     */

    /**
     * 获取本地资源图片
     * @param res
     * @param drawableId
     * @return
     */
    public BitmapShader getBitmapShader(Resources res, int drawableId) {
        Bitmap mBitmap = BitmapFactory.decodeResource(res, drawableId);
        BitmapShader bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        return bitmapShader;
    }

    /**
     * 获取内存卡资源图片
     * @param path
     * @return
     */
    public BitmapShader getBitmapShader(String path) {
        Bitmap mBitmap = BitmapFactory.decodeFile(path);
        Bitmap b = ThumbnailUtils.extractThumbnail(mBitmap, 30, 30);
        BitmapShader bitmapShader = new BitmapShader(b, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        if (mBitmap != null && !mBitmap.isRecycled()) {
            mBitmap.recycle();
            mBitmap = null;
            System.gc();
        }
        return bitmapShader;
    }
}
