package com.gjj.androidstudydemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.TextView;

import com.gjj.androidstudydemo.R;
import com.gjj.androidstudydemo.utils.DpUtil;


/**
 * Created by 高娟娟 on 2018/4/3.
 */

public class WaveProgressView extends View {

    private float waveWidth;        //波浪宽度
    private float waveHeight;       //波浪高度
    private Path wavePath;          //绘制波浪path
    private int waveNum;            //波浪组的数量（一次起伏为一组）
    private int defaultSize;        //自定义View默认的宽高
    private int viewSize;           //重新测量后View的实际高度
    private float percent;
    private float progressNum;      //可以更新的进度条数值
    private float maxNum;           //进度条最大值
    private WaveProgressAnim mWaveProgressAnim;

    private float waveMovingDistance;//波浪平移的距离
    private Paint circlePaint;      //圆形进度条画笔
    private Paint wavePaint;        //绘制波浪画笔
    private Paint secondWavePaint;  //绘制第二个波浪的画笔

    private Bitmap mBitmap;         //缓存Bitmap
    private Canvas bitmapCanvas;
    private int waveColor;          //波浪颜色
    private int bgColor;            //背景进度框颜色
    private int secondWaveColor;    //第二层波浪的颜色

    //添加文字
    private TextView textView;
    private OnAnimationListener onAnimationListener;

    //绘制双波浪效果
    private boolean isDrawSecondWave;

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public interface OnAnimationListener {
        /**
         * 如何处理要显示的文字内容
         * @param interpolatedTime 从0渐变成1，到1时结束动画
         * @param updateNum         进度条数值
         * @param maxNum            进度条最大数值
         * @return
         */
        String howToChangeText(float interpolatedTime,float updateNum,float maxNum);

        /**
         * 如何处理波浪高度
         * @param percent   进度占比
         * @param waveHeight    波浪高度
         * @return
         */
        float howToChangeWaveHeight(float percent,float waveHeight);
    }

    public void setOnAnimationListener(OnAnimationListener onAnimationListener) {
        this.onAnimationListener = onAnimationListener;
    }

    public WaveProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveProgressView);
        waveWidth = typedArray.getDimension(R.styleable.WaveProgressView_wave_width,DpUtil.dip2px(context,40));
        waveHeight = typedArray.getDimension(R.styleable.WaveProgressView_wave_height,DpUtil.dip2px(context,8));
        waveColor = typedArray.getColor(R.styleable.WaveProgressView_wave_color, Color.GREEN);
        bgColor = typedArray.getColor(R.styleable.WaveProgressView_wave_bg_color, Color.GRAY);
        secondWaveColor = typedArray.getColor(R.styleable.WaveProgressView_second_wave_color, getResources().getColor(R.color.light));

        typedArray.recycle();
        defaultSize = DpUtil.dip2px(context,200);

        percent = 0;
        progressNum = 0;
        maxNum = 100;
        mWaveProgressAnim = new WaveProgressAnim();
        // 如果需要让波浪到达最高处后平移的速度改变，给动画设置监听即可
        mWaveProgressAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
