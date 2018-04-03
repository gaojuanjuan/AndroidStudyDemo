package com.gjj.androidstudydemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;

import com.gjj.androidstudydemo.R;
import com.gjj.androidstudydemo.utils.DpUtil;

/**
 * Created by 高娟娟 on 2018/4/3.
 */

public class CircleBarView extends View {

    private Paint rPaint;
    private Paint progressPaint;    //绘制进度条圆弧的画笔
    private Paint bgPaint;          //绘制背景圆弧的画笔


    private float progressSweepAngle;//进度条圆弧扫过的角度
    private float sweepAngle;       //背景圆弧扫过的角度
    private float startAngle;         //背景圆弧的起始角度

    private float progressNum;        //可以更新的进度条数值
    private int maxNum;             //进度条最大值

    private CircleBarAnim mAnim;

    private int defaultSize;//自定义view默认的宽高
    private float barWidth;
    private RectF mRectF;
    private int progressColor;
    private int bgColor;
    private TextView mTextView;
    private OnAnimationListener mOnAnimationListener;
    public CircleBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleBarView);
        progressColor = typedArray.getColor(R.styleable.CircleBarView_progress_color, Color.GREEN);
        bgColor = typedArray.getColor(R.styleable.CircleBarView_bg_color, Color.GRAY);
        startAngle = typedArray.getFloat(R.styleable.CircleBarView_start_angle, 0);
        sweepAngle = typedArray.getFloat(R.styleable.CircleBarView_sweep_angle,360);
        barWidth = typedArray.getDimension(R.styleable.CircleBarView_bar_width,DpUtil.dip2px(context,10));
        typedArray.recycle();
        mAnim = new CircleBarAnim();

        rPaint = new Paint();
        rPaint.setStyle(Paint.Style.STROKE);//只描边不填充
        rPaint.setColor(Color.RED);

        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setColor(progressColor);
        progressPaint.setAntiAlias(true);//设置抗锯齿
        progressPaint.setStrokeWidth(barWidth);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);

        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setColor(bgColor);
        bgPaint.setAntiAlias(true);
        bgPaint.setStrokeWidth(barWidth);
        bgPaint.setStrokeCap(Paint.Cap.ROUND);//设置线头形状

        progressNum = 0;
        maxNum = 100;
        defaultSize = DpUtil.dip2px(context,200);
        mRectF = new RectF();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mRectF,startAngle,sweepAngle,false,bgPaint);
        canvas.drawArc(mRectF,startAngle,progressSweepAngle,false,progressPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = measureSize(defaultSize, heightMeasureSpec);
        int width = measureSize(defaultSize, widthMeasureSpec);
        int min = Math.min(width,height);//获取view最短边的长度
        setMeasuredDimension(min,min);//强制改View为以最短边为长度的正方形
        if (min >= barWidth * 2){
            mRectF.set(barWidth/2,barWidth/2,min - barWidth/2,min- barWidth/2);
        }
    }

    private int measureSize(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }else if (specMode == MeasureSpec.AT_MOST){
            result = Math.min(result,specSize);
        }
        return result;

    }

    public class CircleBarAnim extends Animation{
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            progressSweepAngle = interpolatedTime * sweepAngle * progressNum / maxNum;
            postInvalidate();

            if (mOnAnimationListener != null){
                if (mTextView != null){
                    mTextView.setText(mOnAnimationListener.howToChangeText(interpolatedTime,progressNum,maxNum));
                }
                mOnAnimationListener.howTiChangeProgressColor(progressPaint,interpolatedTime,progressNum,maxNum);

            }
        }
    }
    //给外部调用，用来设置动画时间
    public void setProgressNum(float progressNum,int time){
        mAnim.setDuration(time);
        this.startAnimation(mAnim);
        this.progressNum = progressNum;
    }

    public void setTextView(TextView textView) {
        mTextView = textView;
    }

    public interface  OnAnimationListener{
        /**
         * 如何处理要显示的文字内容
         * @param interpolatedTime
         * @param progressNum
         * @param maxNum
         * @return
         */
        String howToChangeText(float interpolatedTime,float progressNum,float maxNum);

        /**
         * 如何处理进度条的颜色
         * @param paint             进度条画笔
         * @param interpolatedTime
         * @param progressNum
         * @param maxNum
         */
        void howTiChangeProgressColor(Paint paint,float interpolatedTime,float progressNum,float maxNum);
    }

    public void setOnAnimationListener(OnAnimationListener onAnimationListener) {
        mOnAnimationListener = onAnimationListener;
    }
}
