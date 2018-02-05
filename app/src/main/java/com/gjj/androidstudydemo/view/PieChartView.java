package com.gjj.androidstudydemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.gjj.androidstudydemo.bean.PieChartBean;
import com.gjj.androidstudydemo.utils.DpUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaojuanjuan on 2018/1/24.
 */

public class PieChartView extends View {
    private  Context mContext;
    private ArrayList<PieChartBean> beanList;
    private RectF mRectF;
    private Paint mPaint;
    private int mRWidth;
    private int mRHeight;
    private int diameter;
    private float sumValue = 0;
    private float rotateDegree;//每个圆弧的起始角度
    private RectF iRectF;
    private int mMargin = 40;//矩形和圆的距离
    private int mRectWidth = 100;//矩形宽度
    private int textY;//绘制文字的y坐标
    private int mRectHeight = 50;//矩形高度

    public PieChartView(Context context) {
        //在代码中new PieChartView会调用这个构造函数
        this(context,null);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        //InflateLayoutManager时会调用这个构造函数
        this(context, attrs, 0);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        beanList = new ArrayList<>();
        mRectF = new RectF();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (wMode){
            case MeasureSpec.EXACTLY:
                //相当于match_parent或者一个具体值
                mRWidth = wSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED://很少会用到不考虑
                //相当于wrap_content
                mRWidth = (int) DpUtil.dp2px(mContext,400f);
                break;
        }
         switch (hMode){
            case MeasureSpec.EXACTLY:
                //相当于match_parent或者一个具体值
                mRHeight = hSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED://很少会用到不考虑
                //相当于wrap_content
                mRHeight = (int) DpUtil.dp2px(mContext,200f);
                break;
        }

        setMeasuredDimension(mRWidth,mRHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        diameter = Math.min(mRWidth,mRHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRectF.set(0,0,diameter,diameter);
        canvas.translate((mRWidth-diameter)/8,(mRHeight - diameter) / 2);

        if (beanList.size() > 0 && Float.compare(sumValue,0.0f) != 0){
            for (int i = 0; i < beanList.size(); i++) {
                PieChartBean pieChartBean = beanList.get(i);
                //画圆弧
                mPaint.setColor(pieChartBean.mColor);
                canvas.drawArc(mRectF,rotateDegree,pieChartBean.degree,true,mPaint);
                rotateDegree += pieChartBean.degree;
                //画矩形和文字
                drawRectAndText(canvas,pieChartBean);
            }
        }
        //绘制完之后必须要让textY置为0，否则会有问题的
        textY = 0;
    }

    private void drawRectAndText(Canvas canvas, PieChartBean pieChartBean) {
        iRectF = new RectF();
        //设置画矩形的范围
        float left = diameter + mMargin;
        float right = diameter + mMargin + mRectWidth;
        float bottom = textY + mRectHeight;
        iRectF.set(left,textY,right,bottom);
        canvas.drawRect(iRectF,mPaint);
        //设置颜色
        mPaint.setColor(pieChartBean.mColor);
        //设置文字大小
        mPaint.setTextSize(40);
        //画文字
        canvas.drawText(pieChartBean.name + "("+new DecimalFormat(".00").format(pieChartBean.value/sumValue*100)+"%)"
                ,right + 10, textY + 40, mPaint);
        textY += mRectHeight;
    }

    /**
     * 饼状图添加数据
     * @param list
     */
    public void setData(List<PieChartBean> list){
        if (list == null || list.size()<=0)
            return;
        //计算总值
        for (int i = 0; i < list.size(); i++) {
            PieChartBean pieChartBean = list.get(i);
            sumValue += pieChartBean.value;
        }
        //计算每条数据的degree
        for (int i = 0; i < list.size(); i++) {
            PieChartBean pieChartBean = list.get(i);
            pieChartBean.degree = pieChartBean.value / sumValue * 360;
            beanList.add(pieChartBean);
        }
        invalidate();
    }

    /**
     * 设置绘制圆弧的起始角度
     * @param startDegree
     */
    public void setStartDegree(float startDegree){
        this.rotateDegree = startDegree;
        invalidate();
    }
}
