package com.weizhengzhou.androidsdfjdsstudy.wifi_demo.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

import com.weizhengzhou.androidsdfjdsstudy.R;

/**
 * Created by 75213 on 2018/1/27.
 */

public class WifiDialog extends Dialog {
    public WifiDialog(Context context) {
        super(context , R.style.MyDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_dialog);
    }
}
