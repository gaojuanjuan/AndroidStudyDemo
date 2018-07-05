package com.gjj.androidstudydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.gjj.androidstudydemo.R;
import com.gjj.androidstudydemo.adapter.CommonListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.listview)
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.bind(this);
        setTitle("动画的学习");

        String[] animStrings = getResources().getStringArray(R.array.animation);

        listview.setAdapter(new CommonListAdapter(this,animStrings));
        listview.setOnItemClickListener(this);
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                startActivity(new Intent(AnimationActivity.this, TweenActivity.class));
                break;
            case 1:
                startActivity(new Intent(AnimationActivity.this, FrameAnimActivity.class));
                break;
            case 2:
                startActivity(new Intent(AnimationActivity.this, PropertyAnimActivity.class));
                break;
            case 3:
                startActivity(new Intent(AnimationActivity.this, CircularRevealActivity.class));
                break;
            case 4:
                startActivity(new Intent(AnimationActivity.this, TransitionsActivity.class));
                break;
            case 5:
                startActivity(new Intent(AnimationActivity.this, VectorAnimationActivity.class));
                break;
            case 6:
                startActivity(new Intent(AnimationActivity.this, DrawPathActivity.class));
                break;
            case 7:
                startActivity(new Intent(AnimationActivity.this, CustomSVG1Activity.class));
                break;
            case 8:
                startActivity(new Intent(AnimationActivity.this, CustomSVG2Activity.class));
                break;

        }
    }
}
