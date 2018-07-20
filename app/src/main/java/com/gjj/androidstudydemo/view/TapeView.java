package com.gjj.androidstudydemo.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.health.PackageHealthStats;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.gjj.androidstudydemo.R;
import com.gjj.androidstudydemo.utils.DisplayUtil;
import com.gjj.androidstudydemo.utils.DpUtil;

import java.util.logging.Logger;

public class TapeView extends View {
    private static final String TAG = TapeView.class.getSimpleName();
    private Context mContext;
    private int bgColor = Color.parseColor("#FBE40C");
    private int calibrationColor = Color.WHITE;
    private float calibrationWidth = 1.0f;              //刻度线的宽度
    private float calibrationLong = 35;                 //长的刻度线的高度
    private float calibrationShort = 20;                     //短的刻度线的长度
    private int triangleColor = Color.WHITE;            //
    private float triangleHeight = 18.0f;
    private int textColor = Color.WHITE;
    private float textSize = 14.0f;
    private float per = 1;                              //每一格代表的值
    private int perCount = 10;                          //两条长的刻度之间的per数量
    private float gapWidth = 10.0f;                     //两个刻度之间的距离
    private float minValue = 0;                         //刻度尺最小值
    private float maxValue = 100;                       //刻度尺最大值
    private float value = 0;                            //刻度当前值
    private float minFlingVelocity;         //被认为是快速滑动最小速度
    private Scroller scroller;
    private Paint paint;
    private float textY;
    private float offset;                               //当前刻度与最小值的距离（minValue-value）/per*gapWidth
    private float maxOffset;                            //当前刻度与最小值的最大距离（minValue - maxValue）/per * gapWidth
    private int totalCalibration;                       //总的刻度数量
    private int width;
    private int middle;
    private VelocityTracker velocityTracker;            //速度追踪器
    private float lastX;
    private float dx;
    private OnValueChangeListener mOnValueChangeListener;
    public interface OnValueChangeListener{
       void onChange(float value);
    }

    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        mOnValueChangeListener = onValueChangeListener;
    }

    public TapeView(Context context) {
        this(context, null);
    }

    public TapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG,"TapeView()");

        mContext = context;
        initAttrs(attrs);
        init();
        calculateAttr();
    }

    private void calculateAttr() {
        verifyValues(minValue, value, maxValue);
        textY = calibrationLong + DisplayUtil.dp2px(mContext, 30);
        offset = (value - minValue) * 10.0f / per * gapWidth;
        maxOffset = (maxValue - minValue) * 10.0f / per * gapWidth;
        totalCalibration = (int) ((maxValue - minValue) * 10.0f / per + 1);
    }


    private void init() {
        minFlingVelocity = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
        scroller = new Scroller(mContext);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
    }

    /**
     * 读取布局文件中的自定义属性
     *
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.TapeView);
        bgColor = ta.getColor(R.styleable.TapeView_bgColor, bgColor);
        calibrationColor = ta.getColor(R.styleable.TapeView_calibrationColor, calibrationColor);
        calibrationWidth = ta.getDimension(R.styleable.TapeView_calibrationWidth, DisplayUtil.dp2px(mContext, calibrationWidth));
        calibrationLong = ta.getDimension(R.styleable.TapeView_calibrationLong, DisplayUtil.dp2px(mContext, calibrationLong));
        calibrationShort = ta.getDimension(R.styleable.TapeView_calibrationShort, DisplayUtil.dp2px(mContext, calibrationShort));
        triangleColor = ta.getColor(R.styleable.TapeView_triangleColor, triangleColor);
        triangleHeight = ta.getDimension(R.styleable.TapeView_triangleHeight, DisplayUtil.dp2px(mContext, triangleHeight));
        textColor = ta.getColor(R.styleable.TapeView_textColor, textColor);
        textSize = ta.getDimension(R.styleable.TapeView_textSize, DisplayUtil.sp2px(mContext, textSize));
        per = ta.getFloat(R.styleable.TapeView_per, per);
        per *= 10;
        perCount = ta.getInt(R.styleable.TapeView_perCount, perCount);
        gapWidth = ta.getDimension(R.styleable.TapeView_gapWidth, DisplayUtil.dp2px(mContext, gapWidth));
        minValue = ta.getFloat(R.styleable.TapeView_minValue, minValue);
        maxValue = ta.getFloat(R.styleable.TapeView_maxValue, maxValue);
        value = ta.getFloat(R.styleable.TapeView_value, value);
        ta.recycle();
    }

    /**
     * 修正minValue、value、maxValue的有效性
     *
     * @param minValue
     * @param value
     * @param maxValue
     */
    private void verifyValues(float minValue, float value, float maxValue) {
        if (minValue > maxValue) {
            this.minValue = maxValue;
        }
        if (value < minValue) {
            this.value = minValue;
        }
        if (value > maxValue) {
            this.value = maxValue;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG,"onMeasure");
        width = MeasureSpec.getSize(widthMeasureSpec);
        middle = width / 2;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        if (mode == MeasureSpec.AT_MOST) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    (int) DisplayUtil.dp2px(mContext, 80), MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(bgColor);
        drawCalibration(canvas);
        drawTriangle(canvas);
    }

    /**
     * 由于没有画三角形的api,我们需要通过path来构造
     *
     * @param canvas
     */
    private void drawTriangle(Canvas canvas) {
        paint.setColor(triangleColor);
        Path path = new Path();
        path.moveTo(getWidth() / 2 - triangleHeight / 2, 0);
        path.lineTo(getWidth() / 2, triangleHeight / 2);
        path.lineTo(getWidth() / 2 + triangleHeight / 2, 0);
        path.close();
        canvas.drawPath(path, paint);
    }

    /**
     * 在画的时候首先找到第一个刻度画的x坐标，接着加上gapWidth接着画下一根
     * 当x超出当前View宽度则停止
     */
    private void drawCalibration(Canvas canvas) {

        //当前画的刻度的位置
        float currentCalibration;
        float height;
        String value;

        //计算出左边第一个刻度，直接跳过前面不需要画的可读
        int distance = (int) (middle - offset);
        int left = 0;
        if (distance < 0) {
            left = (int) (-distance / gapWidth);
        }
        currentCalibration = middle - offset + left * gapWidth;
        while (currentCalibration < width * 10 && left < totalCalibration) {

            //边缘的一根刻度不画
            if (currentCalibration == 0) {
                left++;
                currentCalibration = middle - offset + left * gapWidth;
                continue;
            }
            if (left % perCount == 0) {
                //长的刻度宽度是短的两倍
                paint.setStrokeWidth(calibrationWidth * 2);
                height = calibrationLong;

                value = String.valueOf(minValue + left * per / 10.0f);
                if (value.endsWith(".0")) {
                    value = value.substring(0, value.length()-2);
                }
                paint.setColor(textColor);
                canvas.drawText(value, currentCalibration - paint.measureText(value) / 2, textY, paint);
            } else {
                paint.setStrokeWidth(calibrationWidth);
                height = calibrationShort;
            }

            paint.setColor(calibrationColor);
            canvas.drawLine(currentCalibration, 0, currentCalibration, height, paint);

            left++;
            currentCalibration = middle - offset + left * gapWidth;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
        float x = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                scroller.forceFinished(true);
                lastX = x;
                dx = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                //dx表示滑动距离，这种计算方式，左-->右 dx小于0，所以重新计算offset时要加上dx
                dx = lastX - x;
                validateValue();
                break;
            case MotionEvent.ACTION_UP:
                //当滑动技术时如果三角形指示器不是在刻度上，要继续滑动让他们对其
                smoothMoveToCalibration();
                calculateVelocity();
                return false;
            default:
                return false;
        }
        lastX = x;
        return true;

    }

    /**
     * 滑动结束后，若是指针在2条刻度之间时，需要让指针指向最近的刻度
     */
    private void smoothMoveToCalibration() {
        offset += dx;
        if (offset < 0){
            offset = 0;
        }else if (offset > maxOffset){
            offset = maxOffset;
        }
        lastX = 0;
        dx = 0;
        value = minValue +Math.round(Math.abs(offset) / gapWidth) * per /10.f;
        offset = (value - minValue) * 10.f /per * gapWidth;
        if (mOnValueChangeListener != null){
            mOnValueChangeListener.onChange(value);
        }
        postInvalidate();
    }

    /**
     * 计算水平速度 像素/秒
     */
    private void calculateVelocity() {
        velocityTracker.computeCurrentVelocity(1000);
        float xVelocity = velocityTracker.getXVelocity();
        //大于这个值才会被认为时fling
        if (Math.abs(xVelocity) > minFlingVelocity){
            scroller.fling(0,0,(int)xVelocity,0,Integer.MIN_VALUE,
                    Integer.MAX_VALUE,0,0);
            invalidate();
            //真正的滑动是在调用invalidate方法之后
        }
    }

    /**
     * 根据滑动距离，重新计算offset,注意它的有效范围
     */
    private void validateValue() {
        offset += dx;
        if (offset < 0){
            offset = 0;
            dx = 0;
            scroller.forceFinished(true);
        }else if (offset > maxOffset){
            offset = maxOffset;
            dx = 0;
            scroller.forceFinished(true);
        }
        value = minValue + Math.round(Math.abs(offset)/gapWidth) * per/10.f;
        if (mOnValueChangeListener != null)
            mOnValueChangeListener.onChange(value);
        postInvalidate();
    }

    //滑动时调用
    @Override
    public void computeScroll() {
        super.computeScroll();
        //返回true表示滑动还没结束
        if (scroller.computeScrollOffset()){
            if (scroller.getCurrX() == scroller.getFinalX()){
                smoothMoveToCalibration();
            }else {
                int x = scroller.getCurrX();
                dx = lastX -x;
                validateValue();
                lastX = x;
            }
        }
    }

    public void setValue(float value,float minValue,float maxValue,float per,int perCount) {
        this.value = value;
        this.minValue = minValue;
        this.maxValue = maxValue;
        //浮点数在计算容易丢失精度，放大10倍
        this.per = per *10.0f;
        this.perCount = perCount;
        calculateAttr();
        invalidate();
    }

    public float getValue() {
        return value;
    }
}
