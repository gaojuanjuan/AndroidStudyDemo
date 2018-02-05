package com.gjj.androidstudydemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by gaojuanjuan on 2018/2/5.
 */

public class SectorView extends View{
    private Paint mPaint;
    private int borderSize;
    private int borderColor;
    private int centerColor;
    private int mWidth;
    private int mHeight;
    private int xPadding;
    private int yPadding;

    private float fraction;
    public SectorView(Context context) {
        this(context,null);
    }

    public SectorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SectorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderSize = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics());

        borderColor = Color.parseColor("#20f00000");
        centerColor = Color.parseColor("#80f00000");

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        if (mWidth == mHeight * 2){
            xPadding = 0;
            yPadding = 0;
        }else if (mWidth < mHeight * 2){
            xPadding = 0;
            yPadding = mHeight - mWidth / 2;
        }else {
            xPadding = (mWidth - mHeight*2)/2;
            yPadding = 0;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float startAngle;
        float endAngle;
        if (fraction <= 0.5f){
            startAngle = 180;
            endAngle = fraction / 0.5f * 180 + 180;
        }else {
            startAngle = (1- fraction)/0.5f*180*-1;
            endAngle = 0;
        }
        mPaint.setColor(borderColor);
        RectF borderRectF = new RectF(xPadding, yPadding, mWidth - xPadding, mHeight * 2 - yPadding);
        canvas.drawArc(borderRectF, startAngle, endAngle - startAngle, true, mPaint);

        mPaint.setColor(centerColor);
        RectF centerRectF = new RectF(xPadding + borderSize, yPadding + borderSize,
                mWidth - xPadding - borderSize, mHeight * 2 - yPadding - borderSize);
        canvas.drawArc(centerRectF, startAngle, endAngle - startAngle, true, mPaint);
    }

    public float getFraction() {
        return fraction;
    }

    public void setFraction(float fraction) {
        this.fraction = fraction;
        invalidate();
    }
}
