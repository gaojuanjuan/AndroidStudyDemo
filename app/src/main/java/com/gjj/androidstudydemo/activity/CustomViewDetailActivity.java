package com.gjj.androidstudydemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.gjj.androidstudydemo.R;
import com.gjj.androidstudydemo.bean.PieChartBean;
import com.gjj.androidstudydemo.utils.Constants;
import com.gjj.androidstudydemo.view.Bezier3;
import com.gjj.androidstudydemo.view.CheckView;
import com.gjj.androidstudydemo.view.CircleColorGradientSeekBarView;
import com.gjj.androidstudydemo.view.ColorSelectorView;
import com.gjj.androidstudydemo.view.ComposeShaderView;
import com.gjj.androidstudydemo.view.FiveRingView;
import com.gjj.androidstudydemo.view.GradientSeekBarView;
import com.gjj.androidstudydemo.view.LineChartView;
import com.gjj.androidstudydemo.view.MatrixAndCameraView;
import com.gjj.androidstudydemo.view.OpView;
import com.gjj.androidstudydemo.view.PieChartView;
import com.gjj.androidstudydemo.view.RadarView;
import com.gjj.androidstudydemo.view.SearchView;
import com.gjj.androidstudydemo.view.TwinklingTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gjj.androidstudydemo.utils.Constants.BEZIER3;
import static com.gjj.androidstudydemo.utils.Constants.BITMAP_SHADER;
import static com.gjj.androidstudydemo.utils.Constants.CHECKVIEW;
import static com.gjj.androidstudydemo.utils.Constants.CIRCLE_GRADIENT_SEEKBAR;
import static com.gjj.androidstudydemo.utils.Constants.COLOR_SELECTOR;
import static com.gjj.androidstudydemo.utils.Constants.CUSTOM_VIEW1;
import static com.gjj.androidstudydemo.utils.Constants.FIVERING;
import static com.gjj.androidstudydemo.utils.Constants.GRADIENT_SEEKBAR;
import static com.gjj.androidstudydemo.utils.Constants.LINECHART;
import static com.gjj.androidstudydemo.utils.Constants.MATRIX_CAMERA1;
import static com.gjj.androidstudydemo.utils.Constants.OP_VIEW;
import static com.gjj.androidstudydemo.utils.Constants.PATHMEASURE_SEARCHVIEW;
import static com.gjj.androidstudydemo.utils.Constants.PIECHART;
import static com.gjj.androidstudydemo.utils.Constants.RADAR;
import static com.gjj.androidstudydemo.utils.Constants.TWINKLING_TV;

public class CustomViewDetailActivity extends AppCompatActivity {

