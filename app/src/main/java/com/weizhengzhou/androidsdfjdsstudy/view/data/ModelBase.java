package com.weizhengzhou.androidsdfjdsstudy.view.data;

import android.content.Context;
import android.graphics.Matrix;

import com.weizhengzhou.androidsdfjdsstudy.view.interfaces.GraphicsItemBase;
import com.weizhengzhou.androidsdfjdsstudy.view.interfaces.ItemType;

import java.util.UUID;

/**
 * Created by 75213 on 2018/1/17.
 * 基础对象
 */

public abstract class ModelBase {
    private UUID objId;
    private Matrix mMatrix = new Matrix();
    private ItemType itemType;

    public UUID getObjId() {
        return objId;
    }

    public void setObjId(UUID objId) {
        this.objId = objId;
    }

    public Matrix getMatrix() {
        return mMatrix;
    }

    public void setMatrix(Matrix matrix) {
        mMatrix.set(matrix);
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public abstract GraphicsItemBase toViewItem(Context context);

}
