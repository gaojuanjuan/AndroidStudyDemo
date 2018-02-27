package com.gjj.androidstudydemo.bean;

/**
 * Created by gaojuanjuan on 2018/1/24.
 */

public class PieChartBean {
    public int mColor;      //填充的颜色值

    public float sweepAngle;    //绘制圆弧是用到的sweepAngle，构建实例的时候不需要赋值
    public String name;         //文字的内容
    public float value;         //具体数值，用来计算sweepAngle和文字右侧的数值

    public PieChartBean(int color, String name, float value) {
        mColor = color;
        this.name = name;
        this.value = value;
    }
}
