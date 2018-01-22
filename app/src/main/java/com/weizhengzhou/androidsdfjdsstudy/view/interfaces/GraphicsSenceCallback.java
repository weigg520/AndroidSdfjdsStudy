package com.weizhengzhou.androidsdfjdsstudy.view.interfaces;

import android.graphics.Path;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.UUID;

/**
 * Created by 75213 on 2018/1/18.
 */

public interface GraphicsSenceCallback {

    /**
     * 一个对象或多个对象（批量）添加到sence,当为批量时，添加完最后一个对象才更新和添加到撤销重做队列
     * @param item
     * @param update 是否立即刷新
     * @param end 批量中的最后一个
     * @param addHistory 是否添加到撤销重做队列
     */
    public void addItem(GraphicsItemBase item, boolean update, boolean end, boolean addHistory);

    public void removeItem(UUID itemId, boolean addHistory);

    public void hidePopupWindow();

    /**
     * 曲线被橡皮擦过，被拆分线段临时保存
     *
     * @param item
     */
    public void addBreakupItem(GraphicsItemBase item);

    /**
     * 橡皮擦功能
     *
     * @param path
     *            橡皮路径
     * @param rect
     *            擦除区域
     */
    public void eraserDown(Path path, Rect rect);

    public void eraserMove(Path path, Rect rect);

    public void eraserPointerUp(Path path, Rect rect);

    public void eraserUp(Path path, Rect rect);

    /**
     * 对象擦功能
     *
     * @param rect
     *            擦除区域
     */
    public void eraserObjectDown(Rect rect);

    public void eraserObjectMove(Rect rect);

    public void eraserObjectUp(Rect rect);

    /**
     * 点选/框选
     *
     */
    public boolean floatingViewOperating();

    public boolean hyperLinkViewOperating();

    public boolean toolOperating();

    public void unchooseItem();

    /**
     * 清除被对象擦标识的item
     */
    public void cancelObjectEraseFlagItems();

    public void chooseDown(MotionEvent event, Rect rect);

    public void choosePointerDown(MotionEvent event, int x, int y);

    public void chooseMove(MotionEvent event, Rect rect);

    public void choosePointerUp(MotionEvent event, int x, int y);

    public void chooseUp(MotionEvent event, Rect rect);

    public void toolDown(MotionEvent event, Rect rect);

    public void toolPointerDown(MotionEvent event, int x, int y);

    public void toolMove(MotionEvent event, Rect rect);

    public void toolPointerUp(MotionEvent event, int x, int y);

    public void toolUp(MotionEvent event, Rect rect);

    /**
     * 漫游操作
     */
    public void onRoamDown(MotionEvent event);

    public void onRoamMove(MotionEvent event);

    public void onRoamUp(MotionEvent event);
}
