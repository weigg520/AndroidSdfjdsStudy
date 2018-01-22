package com.weizhengzhou.androidsdfjdsstudy.animation_demo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.weizhengzhou.androidsdfjdsstudy.R;

public class AnimationActivity extends AppCompatActivity implements View.OnClickListener {
    private Animation mAnimation;

    private android.widget.Button alphabn;
    private android.widget.Button scalebn;
    private android.widget.Button translatebn;
    private android.widget.Button rotatebn;
    private android.widget.ImageView testanim;
    private Button setanimbn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        this.setanimbn = (Button) findViewById(R.id.set_anim_bn);
        this.testanim = (ImageView) findViewById(R.id.test_anim);
        this.rotatebn = (Button) findViewById(R.id.rotate_bn);
        this.translatebn = (Button) findViewById(R.id.translate_bn);
        this.scalebn = (Button) findViewById(R.id.scale_bn);
        this.alphabn = (Button) findViewById(R.id.alpha_bn);

        alphabn.setOnClickListener(this);
        scalebn.setOnClickListener(this);
        translatebn.setOnClickListener(this);
        rotatebn.setOnClickListener(this);
        setanimbn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alpha_bn:
                //mAnimation = AnimationUtils.loadAnimation(this , R.anim.alpha_anim);
                mAnimation = new AlphaAnimation(1.0f, 0.1f);
                mAnimation = getInstanceAnimation(mAnimation, 1000, 5, AlphaAnimation.REVERSE, true);
                testanim.startAnimation(mAnimation);
                mAnimation.reset();
                break;
            case R.id.scale_bn:
                //mAnimation = AnimationUtils.loadAnimation(this , R.anim.scale_anim);
                /**
                 * pivotXType : 缩放类型
                 * 有Animation.ABSOLUTE 绝对值
                 * Animation.RELATIVE_TO_SELF
                 * Animation.RELATIVE_TO_PARENT
                 */
                mAnimation = new ScaleAnimation(1.0f, 0.1f, 1.0f, 0.1f, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
                mAnimation = getInstanceAnimation(mAnimation, 1000, 5, ScaleAnimation.REVERSE, true);
                testanim.startAnimation(mAnimation);
                mAnimation.reset();
                break;
            case R.id.translate_bn:
                mAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_anim);
                //mAnimation = new TranslateAnimation(1 , 0.0f , 1 , -1.0f , 1 , 0.0f , 1 , 0.0f);
                //mAnimation = getInstanceAnimation(mAnimation , 1000 , 5 , TranslateAnimation.REVERSE , true);
                testanim.startAnimation(mAnimation);
                mAnimation.reset();
                break;
            case R.id.rotate_bn:
                mAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
                // mAnimation = new RotateAnimation(0 ,720 , 1 , 0.5f , 1 , 0.5f);
                // mAnimation = getInstanceAnimation(mAnimation , 1500 , 5 , RotateAnimation.REVERSE , true);
                testanim.startAnimation(mAnimation);
                mAnimation.reset();
                break;
            case R.id.set_anim_bn:
                mAnimation = AnimationUtils.loadAnimation(this, R.anim.map_anim);
                testanim.startAnimation(mAnimation);
                mAnimation.reset();
                break;
        }
    }

    /**
     * @param animation   动画
     * @param duration    时间
     * @param repeatCount 循环次数
     * @param repeatMode  循环方式
     * @param fillAfter   状态
     * @return
     */
    public static Animation getInstanceAnimation(Animation animation, int duration, int repeatCount, int repeatMode, boolean fillAfter) {
        animation.setFillAfter(fillAfter);
        animation.setRepeatCount(repeatCount);
        animation.setRepeatMode(repeatMode);
        animation.setDuration(duration);
        return animation;
    }
}
