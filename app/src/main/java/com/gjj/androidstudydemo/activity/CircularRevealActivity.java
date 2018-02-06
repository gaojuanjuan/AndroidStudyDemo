package com.gjj.androidstudydemo.activity;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;

import com.gjj.androidstudydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CircularRevealActivity extends AppCompatActivity {

    @BindView(R.id.iv_1)
    ImageView mIv1;
    @BindView(R.id.iv_2)
    ImageView mIv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular_reveal);
        ButterKnife.bind(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.iv_1, R.id.iv_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_1:
                Animator anim1 = ViewAnimationUtils.createCircularReveal(mIv1, mIv1.getWidth() / 2,
                        mIv1.getHeight() / 2, mIv1.getWidth(), 0);
                anim1.setDuration(2000);
                anim1.start();
                break;
            case R.id.iv_2:
                Animator anim2 = ViewAnimationUtils.createCircularReveal(mIv2, 0, 0, 0,
                        (float) Math.hypot(mIv2.getWidth(), mIv2.getHeight()));
                anim2.setDuration(2000);
                anim2.start();
                break;
        }
    }
}
