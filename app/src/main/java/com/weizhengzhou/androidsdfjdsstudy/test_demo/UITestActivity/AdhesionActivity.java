package com.weizhengzhou.androidsdfjdsstudy.test_demo.UITestActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.weizhengzhou.androidsdfjdsstudy.R;
import com.weizhengzhou.androidsdfjdsstudy.view.ui.adhesion.AdhesionLayout;

public class AdhesionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adhesion);
        AdhesionLayout adhesionLayout = (AdhesionLayout) findViewById(R.id.adhesion);
        adhesionLayout.setOnAdherentListener(new AdhesionLayout.OnAdherentListener() {
            @Override
            public void onDismiss() {
                Toast.makeText(AdhesionActivity.this , "消失" , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
