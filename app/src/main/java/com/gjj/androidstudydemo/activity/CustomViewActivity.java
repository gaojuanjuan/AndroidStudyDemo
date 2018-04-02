package com.gjj.androidstudydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gjj.androidstudydemo.R;
import com.gjj.androidstudydemo.utils.Constants;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomViewActivity extends AppCompatActivity {

    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        ButterKnife.bind(this);
        setTitle("自定义View");
        mIntent = new Intent(CustomViewActivity.this, CustomViewDetailActivity.class);
    }

    @OnClick({R.id.btn_checkview, R.id.btn_drawpath, R.id.btn_bezier3, R.id.btn_op,
            R.id.btn_search_view, R.id.btn_matrix_camera1, R.id.btn_pie_chart, R.id.btn_five_ring,
            R.id.btn_seek_bar, R.id.btn_twinkling_tv, R.id.btn_color_selector,
            R.id.btn_circle_gradient_seekbar, R.id.btn_line_chart_view, R.id.btn_circle_img,
            R.id.btn_custom_view1,R.id.btn_draw_rect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_checkview:
                startCustomActivity(Constants.CHECKVIEW);
                break;
            case R.id.btn_drawpath:
                startCustomActivity(Constants.RADAR);
                break;
            case R.id.btn_bezier3:
                startCustomActivity(Constants.BEZIER3);
                break;
            case R.id.btn_op:
                startCustomActivity(Constants.OP_VIEW);
                break;
            case R.id.btn_search_view:
                startCustomActivity(Constants.PATHMEASURE_SEARCHVIEW);
                break;
            case R.id.btn_matrix_camera1:
                startCustomActivity(Constants.MATRIX_CAMERA1);
                break;
            case R.id.btn_pie_chart:
                startCustomActivity(Constants.PIECHART);
                break;
            case R.id.btn_five_ring:
                startCustomActivity(Constants.FIVERING);
                break;
            case R.id.btn_seek_bar:
                startCustomActivity(Constants.GRADIENT_SEEKBAR);
                break;
            case R.id.btn_twinkling_tv:
                startCustomActivity(Constants.TWINKLING_TV);
                break;
            case R.id.btn_color_selector:
                startCustomActivity(Constants.COLOR_SELECTOR);
                break;
            case R.id.btn_circle_gradient_seekbar:
                startCustomActivity(Constants.CIRCLE_GRADIENT_SEEKBAR);
                break;
            case R.id.btn_line_chart_view:
                startCustomActivity(Constants.LINECHART);
                break;
            case R.id.btn_circle_img:
                startCustomActivity(Constants.BITMAP_SHADER);
                break;
            case R.id.btn_custom_view1:
                startCustomActivity(Constants.CUSTOM_VIEW1);
                break;
            case R.id.btn_draw_rect:
                startActivity(new Intent(CustomViewActivity.this, SquareActivity.class));
                break;
        }
    }


    private void startCustomActivity(String flag) {
        mIntent.putExtra(Constants.CUSTOMVIEW_FLAG,flag);
        startActivity(mIntent);
    }
}
