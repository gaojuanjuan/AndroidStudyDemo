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
import com.gjj.androidstudydemo.utils.Constants;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomViewActivity extends AppCompatActivity implements ItemAdapter.OnItemClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private Intent mIntent;
    private ItemAdapter mAdapter;
    private String[] itemDatas;
    private HashMap<String, String> itemMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        ButterKnife.bind(this);
        setTitle("自定义View");
        mIntent = new Intent(CustomViewActivity.this, CustomViewDetailActivity.class);

        initData();
        mAdapter = new ItemAdapter(this, itemDatas);
        mAdapter.setListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        itemDatas = getResources().getStringArray(R.array.custom_view);
        itemMap = new HashMap<>();
        itemMap.put(getResources().getString(R.string.view_book_page),Constants.BOOK_PAGE);
        itemMap.put(getResources().getString(R.string.view_wave_progress),Constants.WAVE_PROGRESS);
        itemMap.put(getResources().getString(R.string.view_circle_bar),Constants.CIRCLE_BAR);
        itemMap.put(getResources().getString(R.string.view_custom_view1),Constants.CUSTOM_VIEW1);
        itemMap.put(getResources().getString(R.string.view_checkview),Constants.CHECKVIEW);
        itemMap.put(getResources().getString(R.string.view_radar),Constants.RADAR);
        itemMap.put(getResources().getString(R.string.view_bezier3),Constants.BEZIER3);
        itemMap.put(getResources().getString(R.string.view_op_view),Constants.OP_VIEW);
        itemMap.put(getResources().getString(R.string.view_pathmeasure_searchview),Constants.PATHMEASURE_SEARCHVIEW);
        itemMap.put(getResources().getString(R.string.view_matrix_camera1),Constants.MATRIX_CAMERA1);
        itemMap.put(getResources().getString(R.string.view_pie_chart),Constants.PIECHART);
        itemMap.put(getResources().getString(R.string.view_fivering),Constants.FIVERING);
        itemMap.put(getResources().getString(R.string.view_gradient_seekbar),Constants.GRADIENT_SEEKBAR);
        itemMap.put(getResources().getString(R.string.view_twinkling_tv),Constants.TWINKLING_TV);
        itemMap.put(getResources().getString(R.string.view_color_selector),Constants.COLOR_SELECTOR);
        itemMap.put(getResources().getString(R.string.view_circle_gradient_seekbar),Constants.CIRCLE_GRADIENT_SEEKBAR);
        itemMap.put(getResources().getString(R.string.view_line_chart),Constants.LINECHART);
        itemMap.put(getResources().getString(R.string.view_bitmap_shader),Constants.BITMAP_SHADER);
        itemMap.put(getResources().getString(R.string.view_pie_chart2),Constants.PIECHART2);
    }

    private void startCustomActivity(String flag) {
        mIntent.putExtra(Constants.CUSTOMVIEW_FLAG, flag);
        startActivity(mIntent);
    }


    @Override
    public void onItemClick(int position) {
        if (itemDatas[position].equals(getResources().getString(R.string.view_square))){
            startActivity(new Intent(CustomViewActivity.this, SquareActivity.class));
        }else if (itemDatas[position].equals(getResources().getString(R.string.view_tape))){
            startActivity(new Intent(CustomViewActivity.this, TapViewActivity.class));
        }else {
            startCustomActivity(itemMap.get(itemDatas[position]));
        }
    }
}
