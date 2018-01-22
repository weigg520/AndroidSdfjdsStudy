package com.weizhengzhou.androidsdfjdsstudy.view.utils.pentools;


import com.weizhengzhou.androidsdfjdsstudy.view.data.GraphicsItemModel;
import com.weizhengzhou.androidsdfjdsstudy.view.data.PencilModel;
import com.weizhengzhou.androidsdfjdsstudy.view.interfaces.ItemType;
import com.weizhengzhou.androidsdfjdsstudy.view.interfaces.SkyworthGraphicsItem;
import com.weizhengzhou.androidsdfjdsstudy.view.utils.Dpoint;

import java.util.List;

/**
 * Created by 75213 on 2018/1/18.
 */

public class Pencil extends SkyworthGraphicsItem {
    public Pencil(List<Dpoint> points) {
        super(points);
        this.setItemType(ItemType.Pencil);
    }

    @Override
    public String toString() {
        return "Pencil";
    }

    @Override
    public GraphicsItemModel toItemModel() {
        PencilModel model = new PencilModel(mPoints);
        model.setObjId(getItemId());
        model.setPaintModel(toPaintModel());
        model.setMatrix(getItemMatrix());
        return model;
    }
}
