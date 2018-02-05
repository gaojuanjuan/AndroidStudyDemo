package com.gjj.androidstudydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gjj.androidstudydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_custom_view)
    Button mBtnCustomView;
    @BindView(R.id.btn_anim)
    Button mBtnAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_custom_view, R.id.btn_anim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_custom_view:
                startActivity(new Intent(MainActivity.this, CustomViewActivity.class));
                break;
            case R.id.btn_anim:
                startActivity(new Intent(MainActivity.this,AnimationActivity.class));
                break;
        }
    }
}
