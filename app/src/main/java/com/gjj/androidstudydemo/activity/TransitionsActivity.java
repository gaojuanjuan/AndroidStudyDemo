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

public class TransitionsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.listview)
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transitions);
        ButterKnife.bind(this);
        setTitle("Activity跳转动画");

        String[] transitions = getResources().getStringArray(R.array.transitions);
        listview.setAdapter(new CommonListAdapter(this,transitions));
        listview.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                startActivity(new Intent(TransitionsActivity.this, TraditionalActivity.class));
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                break;
            case 1:
                startActivity(new Intent(TransitionsActivity.this, Android5Activity.class));
                break;
            case 2:
                startActivity(new Intent(TransitionsActivity.this, TypicalActivity.class));
                break;

        }
    }
}
