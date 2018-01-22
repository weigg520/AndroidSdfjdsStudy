package com.weizhengzhou.androidsdfjdsstudy.test_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.weizhengzhou.androidsdfjdsstudy.R;
import com.weizhengzhou.androidsdfjdsstudy.view.ui.WaveView;

public class WaveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
        WaveView waveView = (WaveView)findViewById(R.id.wave_view);
        waveView.setRunning();
    }
}
