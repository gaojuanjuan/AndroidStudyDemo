package com.gjj.androidstudydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.gjj.androidstudydemo.R;
import com.gjj.androidstudydemo.adapter.CommonListAdapter;
import com.gjj.androidstudydemo.adapter.ItemAdapter;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ItemAdapter.OnItemClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private String[] itemDatas;
    private ItemAdapter mAdapter;
    private HashMap<String, Class> itemMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        mAdapter = new ItemAdapter(this, itemDatas);
        mAdapter.setListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        itemDatas = getResources().getStringArray(R.array.all);
        itemMap = new HashMap<>();
        itemMap.put(getResources().getString(R.string.custom_view), CustomViewActivity.class);
        itemMap.put(getResources().getString(R.string.anim_instance), AnimationActivity.class);
        itemMap.put(getResources().getString(R.string.event_study), EventStudyActivity.class);
    }


    @Override
    public void onItemClick(int position) {
        startActivity(new Intent(this,itemMap.get(itemDatas[position])));
    }
}