    @BindView(R.id.search_view)
    SearchView mSearchView;
    @BindView(R.id.martrix_and_camera)
    MatrixAndCameraView mMartrixAndCamera;
    @BindView(R.id.radar_view)
    RadarView mRadarView;
    @BindView(R.id.bezier3)
    Bezier3 mBezier3;
    @BindView(R.id.op_view)
    OpView mOpView;
    @BindView(R.id.twinkling_tv)
    TwinklingTextView mTwinklingTv;
    @BindView(R.id.color_selector_view)
    ColorSelectorView mColorSelectorView;
    @BindView(R.id.circle_gradient_seekbar)
    CircleColorGradientSeekBarView mCircleGradientSeekbar;
    @BindView(R.id.line_chart_view)
    LineChartView mLineChartView;
    @BindView(R.id.bitmapshader_view)
    LinearLayout mBitmapshaderView;
    @BindView(R.id.shader)
    ComposeShaderView mShader;
    @BindView(R.id.rb_0)
    RadioButton mRb0;
    @BindView(R.id.rb_1)
    RadioButton mRb1;
    @BindView(R.id.rb_2)
    RadioButton mRb2;
    @BindView(R.id.rb_3)
    RadioButton mRb3;
    @BindView(R.id.rb_4)
    RadioButton mRb4;
    @BindView(R.id.radiogroup)
    RadioGroup mRadiogroup;
    @BindView(R.id.checkview)
    CheckView mCheckview;
    @BindView(R.id.check)
    Button mCheck;
    @BindView(R.id.uncheck)
    Button mUncheck;
    @BindView(R.id.ll_checkview)
    LinearLayout mLlCheckview;
    @BindView(R.id.five_ring_view)
    FiveRingView mFiveRingView;
    @BindView(R.id.pie_chart_view)
    PieChartView mPieChartView;
    @BindView(R.id.gradient_seek_bar)
    GradientSeekBarView mGradientSeekBar;
    @BindView(R.id.expanded_menu_ll)
    LinearLayout expandedMenuLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_show);
        ButterKnife.bind(this);
        String flag = getIntent().getStringExtra(Constants.CUSTOMVIEW_FLAG);
        if (!TextUtils.isEmpty(flag)) {
            switch (flag) {
                case PATHMEASURE_SEARCHVIEW:
                    mSearchView.setVisibility(View.VISIBLE);
                    break;
                case MATRIX_CAMERA1:
                    mMartrixAndCamera.setVisibility(View.VISIBLE);
                    break;
                case RADAR:
                    mRadarView.setVisibility(View.VISIBLE);
                    break;
                case BEZIER3:
                    mBezier3.setVisibility(View.VISIBLE);
                    break;
                case OP_VIEW:
                    mOpView.setVisibility(View.VISIBLE);
                    break;
                case TWINKLING_TV:
                    mTwinklingTv.setVisibility(View.VISIBLE);
                    break;
                case COLOR_SELECTOR:
                    mColorSelectorView.setVisibility(View.VISIBLE);
                    mColorSelectorView.setListener(new ColorSelectorView.OnColorChangedListener() {
                        @Override
                        public void colorChanged(int color) {
                            Toast.makeText(CustomViewDetailActivity.this, "颜色值 = " + int2Hex(color), Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                case CIRCLE_GRADIENT_SEEKBAR:
                    mCircleGradientSeekbar.setVisibility(View.VISIBLE);
                    break;
                case LINECHART:
                    mLineChartView.setVisibility(View.VISIBLE);
                    break;
                case BITMAP_SHADER:
                    mBitmapshaderView.setVisibility(View.VISIBLE);
                    break;
                case FIVERING:
                    mFiveRingView.setVisibility(View.VISIBLE);
                    break;
                case PIECHART:
                    initPieData();
                    mPieChartView.setVisibility(View.VISIBLE);
                    break;
                case GRADIENT_SEEKBAR:
                    mGradientSeekBar.setMaxCount(100);
                    mGradientSeekBar.setCurrentCount(100);
                    mGradientSeekBar.setVisibility(View.VISIBLE);
                    break;
                case CHECKVIEW:
                    mCheckview.setVisibility(View.VISIBLE);
                    mLlCheckview.setVisibility(View.VISIBLE);
                    break;
                case CUSTOM_VIEW1:
                    expandedMenuLl.setVisibility(View.VISIBLE);
                    break;

            }
            setTitle(flag);
        }
    }

    private void initPieData() {
        PieChartBean bean1 = new PieChartBean(Color.parseColor("#f14033"), "郭瑜", 80);
        PieChartBean bean2 = new PieChartBean(Color.parseColor("#e6870b"), "李旭", 30);
        PieChartBean bean3 = new PieChartBean(Color.parseColor("#0fc2c2"), "南南", 50);
        PieChartBean bean4 = new PieChartBean(Color.parseColor("#29b117"), "石瑾", 60);
        PieChartBean bean5 = new PieChartBean(Color.parseColor("#1b75e4"), "羊角", 20);
        PieChartBean bean6 = new PieChartBean(Color.parseColor("#e746d4"), "亚琴", 30);
        PieChartBean bean7 = new PieChartBean(Color.parseColor("#df0d30"), "贾祺", 60);
        List<PieChartBean> list = new ArrayList<>();
        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean4);
        list.add(bean5);
        list.add(bean6);
        list.add(bean7);
        //设置绘制圆弧的起始角度
        mPieChartView.setStartDegree(90f);
        mPieChartView.setData(list);
    }


    /**
     * Color的Int整型转Color的16进制颜色值【方案一】
     * colorInt - -12590395
     * return Color的16进制颜色值——#3FE2C5
     */
    public static String int2Hex(int colorInt) {
        return String.format("#%06X", Integer.valueOf(16777215 & colorInt));
    }

    @OnClick({R.id.check, R.id.uncheck})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.check:
                mCheckview.check();
                break;
            case R.id.uncheck:
                mCheckview.unCheck();
                break;
        }
    }
}
