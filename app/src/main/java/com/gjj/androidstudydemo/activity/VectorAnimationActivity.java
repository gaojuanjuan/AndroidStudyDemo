package com.gjj.androidstudydemo.activity;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.gjj.androidstudydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VectorAnimationActivity extends AppCompatActivity {

    @BindView(R.id.imgBtn)
    ImageView mImgBtn;
    @BindView(R.id.iv_1)
    ImageView mIv1;
    @BindView(R.id.iv_2)
    ImageView mIv2;
    @BindView(R.id.iv_3)
    ImageView mIv3;
    private boolean isSearchBoxChecked = false;
    private boolean isTwitteChecked = false;
    private boolean isFavoriteClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_animation);
        ButterKnife.bind(this);
        setTitle("矢量动画");
    }

    @OnClick({R.id.imgBtn, R.id.iv_1, R.id.iv_2, R.id.iv_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBtn:
                Drawable drawable = mImgBtn.getDrawable();
                ((Animatable) drawable).start();
                break;
            case R.id.iv_1:
                isSearchBoxChecked = !isSearchBoxChecked;
                final int[] stateSet = {android.R.attr.state_checked * (isSearchBoxChecked ? 1 : -1)};
                mIv1.setImageState(stateSet,true);
                break;
            case R.id.iv_2:
                isTwitteChecked = ! isTwitteChecked;
                final int[] stateSet1 = {android.R.attr.state_checked * (isTwitteChecked ? 1 : -1)};
                mIv2.setImageState(stateSet1,true);
                break;
            case R.id.iv_3:
                isFavoriteClick = !isFavoriteClick;
                final int[] stateSet2 = {android.R.attr.state_checked * (isFavoriteClick ? 1 : -1)};
                mIv3.setImageState(stateSet2,true);
                break;
        }
    }
}
