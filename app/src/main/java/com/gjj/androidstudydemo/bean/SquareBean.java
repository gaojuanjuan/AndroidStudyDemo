package com.gjj.androidstudydemo.bean;

/**
 * Created by 高娟娟 on 2018/3/28.
 */

public class SquareBean {
    private int id;
    private int state;//0代表+，1代表-

    public SquareBean() {
    }

    public SquareBean(int i) {
        this.id = i;
    }

    public SquareBean(int id, int state) {
        this.id = id;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
