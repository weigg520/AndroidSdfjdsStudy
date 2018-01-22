package com.weizhengzhou.androidsdfjdsstudy.view.data;

import android.content.Context;

import com.weizhengzhou.androidsdfjdsstudy.view.interfaces.GraphicsItemBase;
import com.weizhengzhou.androidsdfjdsstudy.view.interfaces.ItemType;
import com.weizhengzhou.androidsdfjdsstudy.view.utils.Dpoint;
import com.weizhengzhou.androidsdfjdsstudy.view.utils.pentools.Pencil;

import java.util.List;


/**
 * Created by 75213 on 2018/1/18.
 */

public class PencilModel extends GraphicsItemModel {
    private List<Dpoint> mPoints;

    public PencilModel(List<Dpoint> points) {
        this.mPoints = points;
        setItemType(ItemType.Pencil);
    }

    public List<Dpoint> getPointsFs() {
        return mPoints;
    }

    public void setPointsFs(List<Dpoint> points) {
        this.mPoints = points;
    }

    @Override
    public GraphicsItemBase toViewItem(Context context) {
        Pencil result = new Pencil(this.mPoints);
        result.setStrokePaint(this.getPaintModel().toPaint());
        result.setItemId(getObjId());
        result.creatGraphicsPath(mPoints, result.getItemType());
        result.setItemMatrix(getMatrix());
        return result;
    }
}
