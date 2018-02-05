package com.gjj.androidstudydemo.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.gjj.androidstudydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FrameAnimActivity extends AppCompatActivity {

    @BindView(R.id.iv_frame)
    ImageView mIvFrame;
    @BindView(R.id.btn_start)
    Button mBtnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_anim);
        ButterKnife.bind(this);
        setTitle("逐帧动画");


    }

    @OnClick(R.id.btn_start)
    public void onViewClicked() {
        mIvFrame.setImageResource(R.drawable.animation_list);
        AnimationDrawable anim = (AnimationDrawable) mIvFrame.getDrawable();
        anim.start();
    }
}
