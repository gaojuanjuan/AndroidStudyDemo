package com.gjj.androidstudydemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.gjj.androidstudydemo.R;
import com.gjj.androidstudydemo.view.TapeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TapViewActivity extends AppCompatActivity implements TapeView.OnValueChangeListener {

    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.tapWeight)
    TapeView tapWeight;
    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.tapeHeight)
    TapeView tapeHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap_view);
        ButterKnife.bind(this);
        setTitle("漂亮的卷尺");
        tapWeight.setOnValueChangeListener(this);
        tapeHeight.setOnValueChangeListener(new TapeView.OnValueChangeListener() {
            @Override
            public void onChange(float value) {
                tvHeight.setText(value +" "+getString(R.string.cm));
            }
        });

        tvWeight.setText(tapWeight.getValue() + " "+getString(R.string.kg));
        tvHeight.setText(tapeHeight.getValue() +" "+getString(R.string.cm));
    }

    @Override
    public void onChange(float value) {
        tvWeight.setText(value + " "+getString(R.string.kg));
    }
}
