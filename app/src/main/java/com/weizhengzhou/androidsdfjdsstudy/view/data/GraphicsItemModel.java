package com.weizhengzhou.androidsdfjdsstudy.view.data;

/**
 * Created by 75213 on 2018/1/18.
 */

public abstract class GraphicsItemModel extends ModelBase {
    private PaintModel paintModel;

    public PaintModel getPaintModel() {
        return paintModel;
    }

    public void setPaintModel(PaintModel paintModel) {
        this.paintModel = paintModel;
    }
}
