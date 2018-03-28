package com.gjj.androidstudydemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gjj.androidstudydemo.R;
import com.gjj.androidstudydemo.adapter.SquareAdapter;
import com.gjj.androidstudydemo.bean.SquareBean;
import com.gjj.androidstudydemo.view.SquareView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawRectActivity extends AppCompatActivity implements SquareAdapter.ReduceAndAddClickListener {


    @BindView(R.id.square_view)
    SquareView mSquareView;
    @BindView(R.id.rv)
    RecyclerView mRv;
    private ArrayList<SquareBean> squareBeans;
    private SquareAdapter mSquareAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_rect);
        ButterKnife.bind(this);
        initData();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRv.setLayoutManager(manager);
        mSquareAdapter = new SquareAdapter(this, squareBeans);
        mSquareAdapter.setReduceAndAddClickListener(this);
        mRv.setAdapter(mSquareAdapter);
    }


    private void initData() {
        squareBeans = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            SquareBean squareBean = new SquareBean(i);
            squareBeans.add(squareBean);
        }
    }




    @Override
    public void clickAdd(int postion) {

        mSquareView.addData(postion,true);
    }

    @Override
    public void clickReduce(int postion) {
        mSquareView.addData(postion,false);
    }
}
