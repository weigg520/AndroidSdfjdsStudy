package com.weizhengzhou.androidsdfjdsstudy.home_demo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.weizhengzhou.androidsdfjdsstudy.R;
import com.weizhengzhou.androidsdfjdsstudy.home_demo.BannerPager;
import com.weizhengzhou.androidsdfjdsstudy.home_demo.interfac.IBannerListener;
import com.weizhengzhou.androidsdfjdsstudy.home_demo.view.adapter.IndexPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends AppCompatActivity implements IBannerListener{

    private com.weizhengzhou.androidsdfjdsstudy.home_demo.BannerPager bannerpagerindex;
    private IndexPagerAdapter mAdapter;
    private List<Integer> mImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        this.bannerpagerindex = (BannerPager) findViewById(R.id.banner_pager_index);
        init();
    }

    private void init() {
        mImages = new ArrayList<>();
        mImages.add(R.drawable.test1);
        mImages.add(R.drawable.test2);
        mImages.add(R.drawable.test3);

        mAdapter = new IndexPagerAdapter(this , mImages);
        bannerpagerindex.addOnBannerListener(this);
        bannerpagerindex.setAdapter(mAdapter , mImages.size());
        bannerpagerindex.startLoop();
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bannerpagerindex.stopLoop();
    }
}
