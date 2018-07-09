package com.gjj.androidstudydemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.gjj.androidstudydemo.bean.PieChartBean;

import java.util.List;

//https://www.jb51.net/article/106245.htm
public class PieChart2View extends View {

    private int mHeight, mWidth;                 //view的宽高
    private Paint mPaint;                       //扇形的画笔
    private Paint mTextPaint;                   //文字的画笔

    private int centerX, centerY;                //中心坐标
    private int radius = 1000;                  //半径
    private int mTextSize;                      //文字大小

    private List<PieChartBean> data;
    //默认的颜色
    private int[] mColors = {Color.parseColor("#FF4081"), Color.parseColor("#ffc0cb"),
            Color.parseColor("#00ff00"), Color.parseColor("#0066ff"), Color.parseColor("#ffee00")};
    private float total;

    private int linWidth = 50;

    public PieChart2View(Context context) {
        this(context, null);
    }

    public PieChart2View(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChart2View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();

        mTextPaint = new Paint();
        mTextPaint.setTextSize(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (data == null || data.size() == 0) return;

        centerX = (getRight() - getLeft()) / 2;
        centerY = (getBottom() - getTop()) / 2;

        int min = mHeight > mWidth ? mWidth : mHeight;

        if (radius > min / 2) {
            radius = (int) ((min - getPaddingTop() - getPaddingBottom()) / 3.5);
        }

        //绘制扇形
        drawSector(canvas);
        //绘制线与文字
        drawLineAndText(canvas);
    }

    //画线与文字
    private void drawLineAndText(Canvas canvas) {
        int start = 0;
        //平移画布到中心
        canvas.translate(centerX, centerY);
        mPaint.setStrokeWidth(4);
        for (int i = 0; i < data.size(); i++) {
            float angles = (float) ((data.get(i).value * 1.0f / total) * 360);
            if (i == data.size() - 1) {
                drawLine(canvas, start, 360 - start, data.get(i).name, Color.GRAY);
            } else {
                drawLine(canvas, start, angles, data.get(i).name, mColors[i % mColors.length]);
                start += angles;
            }
        }
    }

    private void drawLine(Canvas canvas, int start, float angles, String text, int color) {
        mPaint.setColor(color);
        float stopX, stopY;

        stopX = (float) ((radius + 40) * Math.cos((2 * start + angles) / 2 * Math.PI / 180));
        stopY = (float) ((radius + 40) * Math.sin((2 * start + angles) / 2 * Math.PI / 180));

        canvas.drawLine((float) ((radius - 20) * Math.cos((2 * start + angles) / 2 * Math.PI / 180)),
                (float) ((radius - 20) * Math.sin((2 * start + angles) / 2 * Math.PI / 180)),
                stopX, stopY, mPaint
        );

        //话横线
        int dx;//判断横线是画在左边还是右边
        int endX;
        if (stopX > 0) {
            endX = centerX - getPaddingRight() - linWidth;
        } else {
            endX = (-centerX + getPaddingLeft() + linWidth);
        }

        //画横线
        canvas.drawLine(stopX, stopY, endX, stopY, mPaint);

        dx = (int) (endX - stopX);
        Rect rect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        int w = rect.width();
        int h = rect.height();

        int offset = 20;
        canvas.drawText(text, 0, text.length(), dx > 0 ? stopX + offset : stopX - w - offset, stopY + h + 10, mTextPaint);
        //测量百分比大小
        String percentage = angles / 3.60 + "";
        percentage = percentage.substring(0, percentage.length() > 4 ? 4 : percentage.length()) + "%";
        mTextPaint.getTextBounds(percentage, 0, percentage.length(), rect);
        w = rect.width() - 10;
        canvas.drawText(percentage, 0, percentage.length(), dx > 0 ? stopX + offset : stopX - w - offset, stopY - 10, mTextPaint);
    }

    private void drawSector(Canvas canvas) {
        total = 0;
        for (int i = 0; i < data.size(); i++) {
            total += data.get(i).value;
        }
        int centerX = (getRight() - getLeft()) / 2;
        int centerY = (getBottom() - getTop()) / 2;
        //圆形区域
        RectF rectF = new RectF(centerX - radius, centerY - radius,
                centerX + radius, centerY + radius);
        int start = 0;//扇形开始角度

        for (int i = 0; i < data.size(); i++) {
            float angles = (float) ((data.get(i).value * 1.0f / total) * 360);
            mPaint.setColor(mColors[i % mColors.length]);
            if (i == data.size() - 1) {
                canvas.drawArc(rectF, start, 360 - start, true, mPaint);
            } else {
                canvas.drawArc(rectF, start, angles, true, mPaint);
                start += angles;
            }


        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取宽高，没要进行适配，view的宽高不要设置为wrap_content
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
    }

    public void setData(List<PieChartBean> data) {
        this.data = data;
        postInvalidate();
    }
}
