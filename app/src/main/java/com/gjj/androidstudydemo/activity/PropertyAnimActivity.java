package com.gjj.androidstudydemo.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjj.androidstudydemo.R;
import com.gjj.androidstudydemo.view.SectorView;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PropertyAnimActivity extends AppCompatActivity {

    @BindView(R.id.iv_1)
    ImageView mIv1;
    @BindView(R.id.btn_property_start1)
    Button mBtnPropertyStart1;
    @BindView(R.id.iv_2)
    ImageView mIv2;
    @BindView(R.id.btn_property_start2)
    Button mBtnPropertyStart2;
    @BindView(R.id.view_1)
    View mView1;
    @BindView(R.id.layout_2)
    RelativeLayout mLayout2;
    @BindView(R.id.btn_property_start3)
    Button mBtnPropertyStart3;
    @BindView(R.id.sector_view)
    SectorView mSectorView;
    @BindView(R.id.btn_property_start4)
    Button mBtnPropertyStart4;
    @BindView(R.id.btn_property_path)
    Button mBtnPropertyPath;
    @BindView(R.id.txt_number)
    TextView mTxtNumber;
    @BindView(R.id.btn_property_start5)
    Button mBtnPropertyStart5;
    private ObjectAnimator anim1;
    private ValueAnimator anim2;
    private ValueAnimator anim3;
    private ObjectAnimator anim4;
    private ValueAnimator anim5;

    private  DecimalFormat format = new DecimalFormat("#.00");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_anim);
        ButterKnife.bind(this);
        setTitle("属性动画");
    }

    @OnClick({R.id.btn_property_start1, R.id.btn_property_start2, R.id.btn_property_start3, R.id.btn_property_start4, R.id.btn_property_path, R.id.btn_property_start5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_property_start1:
                anim1 = ObjectAnimator.ofFloat(mIv1,"rotationY",0,359);
                anim1.setDuration(1000);
                anim1.setRepeatCount(1);
                anim1.setRepeatMode(ValueAnimator.REVERSE);
                anim1.start();
                break;
            case R.id.btn_property_start2:
                anim2 = ValueAnimator.ofFloat(0,1);
                anim2.setDuration(1000);
                anim2.setInterpolator(new AccelerateInterpolator());
                anim2.setRepeatCount(1);
                anim2.setRepeatMode(ValueAnimator.REVERSE);
                anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float f = animation.getAnimatedFraction();
                        float rotate = f * 359;
                        mIv2.setRotationY(rotate);
                    }
                });
                anim2.start();
                break;
            case R.id.btn_property_start3:
                final int layoutWidth = mLayout2.getWidth();
                int layoutHeight = mLayout2.getHeight();
                final int dotWidth = mView1.getWidth();
                //此处>>的优先级小于减号的，也就是先计算减号再除以2
                final int radio = layoutHeight - dotWidth >> 1;//右移一位相当于除以2
                Log.d("gjj","layoutWidth = "+layoutWidth+",layoutHeight = " +layoutHeight+",dotWidth = "
                        +dotWidth+",dotWidth >> 1 = "+(dotWidth >> 1)+",radio = "+radio);
                Log.d("gjj","mView1.getX() = "+mView1.getX()+"mView1.getY() = "+mView1.getY());
                anim3 = ValueAnimator.ofFloat(0,1);
                anim3.setDuration(1600);
                anim3.setInterpolator(new AccelerateInterpolator());
                anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float f = animation.getAnimatedFraction();
                        f+=0.5;
                        if (f>1){
                            f-=1;
                        }
                        double angle = f * Math.PI * 2;
                        double x = radio * Math.sin(angle);
                        double y = radio * Math.cos(angle);
                        mView1.setX((int) x + (layoutWidth - dotWidth >> 1));
                        mView1.setY(((int) y)+radio);
                    }
                });
                anim3.start();
                break;
            case R.id.btn_property_start4:
                anim4 = ObjectAnimator.ofFloat(mSectorView,"fraction",0,1);
                anim4.setDuration(2000);
                anim4.setRepeatCount(ValueAnimator.INFINITE);
                anim4.setRepeatMode(ValueAnimator.RESTART);
                anim4.start();
                break;
            case R.id.btn_property_path:
                startActivity(new Intent(PropertyAnimActivity.this,PathAnimActivity.class));
                break;
            case R.id.btn_property_start5:
                anim5 = ValueAnimator.ofFloat(0, 2000);
                anim5.setDuration(2000);
                anim5.setInterpolator(new LinearInterpolator());
                anim5.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        mTxtNumber.setText(format.format(value));
                    }
                });
                anim5.start();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(anim1 != null) {
            anim1.cancel();
            anim1 = null;
        }
        if(anim2 != null) {
            anim2.cancel();
            anim2 = null;
        }
        if(anim3 != null) {
            anim3.cancel();
            anim3 = null;
        }
        if(anim4 != null) {
            anim4.cancel();
            anim4 = null;
        }
         if(anim5 != null) {
             anim5.cancel();
             anim5 = null;
        }

    }
}
