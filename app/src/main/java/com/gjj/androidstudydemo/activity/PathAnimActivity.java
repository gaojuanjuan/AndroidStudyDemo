package com.gjj.androidstudydemo.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.gjj.androidstudydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PathAnimActivity extends AppCompatActivity {

    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_anim);
        ButterKnife.bind(this);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.btn_start)
    public void onViewClicked() {
        Path path = new Path();
        path.moveTo(100, 100);
        path.quadTo(screenWidth - 300, 200, screenWidth - 100, screenHeight - 600);

        ObjectAnimator animator = ObjectAnimator.ofFloat(mIv, View.X, View.Y, path);
        animator.setDuration(2000);
        animator.setRepeatCount(1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();
    }
}
