package com.gjj.androidstudydemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by gaojuanjuan on 2018/1/26.
 */

public class LineChartView extends View {
    private Paint gridPaint;
    private Paint pathPaint;
    private Paint pathEndPaint;
    private int gridWidth = 50;

    public LineChartView(Context context) {
        this(context,null);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化背景网格画笔
        gridPaint = new Paint();
        gridPaint.setColor(0xee000000);
        gridPaint.setStrokeWidth(3);

        //初始化折现画笔
        pathPaint = new Paint();
        pathPaint.setAntiAlias(true);
        pathPaint.setColor(0xffcd0000);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth((float) 1.0);

        //初始化折返回路线画笔，用于做出渲染效果
        pathEndPaint = new Paint();
        pathEndPaint.setAntiAlias(true);
        pathEndPaint.setColor(0xffcd0000);
        pathEndPaint.setStyle(Paint.Style.FILL);

        //设置颜色渐变的渲染
        LinearGradient shader = new LinearGradient(0, 0, gridWidth * 10, gridWidth * 20, 0xffcd0000, 0x11cd6839,
                Shader.TileMode.CLAMP);
        pathEndPaint.setShader(shader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(40,200);
        //画竖线
        for (int i = 0; i <= 20; i++) {
            canvas.drawLine(gridWidth * i,0,gridWidth * i,gridWidth * 10,gridPaint);
        }
        //画横线
        for (int j = 0; j <= 10;j++) {
            canvas.drawLine(0,gridWidth * j,gridWidth * 20,gridWidth * j,gridPaint);
        }

        //画折现
        drawPath(canvas);

    }

    private void drawPath(Canvas canvas) {
        Path path = new Path();

        for (int i = 0; i < 20; i++) {
            if (i == 0){
                path.moveTo(0,0);
            }else {
                //折现的高度是一个随机数
                path.lineTo(gridWidth * i, gridWidth * ((int) (Math.random() * 10)));
            }
            canvas.drawPath(path,pathPaint);
        }
        path.lineTo(gridWidth * 20,gridWidth * 10);
        path.lineTo(0,gridWidth *10);
        path.close();
        canvas.drawPath(path,pathEndPaint);
    }
}
