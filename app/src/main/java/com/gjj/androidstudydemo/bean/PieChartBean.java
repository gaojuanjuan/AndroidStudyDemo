package com.gjj.androidstudydemo.bean;

/**
 * Created by gaojuanjuan on 2018/1/24.
 */

public class PieChartBean {
    public int mColor;

    public float degree;//自定义view中用到的，构建实例的时候不需要赋值
    public String name;
    public float value;

    public PieChartBean(int color, String name, float value) {
        mColor = color;
        this.degree = degree;
        this.name = name;
        this.value = value;
    }
}
