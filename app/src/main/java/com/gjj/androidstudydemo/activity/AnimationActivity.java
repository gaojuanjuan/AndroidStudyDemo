package com.gjj.androidstudydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.gjj.androidstudydemo.R;
import com.gjj.androidstudydemo.adapter.ItemAdapter;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimationActivity extends AppCompatActivity implements ItemAdapter.OnItemClickListener {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private String[] itemDatas;
    private ItemAdapter mAdapter;
    private HashMap<String, Class> itemMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.bind(this);
        setTitle("动画的学习");
        initData();
        mAdapter = new ItemAdapter(this, itemDatas);
        mAdapter.setListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        itemDatas = getResources().getStringArray(R.array.animation);
        itemMap = new HashMap<>();
        itemMap.put(getResources().getString(R.string.anim_tween), TweenActivity.class);
        itemMap.put(getResources().getString(R.string.anim_frame), FrameAnimActivity.class);
        itemMap.put(getResources().getString(R.string.anim_property), PropertyAnimActivity.class);
        itemMap.put(getResources().getString(R.string.anim_circular_reveal), CircularRevealActivity.class);
        itemMap.put(getResources().getString(R.string.anim_transitions), TransitionsActivity.class);
        itemMap.put(getResources().getString(R.string.anim_vector), VectorAnimationActivity.class);
        itemMap.put(getResources().getString(R.string.anim_drawpath), DrawPathActivity.class);
        itemMap.put(getResources().getString(R.string.anim_custom_svg1), CustomSVG1Activity.class);
        itemMap.put(getResources().getString(R.string.anim_custom_svg2), CustomSVG2Activity.class);
    }



    @Override
    public void onItemClick(int position) {
        startActivity(new Intent(this,itemMap.get(itemDatas[position])));
    }
}
