package com.gjj.androidstudydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.View;
import android.widget.Button;

import com.gjj.androidstudydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransitionsActivity extends AppCompatActivity {

    @BindView(R.id.btn_traditional_anim)
    Button mBtnTraditionalAnim;
    @BindView(R.id.btn_5_anim)
    Button mBtn5Anim;
    @BindView(R.id.btn_typical_anim)
    Button mBtnTypicalAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transitions);
        ButterKnife.bind(this);
        setTitle("Activity跳转动画");
    }

    @OnClick({R.id.btn_traditional_anim, R.id.btn_5_anim, R.id.btn_typical_anim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_traditional_anim:
                startActivity(new Intent(TransitionsActivity.this,TraditionalActivity.class));
                overridePendingTransition(R.anim.slide_right_in,R.anim.slide_left_out);
                break;
            case R.id.btn_5_anim:
                startActivity(new Intent(TransitionsActivity.this,Android5Activity.class));
                break;
            case R.id.btn_typical_anim:
                startActivity(new Intent(TransitionsActivity.this,TypicalActivity.class));
                break;
        }
    }
}
