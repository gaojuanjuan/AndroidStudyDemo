package com.gjj.androidstudydemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.gjj.androidstudydemo.utils.DpUtil;


/**
 * Created by gaojuanjuan on 2018/1/24.
 */

public class FiveRingView extends ViewGroup {
    private  Context mContext;
    private TextPaint mTextPaint;
    private int startX;//圆环起始X轴
    private int startY;//圆环起始Y轴
    private int mWidth;
    private int mHeight;

    public FiveRingView(Context context) {
        this(context,null);
    }

    public FiveRingView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FiveRingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        setBackgroundColor(Color.TRANSPARENT);
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setTextSize(50);
        mTextPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();
        startX = startY = 0;
        int gap = 10;//同一行圆圈之间的间隔
        int screenWidth = DpUtil.getScreenSizeWidth(mContext);
        int firstTotalWidth = 0;//第一行子View的总宽度
        int secondTotalWidth = 0;//第二行子View的总高度
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int cWidth = childView.getMeasuredWidth();
            if (i <= 2){
                //前三个总宽度
                firstTotalWidth += cWidth;
            }else {
                //后两个总宽度
                secondTotalWidth += cWidth;
            }
        }

        int leftFMargin = (screenWidth - firstTotalWidth - gap *2)/2;
        int leftSMargin = (screenWidth - secondTotalWidth - gap)/2;

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            if (i <= 2){
                //排列前三个圆圈
                if (i == 0){
                    childView.layout(leftFMargin+startX,startY,
                            leftFMargin + startX + childWidth, startY+childHeight);
                    startX += childWidth;
                }else{
                    childView.layout(leftFMargin + startX + gap,startY,
                            leftFMargin + startX + gap+childWidth,startY+childHeight);
                    startX +=childWidth+gap;
                }
                if (i == 2){
                    startX = 0;
                    startY += childHeight/2;
                }
            }else {
                //排列后两个圆圈
                if (i == 3){
                    childView.layout(leftSMargin + startX,startY,
                            leftSMargin + startX + childWidth,startY + childHeight);
                    startX += childWidth;
                }else {
                    childView.layout(leftSMargin + startX + gap,startY,
                            leftSMargin + startX + gap + childWidth,startY + childHeight);
                    startX += (childWidth + gap);
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int screenSizeWidth = DpUtil.getScreenSizeWidth(mContext);
        String upStr = "同一个世界，同一个梦想";
        String downStr = "One World,One Dream";
        canvas.drawText(upStr,(screenSizeWidth - mTextPaint.measureText(upStr)) / 2,getRingHeight()/2*3+60,mTextPaint);
        canvas.drawText(downStr,(screenSizeWidth - mTextPaint.measureText(downStr)) / 2,getRingHeight()/2*3+120,mTextPaint);
    }

    /**
     * 获得圆环高度
     * @return
     */
    private int getRingHeight() {
        return getChildAt(0).getMeasuredHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (wMode){
            case MeasureSpec.EXACTLY:
                mWidth = wSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                //应该先计算所有子View的宽度
                mWidth = wSize;
                break;
        }
        switch (hMode){
            case MeasureSpec.EXACTLY:
                mHeight = hSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                //应该先计算所有子View的高度
                mHeight = hSize;
                break;
        }
        setMeasuredDimension(mWidth,mHeight);
    }


}
