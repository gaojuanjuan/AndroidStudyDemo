package com.gjj.androidstudydemo.activity;

import android.graphics.Picture;
import android.graphics.drawable.PictureDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.gjj.androidstudydemo.R;
import com.gjj.androidstudydemo.view.PathView;
import com.gjj.androidstudydemo.view.SearchView1;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DrawPathActivity extends AppCompatActivity {

    @BindView(R.id.pathView)
    PathView mPathView;
    @BindView(R.id.btn_path_anim)
    Button mBtnPathAnim;
    @BindView(R.id.iv_demo)
    ImageView mIvDemo;
    @BindView(R.id.searchview)
    SearchView1 mSearchview;
    @BindView(R.id.btn_searchview_anim)
    Button mBtnSearchviewAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_path);
        ButterKnife.bind(this);
        setTitle("Path绘制");
    }

    @OnClick({R.id.btn_path_anim, R.id.btn_searchview_anim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_path_anim:
                mPathView.getPathAnimator().
                        delay(100).
                        duration(1500).
                        interpolator(new AccelerateDecelerateInterpolator())
                        .start();
                try {
                    SVG svg = SVG.getFromResource(this, R.raw.android);
                    Picture picture = svg.renderToPicture();
                    PictureDrawable drawable = new PictureDrawable(picture);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
                        mIvDemo.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
                    }
                    mIvDemo.setImageDrawable(drawable);
                } catch (SVGParseException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.btn_searchview_anim:
                mSearchview.startAnim();
                break;
        }
    }
}
