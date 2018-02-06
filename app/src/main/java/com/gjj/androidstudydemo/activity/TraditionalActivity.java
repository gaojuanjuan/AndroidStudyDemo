package com.gjj.androidstudydemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gjj.androidstudydemo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TraditionalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traditional);
        ButterKnife.bind(this);
        setTitle("传统转场动画");
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);

    }
}
