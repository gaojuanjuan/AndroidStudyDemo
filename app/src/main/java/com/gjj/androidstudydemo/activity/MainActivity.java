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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.listview)
    ListView listview;
    private String[] mAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAll = getResources().getStringArray(R.array.all);
        listview.setAdapter(new CommonListAdapter(this,mAll));

        listview.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                startActivity(new Intent(MainActivity.this, CustomViewActivity.class));
                break;
            case 1:
                startActivity(new Intent(MainActivity.this, AnimationActivity.class));
                break;
        }
    }
}
