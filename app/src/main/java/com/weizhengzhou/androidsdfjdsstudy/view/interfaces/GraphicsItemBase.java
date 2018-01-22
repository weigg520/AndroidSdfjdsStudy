package com.weizhengzhou.androidsdfjdsstudy.view.interfaces;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

import com.weizhengzhou.androidsdfjdsstudy.view.data.ModelBase;

import java.util.UUID;

/**
 * Created by 75213 on 2018/1/17.
 * 图像对象
 */

public abstract class GraphicsItemBase {

    private UUID mItemId; // 对象唯一值
    protected ItemType mItemType;

    private Point mInitPos = new Point(); // 对象初始位置
    protected Rect mItemBound = new Rect(); // 对象外接区域
    protected Matrix mMatrix = new Matrix();

    protected boolean mSelected = false; // 选中
    private boolean mLocked = false; // 锁定对象

    public UUID getItemId() {
        return mItemId;
    }

    public void setItemId(UUID itemId) {
        this.mItemId = itemId;
    }

    public ItemType getItemType() {
        return mItemType;
    }

    public void setItemType(ItemType itemType) {
        this.mItemType = itemType;
    }

    public boolean isItemLocked() {
        return mLocked;
    }

    public void setItemLocked(boolean locked) {
        mLocked = locked;
    }

    public boolean isItemSelected() {
        return mSelected;
    }

    public void setItemSelected(boolean selected) {
        mSelected = selected;
    }

    public void setInitPosition(int x, int y) {
        mInitPos.set(x, y);
    }

    public Point getInitPosition() {
        return mInitPos;
    }

    public void setItemBound(Rect rect) {
        mItemBound.set(rect);
    }

    public Rect getItemBound() {
        return mItemBound;
    }

    public Matrix getItemMatrix() {
        return mMatrix;
    }

    public void setItemMatrix(Matrix matrix) {
        if (matrix == null || matrix.isIdentity())
            return;

        mMatrix.postConcat(matrix);
    }

    public abstract boolean collision(Rect rect);

    public abstract ModelBase toItemModel();

}
