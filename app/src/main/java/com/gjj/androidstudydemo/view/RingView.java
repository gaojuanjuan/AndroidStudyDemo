package com.gjj.androidstudydemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.gjj.androidstudydemo.R;


/**
 * Created by gaojuanjuan on 2018/1/24.
 */

public class RingView extends View {

    private int ringView;
    private int stokeWidth;
    private Paint mPaint;

    public RingView(Context context) {
        this(context,null);
    }

    public RingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RingView);
        ringView = typedArray.getColor(R.styleable.RingView_ring_color, Color.RED);
        stokeWidth = typedArray.getInt(R.styleable.RingView_stroke_width,10);
        typedArray.recycle();

        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(ringView);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(stokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float radius = (Math.min(getMeasuredHeight(), getMeasuredWidth()) - mPaint.getStrokeWidth()) / 2;
        canvas.drawCircle(getMeasuredWidth() / 2,getMeasuredHeight()/2,radius,mPaint);
    }
}
