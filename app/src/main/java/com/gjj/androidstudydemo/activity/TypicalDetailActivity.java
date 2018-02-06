package com.gjj.androidstudydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjj.androidstudydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TypicalDetailActivity extends AppCompatActivity {

    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.tx_name)
    TextView mTxName;
    @BindView(R.id.tx_desc)
    TextView mTxDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typical_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String desc = intent.getStringExtra("desc");
        int resId = intent.getIntExtra("resId", 0);
        mIvHead.setImageResource(resId);
        mTxName.setText(name);
        mTxDesc.setText(desc);
    }
}
