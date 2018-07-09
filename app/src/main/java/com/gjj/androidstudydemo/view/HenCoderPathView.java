package com.gjj.androidstudydemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class HenCoderPathView extends View {


    private Paint mPaint;
    private Path mPath;

    public HenCoderPathView(Context context) {
       this (context,null);
    }

    public HenCoderPathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HenCoderPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPath = new Path();

        //绘制一个心形
//        mPath.addArc(200,200,400,400,-225,225);
//        mPath.arcTo(400,200,600,400,-180,225,false);
//        mPath.lineTo(400,542);

        //绘制一个圆
        mPath.addCircle(300,300,200, Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath,mPaint);
    }
}
