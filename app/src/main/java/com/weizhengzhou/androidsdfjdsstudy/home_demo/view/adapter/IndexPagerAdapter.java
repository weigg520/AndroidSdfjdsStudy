package com.weizhengzhou.androidsdfjdsstudy.home_demo.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

/**
 * Created by 75213 on 2018/1/25.
 */

public class IndexPagerAdapter extends PagerAdapter {
    private List<Integer> mImages;
    private Context mContent;

    public IndexPagerAdapter(Context content , List images){
        mImages = images;
        mContent = content;
    }

    @Override
    public int getCount() {
        return mImages ==  null ? 0 :  mImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        FrameLayout frame = new FrameLayout(mContent);
        frame.setBackgroundResource(mImages.get(position));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT);
        frame.setLayoutParams(params);
        container.addView(frame);
        return frame;
    }
}
