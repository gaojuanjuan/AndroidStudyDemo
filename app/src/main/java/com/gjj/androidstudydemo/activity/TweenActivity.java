package com.gjj.androidstudydemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gjj.androidstudydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TweenActivity extends AppCompatActivity {

    @BindView(R.id.iv_six)
    ImageView mIvSix;
    @BindView(R.id.ll_tween)
    LinearLayout mLlTween;

    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tween);
        ButterKnife.bind(this);
        setTitle("补间动画");

    }

    @OnClick({R.id.btn_start2, R.id.btn_start1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start1:
                animation = AnimationUtils.loadAnimation(this, R.anim.tween1);
                mLlTween.startAnimation(animation);
                break;
            case R.id.btn_start2:
                animation = AnimationUtils.loadAnimation(this, R.anim.tween2);
                mIvSix.startAnimation(animation);
                break;
        }

    }
}
