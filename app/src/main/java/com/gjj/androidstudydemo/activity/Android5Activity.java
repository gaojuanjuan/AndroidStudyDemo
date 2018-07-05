package com.gjj.androidstudydemo.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gjj.androidstudydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Android5Activity extends AppCompatActivity {

    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android5);
        ButterKnife.bind(this);
        setTitle("Android 5.0转场动画");
        mIntent = new Intent(Android5Activity.this, Android5DetailActivity.class);


    }

    @OnClick({R.id.btn_explode_anim, R.id.btn_slide_anim, R.id.btn_fade_anim, R.id.btn_share_anim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_explode_anim:
                mIntent.putExtra("flag", 0);
                startActivity(mIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.btn_slide_anim:
                mIntent.putExtra("flag", 1);
                startActivity(mIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.btn_fade_anim:
                mIntent.putExtra("flag", 2);
                startActivity(mIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.btn_share_anim:
                View fab = findViewById(R.id.fab_button);
                View txName = findViewById(R.id.tx_user_name);
                mIntent.putExtra("flag", 3);
                //创建单个共享元素
//        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, view, "share").toBundle());
                //创建多个共享单元
                startActivity(mIntent, ActivityOptions.makeSceneTransitionAnimation(this,
                        Pair.create(view, "share"),
                        Pair.create(fab, "fab"),
                        Pair.create(txName, "user_name"))
                        .toBundle());
                break;
        }
    }
}
