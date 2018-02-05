package com.gjj.androidstudydemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by gaojuanjuan on 2018/1/25.
 */

public class TwinklingTextView extends TextView {
    private int mViewWidth;                 //用于获取整个View的宽度
    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private int mTranslate;                 //用于记录渲染的偏移量

    public TwinklingTextView(Context context) {
        this(context,null);
    }

    public TwinklingTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TwinklingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //通过这个条件判断，可以保证只在初始化的时候调用一次
        if (mViewWidth == 0){
            mViewWidth = getMeasuredWidth();
        }
        if (mViewWidth > 0){
            //创建渐变渲染器
            mLinearGradient = new LinearGradient(0,0,mViewWidth,0,new int[]{0x33e20b6c, 0xffe20b6c, 0x33e20b6c},
                    null, Shader.TileMode.CLAMP);
            //对当前view的paing设置渲染
            getPaint().setShader(mLinearGradient);
            mGradientMatrix = new Matrix();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mGradientMatrix != null){
            mTranslate += mViewWidth / 10;
            if (mTranslate > 2*mViewWidth){
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate,0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(50);
        }

    }
}
