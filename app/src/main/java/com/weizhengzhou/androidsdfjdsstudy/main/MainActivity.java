package com.weizhengzhou.androidsdfjdsstudy.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.weizhengzhou.androidsdfjdsstudy.R;
import com.weizhengzhou.androidsdfjdsstudy.animation_demo.view.AnimationActivity;
import com.weizhengzhou.androidsdfjdsstudy.home_demo.view.IndexActivity;
import com.weizhengzhou.androidsdfjdsstudy.main.adapter.MainAdapter;
import com.weizhengzhou.androidsdfjdsstudy.main.bean.MainBean;
import com.weizhengzhou.androidsdfjdsstudy.test_demo.UITestActivity.AdhesionActivity;
import com.weizhengzhou.androidsdfjdsstudy.test_demo.UITestActivity.TextViewUiActivity;
import com.weizhengzhou.androidsdfjdsstudy.test_demo.WaveActivity;
import com.weizhengzhou.androidsdfjdsstudy.wifi_demo.WifiConnectActivity;
import com.weizhengzhou.androidsdfjdsstudy.wifi_demo.WifiDemoActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView main_listview;
    private MainAdapter mAdapter;
    private List<MainBean> mMainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        main_listview = (ListView) findViewById(R.id.activity_main_list_view);
        //加载按钮数据
        mMainList = new ArrayList<>();
        initData();
        //初始化适配器
        mAdapter = new MainAdapter(this, mMainList);
        main_listview.setAdapter(mAdapter);
        main_listview.setOnItemClickListener(this);
    }



    /**
     * 描述： 跳转到相应界面
     */
    private void initData() {
        //生命周期
        mMainList.add(getMainBean("倾斜45度角TextView", TextViewUiActivity.class));
        mMainList.add(getMainBean("波浪View", WaveActivity.class));
        mMainList.add(getMainBean("粘合View", AdhesionActivity.class));
        mMainList.add(getMainBean("动画基础", AnimationActivity.class));
        mMainList.add(getMainBean("Wifi设置", WifiConnectActivity.class));
        mMainList.add(getMainBean("Wifi设置2", WifiDemoActivity.class));
        mMainList.add(getMainBean("轮播图", IndexActivity.class));
    }

    /**
     * 描述：返回Bean
     *
     * @param name
     * @param cls
     * @return
     */
    private MainBean getMainBean(String name, Class<? extends Activity> cls) {
        MainBean bean = new MainBean();
        bean.setActivityClass(cls);
        bean.setNameItem(name);
        return bean;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //跳转到相应Activity界面
        startActivity(new Intent(MainActivity.this, mMainList.get(position).getActivityClass()));
    }
}
