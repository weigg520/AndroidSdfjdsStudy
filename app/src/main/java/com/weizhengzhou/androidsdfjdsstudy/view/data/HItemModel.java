package com.weizhengzhou.androidsdfjdsstudy.view.data;

import com.weizhengzhou.androidsdfjdsstudy.view.interfaces.HItemTypes;

import java.util.List;
import java.util.UUID;

/**
 * Created by 75213 on 2018/1/17.
 */

public class HItemModel {
    private UUID objectId;

    public UUID getObjectId() {
        return objectId;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getToIndex() {
        return toIndex;
    }

    public void setToIndex(int toIndex) {
        this.toIndex = toIndex;
    }

    public HItemTypes getHItemTypes() {
        return mHItemTypes;
    }

    public void setHItemTypes(HItemTypes HItemTypes) {
        mHItemTypes = HItemTypes;
    }

    public ModelBase getOldModel() {
        return oldModel;
    }

    public void setOldModel(ModelBase oldModel) {
        this.oldModel = oldModel;
    }

    public List<ModelBase> getNewModelList() {
        return newModelList;
    }

    public void setNewModelList(List<ModelBase> newModelList) {
        this.newModelList = newModelList;
    }

    private int pageIndex = 0;
    private int toIndex = 0;
    private HItemTypes mHItemTypes;
    private ModelBase oldModel;
    private List<ModelBase> newModelList;

    public HItemModel(UUID objectId, HItemTypes HItemTypes, ModelBase oldModel, List<ModelBase> newModelList) {
        this.objectId = objectId;
        mHItemTypes = HItemTypes;
        this.oldModel = oldModel;
        this.newModelList = newModelList;
    }

    public HItemModel(UUID objectId, int pageIndex, HItemTypes HItemTypes, ModelBase oldModel, List<ModelBase> newModelList) {
        this.objectId = objectId;
        this.pageIndex = pageIndex;
        mHItemTypes = HItemTypes;
        this.oldModel = oldModel;
        this.newModelList = newModelList;
    }


    public HItemModel(UUID objectId, int pageIndex, int toIndex, HItemTypes HItemTypes, ModelBase oldModel, List<ModelBase> newModelList) {
        this.objectId = objectId;
        this.pageIndex = pageIndex;
        this.toIndex = toIndex;
        mHItemTypes = HItemTypes;
        this.oldModel = oldModel;
        this.newModelList = newModelList;
    }

}
