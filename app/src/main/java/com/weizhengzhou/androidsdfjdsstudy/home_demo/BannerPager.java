package com.weizhengzhou.androidsdfjdsstudy.home_demo;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.weizhengzhou.androidsdfjdsstudy.R;
import com.weizhengzhou.androidsdfjdsstudy.home_demo.interfac.IBannerListener;
import com.weizhengzhou.androidsdfjdsstudy.home_demo.wiget.ViewPagerScroller;

import java.lang.reflect.Field;

/**
 * Created by 75213 on 2018/1/24.
 */

public class BannerPager extends FrameLayout implements ViewPager.OnPageChangeListener {
    private static final String TAG =  BannerPager.class.getSimpleName();

    private ViewPager mViewPager;
    private LinearLayout mDotContainer;
    private IBannerListener mListener;

    private static final long PAGE_SMOOTH_PERIOD = 3000; //轮播时间
    private int mLastSelectedPos = 0;//标记上一次选中点的位置
    private Context mContext;
    private int mDocSize ; //页面数量
    private int normalDot; //状态【未选中】
    private int selectDot; //状态【选中】

    private int mSize; //标记上一个点


    public BannerPager(@NonNull Context context) {
        super(context , null);
    }

    public BannerPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs , R.styleable.BannerPager);
        mDocSize = a.getDimensionPixelSize(R.styleable.BannerPager_dot_size ,dp2px(5));
        normalDot = a.getResourceId(R.styleable.BannerPager_dot_normal , R.drawable.normal_dot_shape);
        selectDot = a.getResourceId(R.styleable.BannerPager_dot_select , R.drawable.select_dot_shape);
        //回收属性
        a.recycle();

        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_banner_pager , this);
        initView();
    }

    private void initView() {
        mViewPager = (ViewPager)findViewById(R.id.banner_view_pager);
        mDotContainer = (LinearLayout)findViewById(R.id.banner_dots_container);

        initViewPager();
        initEvent();
    }

    private void initEvent() {
        mViewPager.addOnPageChangeListener(this);
    }

    private void initViewPager() {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            Interpolator interpolator = new AccelerateDecelerateInterpolator();
            ViewPagerScroller scroller = new ViewPagerScroller(getContext() , interpolator);
            field.set(mViewPager , scroller);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }

    /**
     *  当页面发生滚动时候的回调
     * @param position 滚动页面的下标
     * @param positionOffset 页面滚动偏移的像素点的个数 / viewPager宽度
     * @param positionOffsetPixels 页面滚动偏移的像素点的个数
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d(TAG, "onPageScrolled " + position + " "
                + positionOffset + " " + positionOffsetPixels);
    }

    /**
     * 页面被选中回调
     * @param position 选中页面的下标
     */
    @Override
    public void onPageSelected(int position) {
        if (mListener != null){
            mListener.onPageSelected(position);
        }
        //如果选中位置和上次选中位置是同一个位置 不做处理
        if (position == mLastSelectedPos){
            return;
        }
        //切换图片的同时点切换
        View doc = mDotContainer.getChildAt(position);
        doc.setBackgroundResource(selectDot);
        //将上次选中的点的背景恢复成白色
        View perDot = mDotContainer.getChildAt(mLastSelectedPos);
        perDot.setBackgroundResource(normalDot);
        //更新上次选中的点的位置
        mLastSelectedPos = position;
    }

    public static final int SCROLL_STATE_IDLE = 0;//空闲状态
    public static final int SCROLL_STATE_DRAGGING = 1;//拖拽状态
    public static final int SCROLL_STATE_SETTLING = 2;//设置状态，从手指松开到空闲状态之间的状态
    /**
     * 页面滚动状态发生变化的回调
     * @param state
     */
    @Override
    public void onPageScrollStateChanged(int state) {

        Log.e(TAG, "onPageScrollStateChanged " + state);
        switch (state){
            case SCROLL_STATE_IDLE:
                goLoop();
                break;
            case SCROLL_STATE_DRAGGING:
                stopLoop();
                carouselTag = true;
                break;
            case SCROLL_STATE_SETTLING:
                goLoop();
                break;
            default:
                break;
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dp2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 添加监听
     * @param listener
     */
    public void addOnBannerListener(IBannerListener listener) {
        mListener = listener;
    }

    /***
     * 设置适配器
     * @param adapter
     * @param size
     */
    public void setAdapter(PagerAdapter adapter , int size){
        mViewPager.setOffscreenPageLimit(size);
        mViewPager.setAdapter(adapter);
        setSize(size);
    }

    /**
     * 轮播图设置数据
     * @param size 可滑动的页面
     */
    public void setSize(int size) {
        mSize = size;
        if (size < 1)
            return;
        //先清理掉原来的点
        mDotContainer.removeAllViews();
        for (int i = 0 ;i < size ;i++){
            View doc = new View(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mDocSize ,mDocSize);
            doc.setLayoutParams(params);
            //最后一个点不要间距
            if (i != size - 1){
                //配置点间距
                params.rightMargin = dp2px(17f);
            }
            //默认第一个点选中
            doc.setBackgroundResource( i == 0 ? selectDot : normalDot);
            //将点添加到容器
            mDotContainer.addView(doc);
        }
    }

    private boolean runTag = true;

    /**
     * 开始轮播
     */
    public void startLoop() {
        if (mSize > 1) {
            //postDelayed(mLoop, PAGE_SMOOTH_PERIOD);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (runTag){
                        SystemClock.sleep(PAGE_SMOOTH_PERIOD);
                        handler.sendEmptyMessage(1);
                    }
                }
            }).start();
        }
    }

    private boolean carouselTag = true; //轮播标记

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                int item = mViewPager.getCurrentItem(); //获取当前viewpager的位置
                Log.e(TAG , "数据itemL: " + item + "size： " + mSize  + "取余:" + (item%mSize));
                if (carouselTag){
                    item++;
                    if (item == mSize -1){
                        carouselTag = false;
                    }
                }else {
                    item--;
                    if (item == 0){
                        carouselTag = true;
                    }
                }
                mViewPager.setCurrentItem((item%mSize) , true);
            }
        }
    };


    /**
     * 开启
     */
    public void goLoop(){
        runTag = true;
    }

    /**
     * 停止无限循环
     */
    public void stopLoop(){
        runTag = false;
    }


}
