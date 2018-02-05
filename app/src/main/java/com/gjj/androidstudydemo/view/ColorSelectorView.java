package com.gjj.androidstudydemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import com.gjj.androidstudydemo.utils.Constants;

import static java.lang.Math.PI;

/**
 * Created by gaojuanjuan on 2018/1/25.
 */

public class ColorSelectorView extends View {
    private static final int CENTER_RADIUS = 50;
    private int[] mColors;
    private Paint mColorPaint;
    private Paint mCenterPaint;

    private OnColorChangedListener mListener;
    private static final float PI = 3.1415926f;
    private int viewWidth;
    private int viewHeight;

    public ColorSelectorView(Context context) {
        this(context,null);
    }

    public ColorSelectorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorSelectorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mColors = new int[]{0xFFFF0000, 0xFFFF00FF,
                0xFF0000FF, 0xFF00FFFF, 0xFF00FF00,
                0xFFFFFF00, 0xFFFF0000};
        SweepGradient s = new SweepGradient(0, 0, mColors, null);

        //画外部颜色选择圆环
        mColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColorPaint.setShader(s);
        mColorPaint.setStyle(Paint.Style.STROKE);
        mColorPaint.setStrokeWidth(50);

        //画中心的圆
        mCenterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCenterPaint.setColor(Color.RED);
        mCenterPaint.setStrokeWidth(15);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(CENTER_X * 2,CENTER_Y * 2);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(viewWidth/2,viewHeight/2);
        canvas.drawCircle(0,0,CENTER_RADIUS * 6,mColorPaint);
        canvas.drawCircle(0,0,CENTER_RADIUS,mCenterPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX() - viewWidth/2;
        float y = event.getY() - viewHeight/2;
        //判断当前触摸的位置是否在圆环内部

        boolean isInRing = Math.sqrt(x * x + y * y) <= (CENTER_RADIUS*6);
        Log.e(Constants.TAG,"onTouchEvent,x = "+x+",y = "+y+",CENTER_RADIUS = "+CENTER_RADIUS+
                ",Math.sqrt(x * x + y * y) = "+Math.sqrt(x * x + y * y));

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                Log.e(Constants.TAG,"ACTION_DOWN");
                if (isInRing){
                    setCenterPaintColor(x,y);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isInRing&&mListener!=null){
                    mListener.colorChanged(mCenterPaint.getColor());
                }
                invalidate();
                break;

        }
        return true;
    }

    private void setCenterPaintColor(float x, float y) {
        float angle = (float) Math.atan2(y, x);
        //计算得到江都相对于整个圆的比例
        float unit = angle / (2 * PI);
        if (unit < 0){
            unit += 1;
        }
        mCenterPaint.setColor(interpColor(mColors,unit));
        invalidate();
    }

    /**
     * 根据颜色数组和当前触摸点所在的比例计算得到颜色值
     * @param colors    颜色数组
     * @param unit      当前触摸点的比例
     * @return
     */
    private int interpColor(int[] colors, float unit) {
        if (unit <= 0){
            return colors[0];
        }
        if (unit >= 1){
            return colors[colors.length - 1];
        }
        float p = unit * (colors.length -1);
        int i = (int)p;
        p -= i;
        int c0 = colors[i];
        int c1 = colors[i + 1];
        int a = ave(Color.alpha(c0),Color.alpha(c1),p);
        int r = ave(Color.red(c0),Color.red(c1),p);
        int g = ave(Color.green(c0),Color.green(c1),p);
        int b = ave(Color.blue(c0),Color.blue(c1),p);
        return Color.argb(a,r,g,b);
    }

    /**
     * 计算两个值之间的指定比例的值
     * 假设开始值为0，结束值为1；比例为0时，结果为0；比例为1时，结果为1；比例为0.5时，结果就是0.5
     * @param s 区域的开始值
     * @param d 区域的结束值
     * @param p 比例
     * @return
     */
    private int ave(int s, int d, float p) {
        return s + Math.round(p * (d-s));
    }

    public interface OnColorChangedListener{
        void colorChanged(int color);
    }

    public void setListener(OnColorChangedListener listener) {
        mListener = listener;
    }
}
