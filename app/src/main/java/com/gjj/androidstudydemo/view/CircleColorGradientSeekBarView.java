package com.gjj.androidstudydemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.gjj.androidstudydemo.utils.Constants;


/**
 * Created by gaojuanjuan on 2018/1/26.
 */

public class CircleColorGradientSeekBarView extends View {
    private static final int[] SECTION_COLORS = {0xffffd300, Color.GREEN, 0xff319ed4, 0xffffd300};
    private Paint baseRingPaint;
    private RectF rect;
    private Paint colorfulRingPaint;
    private float ringWidth = 60;
    private int cx;
    private int cy;
    private int outerRadius;
    private float innerRadius;
    private float ringRadius;

    private int angle = 0;//弧度值
    private float mMaxProgress = 100;//最大进度值
    private boolean CALLED_FROM_ANGLE = false;
    private int mProgress;
    private int mProgressPercent;
    private Paint mTextPaint;

    public CircleColorGradientSeekBarView(Context context) {
        this(context, null);
    }

    public CircleColorGradientSeekBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleColorGradientSeekBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        baseRingPaint = new Paint();
        baseRingPaint.setColor(Color.GRAY);
        baseRingPaint.setAntiAlias(true);
        baseRingPaint.setStrokeWidth(ringWidth);
        baseRingPaint.setStyle(Paint.Style.STROKE);
        rect = new RectF();

        colorfulRingPaint = new Paint();
        colorfulRingPaint.setColor(Color.parseColor("#ff33b5e5"));
        colorfulRingPaint.setAntiAlias(true);
        colorfulRingPaint.setStrokeWidth(ringWidth);
        colorfulRingPaint.setStyle(Paint.Style.STROKE);

        setBackgroundColor(Color.TRANSPARENT);
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setTextSize(100);
        mTextPaint.setColor(Color.RED);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e(Constants.TAG, "w = " + w + "h = " + h);
        Log.e(Constants.TAG, "width = " + getWidth() + "height = " + getHeight());
        Log.e(Constants.TAG, "MeasuredWidth = " + getMeasuredWidth() + ",MeasuredHeight = " + getMeasuredHeight());
        //选择最小的值作为圆环视图的直径
        int size = (w > h) ? h : w;

        cx = w / 2;
        cy = h / 2;

        outerRadius = size / 2;//圆环外部半径
        ringRadius = outerRadius - ringWidth / 2;//圆环的半径
        innerRadius = outerRadius - ringWidth;//圆环内部半径

        float left = cx - ringRadius;   //渐变圆环外接矩形左边坐标
        float right = cx + ringRadius;  //渐变圆环外接矩形右边坐标
        float top = cy - ringRadius;    //渐变圆环外接矩形顶部坐标
        float bottom = cy + ringRadius; //渐变圆环外接矩形底部坐标
        rect.set(left, top, right, bottom);//设置渐变圆环的位置
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        SweepGradient shader = new SweepGradient(cx, cy, SECTION_COLORS, null);
        Matrix matrix = new Matrix();
        matrix.setRotate(-90, cx, cy);
        shader.setLocalMatrix(matrix);
        colorfulRingPaint.setShader(shader);
        //画背景圆环;
        canvas.drawCircle(cx, cy, ringRadius, baseRingPaint);
        String text = mProgressPercent + "%";
        //获取text的高度和宽度
        Rect textRect = new Rect();
        mTextPaint.getTextBounds(text,0,text.length(),textRect);
        canvas.drawText(text,cx-(textRect.width()/2),cy+(textRect.height()/2),mTextPaint);
        //画渐变圆环，每次刷新界面主要是改变这里的angle的值
        Log.e(Constants.TAG, "onDraw,rect = " + this.rect.toString());
        canvas.drawArc(this.rect, 270, angle, false, colorfulRingPaint);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        this.getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moved(x, y, false);
                break;
            case MotionEvent.ACTION_MOVE:
                moved(x, y, false);
                break;
            case MotionEvent.ACTION_UP:
                moved(x, y, true);
                break;
        }
        return true;
    }

    private void moved(float x, float y, boolean up) {
        //计算圆心到触摸点的直线距离，使用数学中的勾股定理
        float distance = (float) Math.sqrt(Math.pow((x - cx), 2) + Math.pow((y - cy), 2));

        //如果触摸点在外圆半径的一个适配区域内
        if (distance < outerRadius + 100 && distance > innerRadius - 100 && !up) {

            //将角度转换成弧度
            float degrees = (float) ((float) ((Math.toDegrees(Math.atan2(x - cx, cy - y)) + 360.0)) % 360.0);

            //使弧度值永远为正
            if (degrees < 0) {
                degrees += 2 * Math.PI;
            }

            setAngle(Math.round(degrees));
            invalidate();

        } else {
            invalidate();
        }
    }

    public void setAngle(int angle) {
        this.angle = angle;
        float donePercent = (((float) this.angle) / 360) * 100;
        float progress = (donePercent / 100) * getMaxProgress();
        setProgressPercent(Math.round(donePercent));
        CALLED_FROM_ANGLE = true;
        setProgress(Math.round(progress));
    }

    public float getMaxProgress() {
        return mMaxProgress;
    }

    public void setProgress(int progress) {
        mProgress = progress;
    }

    public int getProgressPercent() {
        return mProgressPercent;
    }

    public void setProgressPercent(int progressPercent) {
        mProgressPercent = progressPercent;
    }

    public void setRingBackgroundColor(int color) {
        baseRingPaint.setColor(color);
    }

}
