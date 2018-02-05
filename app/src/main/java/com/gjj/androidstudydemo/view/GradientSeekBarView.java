package com.gjj.androidstudydemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by gaojuanjuan on 2018/1/25.
 */

public class GradientSeekBarView extends View {
    private static final int[] SECTION_COLORS = {0xffCC3399, 0xFF00CCFF, 0xff339999};
    private  Context mContext;
    private Paint mPaint;
    private RectF rfBase;       //底层圆角矩形
    private RectF rfCover;      //覆盖层圆角矩形，底层圆角矩形和覆盖层圆角矩形形成一个线框
    private RectF rfContent;    //内容圆角矩形
    private int mWidth;
    private int mHeight;
    private float currentCount;
    private float maxCount;

    public GradientSeekBarView(Context context) {
        this(context,null);
    }

    public GradientSeekBarView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public GradientSeekBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mPaint = new Paint();
        rfBase = new RectF();
        rfCover = new RectF();
        rfContent = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);

        if (wMode == MeasureSpec.EXACTLY || wMode ==MeasureSpec.AT_MOST){
            mWidth = wSize;
        }else {
            mWidth = 0;
        }
        if (hMode == MeasureSpec.AT_MOST || hMode ==MeasureSpec.UNSPECIFIED){
            mHeight = dpToPx(15);
        }else {
            mHeight = hSize;
        }
        setMeasuredDimension(mWidth,mHeight);
    }

    private int dpToPx(int dp){
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f * (dp >= 0 ? 1:-1));
    }
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        int round = mHeight /2;

        //绘制底层的圆角矩形
        mPaint.setColor(Color.GRAY);
        rfBase.set(0,0,mWidth,mHeight);
        canvas.drawRoundRect(rfBase,round,round,mPaint);

        //绘制内层的圆角矩形，和底层的圆角矩形形成线框
        mPaint.setColor(Color.WHITE);
        rfCover.set(2,2,mWidth-2,mHeight-2);
        canvas.drawRoundRect(rfCover,round,round,mPaint);

        //得到当前位置占总宽度的比例
        float section = currentCount / maxCount;
        
        //创建内容圆角矩形
        rfContent.set(2,2,(mWidth - 2)*section,mHeight - 2);
        
        //如果当前值的比例小于等于1/3，设置颜色为颜色数组中的第一个值
        if (section <= 1.0f/3.0f){
            if (section != 0.0f){
                mPaint.setColor(SECTION_COLORS[0]);                
            }else {
                mPaint.setColor(Color.TRANSPARENT);
            }
        }else{
            int count = (section <= 1.0f / 3.0f * 2) ? 2 : 3;
            int[] colors = new int[count];
            System.arraycopy(SECTION_COLORS,0,colors,0,count);
            LinearGradient shader = new LinearGradient(3, 3, (mWidth - 3) * section, mHeight - 3, colors, null, Shader.TileMode.MIRROR);
            mPaint.setShader(shader);
        }
        canvas.drawRoundRect(rfContent,round,round,mPaint);
        //一次绘制完成之后应该重置一下画笔
        mPaint.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        //下面这句话的作用：表示子组件要自己消费这次事件，告诉父组件不要拦截（抢走）这次的事件
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                moved(x);
                break;
            case MotionEvent.ACTION_MOVE:
                moved(x);
                break;
            case MotionEvent.ACTION_UP:
                moved(x);
                break;
        }
        return true;
    }

    private void moved(float x) {
        if (x > mWidth){
            return;
        }
        currentCount = maxCount * (x / mWidth);
        invalidate();
    }

    public void setMaxCount(float maxCount){
        this.maxCount = maxCount;
    }

    public void setCurrentCount(float currentCount){
        this.currentCount = currentCount > maxCount ? maxCount :currentCount;
        invalidate();
    }

    public float getMaxCount() {
        return maxCount;
    }

    public float getCurrentCount() {
        return currentCount;
    }
}