//                if (percent == progressNum / maxNum){
//                    mWaveProgressAnim.setDuration(8000);
//                }
            }
        });
        waveMovingDistance = 0;
        wavePath = new Path();

        wavePaint = new Paint();
        wavePaint.setColor(waveColor);
        wavePaint.setAntiAlias(true);

        wavePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        circlePaint = new Paint();
        circlePaint.setColor(bgColor);
        circlePaint.setAntiAlias(true);

        secondWavePaint = new Paint();
        secondWavePaint.setColor(secondWaveColor);
        secondWavePaint.setAntiAlias(true);
        //因为要覆盖在第一层波浪上，且要让半透明生效，所以选SRC_ATOP模式
        secondWavePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        isDrawSecondWave = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBitmap = Bitmap.createBitmap(viewSize, viewSize, Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(mBitmap);
        bitmapCanvas.drawCircle(viewSize/2,viewSize/2,viewSize/2,circlePaint);
        bitmapCanvas.drawPath(getWavePath(),wavePaint);
        if (isDrawSecondWave){
            bitmapCanvas.drawPath(getSecondWavePath(),secondWavePaint);
        }
        canvas.drawBitmap(mBitmap,0,0,null);
    }

    private Path getSecondWavePath() {
        float changeWaveHeight = waveHeight;
//        if (onAnimationListener != null){
//            changeWaveHeight  = onAnimationListener.howToChangeWaveHeight(percent,waveHeight) == 0 && percent <1?
//                    waveHeight:onAnimationListener.howToChangeWaveHeight(percent,waveHeight);
//        }
        wavePath.reset();
        //移动到左上方，也就是p3
        wavePath.moveTo(0,(1-percent)*viewSize);
        //移动到右下方，也就是p2点
        wavePath.lineTo(0,viewSize);
        //移动到右下方，也就是p1点
        wavePath.lineTo(viewSize,viewSize);
        //移动到右上方，也就是p0点
        wavePath.lineTo(viewSize + waveMovingDistance,(1-percent) * viewSize);

        //从p0开始向p3方向绘制波浪曲线（注意绘制二阶贝塞尔曲线控制点和终点x坐标的正负值）
        for (int i = 0; i < waveNum * 2; i++) {
            wavePath.rQuadTo(- waveWidth/2,changeWaveHeight,-waveWidth,0);
            wavePath.rQuadTo(-waveWidth/2,-changeWaveHeight,-waveWidth,0);
        }

        //将path封闭起来
        wavePath.close();
        return wavePath;
    }

    public void setDrawSecondWave(boolean drawSecondWave) {
        isDrawSecondWave = drawSecondWave;
    }

    private Path getWavePath(){
        wavePath.reset();
        //移动到右上方，p0点
        wavePath.moveTo(viewSize,(1-percent)*viewSize);//让p0p1的长度随percent的增加而增加（注意这里y轴方向默认是向下的）
        //移动到右下方，p1点
        wavePath.lineTo(viewSize,viewSize);
        //移动到左下方，p2点
        wavePath.lineTo(0,viewSize);
        //移动到左上方，p3点
//        wavePath.lineTo(0,(1-percent)*viewSize);//让p3p2的长度随percent的增加而增加（注意这里y轴方向默认是向下的）
        //移动到左上方，也就是p3点（x轴默认方向是向右的，我们要向左平移，因此设为负值）
        wavePath.lineTo(-waveMovingDistance,(1-percent)*viewSize);

        //实现波浪高度随进度上升而下降的效果
        float changeWaveHeight = waveHeight;
//        if (onAnimationListener != null){
//            changeWaveHeight = onAnimationListener.howToChangeWaveHeight(percent,waveHeight) == 0 && percent < 1
//                    ?waveHeight : onAnimationListener.howToChangeWaveHeight(percent,waveHeight);
//        }
        //从p3开始向p0方向绘制波浪曲线
        for (int i = 0; i < waveNum * 2; i++) {
            wavePath.rQuadTo(waveWidth/2,changeWaveHeight,waveWidth,0);
            wavePath.rQuadTo(waveWidth/2,-changeWaveHeight,waveWidth,0);

        }
        wavePath.close();
        return wavePath;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = measureSize(defaultSize, heightMeasureSpec);
        int width = measureSize(defaultSize, widthMeasureSpec);
        int min = Math.min(width,height);
        setMeasuredDimension(min,min);
        viewSize = min;
        //波浪的数量需要进一取整，所以使用Math.ceil函数
        waveNum = (int) Math.ceil(Double.parseDouble(String.valueOf(viewSize / waveWidth / 2)));
    }

    private int measureSize(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == View.MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }

    public class WaveProgressAnim extends Animation{
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (percent < progressNum/maxNum){
                percent = interpolatedTime * progressNum / maxNum;
                //显示文字
                if (textView != null && onAnimationListener != null){
                    textView.setText(onAnimationListener.howToChangeText(interpolatedTime,
                            progressNum,maxNum));
                }
            }
            waveMovingDistance = interpolatedTime * waveNum * waveWidth *2;
            postInvalidate();
        }
    }
    public void setProgressNum(float progressNum,int time){
        this.progressNum = progressNum;
        percent = 0;
        mWaveProgressAnim.setDuration(time);
        mWaveProgressAnim.setRepeatCount(Animation.INFINITE);
        mWaveProgressAnim.setInterpolator(new LinearInterpolator());//让动画匀速播放，不然会出现波浪平移停顿的现象
        this.startAnimation(mWaveProgressAnim);
    }

}
