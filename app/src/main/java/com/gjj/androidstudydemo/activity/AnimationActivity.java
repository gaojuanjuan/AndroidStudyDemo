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

public class AnimationActivity extends AppCompatActivity {

    @BindView(R.id.btn_tween_anim)
    Button mBtnTweenAnim;
    @BindView(R.id.btn_frame_anim)
    Button mBtnFrameAnim;
    @BindView(R.id.btn_property_anim)
    Button mBtnPropertyAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.bind(this);
        setTitle("动画的学习");
    }

    @OnClick({R.id.btn_tween_anim, R.id.btn_frame_anim, R.id.btn_property_anim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_tween_anim:
                startActivity(new Intent(AnimationActivity.this,TweenActivity.class));
                break;
            case R.id.btn_frame_anim:
                startActivity(new Intent(AnimationActivity.this,FrameAnimActivity.class));
                break;
            case R.id.btn_property_anim:
                startActivity(new Intent(AnimationActivity.this,PropertyAnimActivity.class));
                break;
        }
    }
}
