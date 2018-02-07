package com.gjj.androidstudydemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.gjj.androidstudydemo.R;
import com.gjj.androidstudydemo.view.TransPathView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomSVG2Activity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.trans_path_view)
    TransPathView mTransPathView;
    @BindView(R.id.seekbar)
    SeekBar mSeekbar;
    public static final String HEART = "M99,349 C193,240,283,165,400,99 C525,172,611,246,701,348 C521,416,433,511,400,700 C356,509,285,416,99,349";
    public static final String TWITTER = "M99,349 C297,346,376,210,400,99 C432,208,506,345,701,348 C629,479,549,570,400,700 C227,569,194,522,99,349";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_svg2);
        ButterKnife.bind(this);
        setTitle("自定义SVG2");
        mTransPathView.setViewPort(800, 800);
        mTransPathView.setPaths(HEART, TWITTER);
        mSeekbar.setMax(100);
        mSeekbar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        float fraction = progress * 1.0f / seekBar.getMax();
        mTransPathView.setFraction(fraction);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @OnClick(R.id.trans_path_view)
    public void onViewClicked() {
        mTransPathView.startTransWithOutRotate();
    }
}
