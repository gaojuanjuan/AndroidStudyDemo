package com.gjj.androidstudydemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.gjj.androidstudydemo.bean.SquareBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 高娟娟 on 2018/3/28.
 */

public class SquareView extends View {
    private Paint mPaint;
    private int squareMargin = 20;
    private int squareLength = 100;
    private List<SquareBean> mSquareBeanList;
    private int firstMarginL;
    private int lineNum = 15;
    private Paint textPaint;
    private Context mContext;
    private int viewHeigth;
    public SquareView(Context context) {
        this(context,null);
    }

    public SquareView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);

    }

    public SquareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(40);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //计算正方形之间的间距
        firstMarginL = (w - (lineNum * squareLength) - (lineNum -1)*squareMargin)/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("gjj","重新绘制了");
        if (mSquareBeanList==null || mSquareBeanList.size()==0)
            return;
        canvas.translate(firstMarginL,0);
        for (int i = 0; i < mSquareBeanList.size(); i++) {
            //计算绘制矩形的位置
            RectF rectF = new RectF(
                    squareMargin+(i%lineNum)*(squareLength+squareMargin),
                    squareMargin+(squareLength+squareMargin)*(i/lineNum),
                    (squareMargin+squareLength)*(i % lineNum + 1),
                    (squareMargin+squareLength)*((i+lineNum)/lineNum));
            if (mSquareBeanList.get(i).getState() == 0){//如果是加号，颜色是绿色
                mPaint.setColor(Color.GREEN);
            }else {//如果是减号，颜色是红色
                mPaint.setColor(Color.RED);
            }
            //绘制矩形
            canvas.drawRect(rectF,mPaint);
            mPaint.setColor(Color.WHITE);
            Rect rect = new Rect();
            textPaint.getTextBounds(mSquareBeanList.get(i).getId()+"", 0,
                    (mSquareBeanList.get(i).getId()+"").length(), rect);
            //绘制正方向上的文字
            canvas.drawText(mSquareBeanList.get(i).getId()+"",
                    squareMargin+(i%lineNum)*(squareLength+squareMargin) + squareLength/2-rect.width()/2,
                    (squareMargin+squareLength)*((i+lineNum)/lineNum)-squareLength/2+rect.height()/2,textPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);

        //使用该自定义view时请设置view的宽度为match_parent
//        switch (wMode){
//            case MeasureSpec.EXACTLY:
//                //相当于match_parent或者一个具体值
//                break;
//            case MeasureSpec.AT_MOST:
//            case MeasureSpec.UNSPECIFIED://很少会用到不考虑
//                //相当于wrap_content
//                viewWidth = (int) DpUtil.dp2px(mContext,400f);
//                break;
//        }
        //使用该自定义view时请设置view的宽度为wrap_content
        switch (hMode){
            case MeasureSpec.EXACTLY:
                //相当于match_parent或者一个具体值
                viewHeigth = hSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED://很少会用到不考虑
                //相当于wrap_content
                if (mSquareBeanList != null){
                    viewHeigth = 140 + (mSquareBeanList.size()/lineNum)*120;
                }else {
                    viewHeigth = 140;
                }
                break;
        }
        setMeasuredDimension(wSize,viewHeigth);
    }
    public void addAllData(List<SquareBean> list){
        mSquareBeanList = list;
        postInvalidate();
    }

    /**
     * 该方法是对外暴露的，点击加号和减号是调用的
     * @param id            数据条目和正方形条目之间的对应
     * @param isClickAdd    点击加号时，isClickAdd设置为true，点击减号时，isClickAdd设置为false
     */
    public void addData(int id,boolean isClickAdd){
        if (mSquareBeanList == null){
            mSquareBeanList = new ArrayList<>();
            if (isClickAdd){
                mSquareBeanList.add(new SquareBean(id,0));
            }else {
                mSquareBeanList.add(new SquareBean(id,1));
            }
        } else if (mSquareBeanList.size() == 0) {
            if (isClickAdd){
                mSquareBeanList.add(new SquareBean(id,0));
            }else {
                mSquareBeanList.add(new SquareBean(id,1));
            }
        }else {
            boolean isExist = false;
            int position = 0;
            for (int i = 0; i < mSquareBeanList.size(); i++) {
                if (id == mSquareBeanList.get(i).getId()){
                    isExist = true;
                    position = i;
                    break;
                }
            }
            if (!isExist){//不存在的时候
                if (isClickAdd){
                    mSquareBeanList.add(new SquareBean(id,0));
                }else {
                    mSquareBeanList.add(new SquareBean(id,1));
                }
            }else if (isClickAdd && mSquareBeanList.get(position).getState() != 0){//点击了加号，存在且是减号的时候
                mSquareBeanList.get(position).setState(0);
            }else if (!isClickAdd && mSquareBeanList.get(position).getState() != 1){//点击了减号，存在且是加号的时候
                mSquareBeanList.get(position).setState(1);
            }else {//存在且是加号的时候，不需要重新绘制界面
                return;
            }
        }
        if (mSquareBeanList != null &&mSquareBeanList.size()>lineNum && mSquareBeanList.size() % lineNum == 1){
            requestLayout();
        }else {
            postInvalidate();
        }

    }

}
