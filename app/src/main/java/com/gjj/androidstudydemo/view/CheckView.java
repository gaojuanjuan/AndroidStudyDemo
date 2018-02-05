package com.gjj.androidstudydemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.gjj.androidstudydemo.R;
import com.gjj.androidstudydemo.utils.Constants;

/**
 * Created by gaojuanjuan on 2017/12/31.
 */

public class CheckView extends View {
    public static final int ANIM_NULL = 0;      //动画状态--没有
    public static final int ANIM_CHECK = 1;     //动画状态--开启
    public static final int ANIM_UNCHECK = 2;   //动画状态--结束
    private int animCurrentPage = -1;           //当前页码
    private int animMaxPage = 13;               //总页码数
    private int animDuration = 500;             //动画时长
    private int animState = ANIM_NULL;          //动画状态
    private Context mContext;
    private Paint mPaint;
    private Handler mHandler;
    private int mWidth;
    private int mHeight;
    private Bitmap mBitmap;

    private boolean isCheck = false;
    public CheckView(Context context) {
        super(context);
    }

    public CheckView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mPaint = new Paint();
        mPaint.setColor(Color.MAGENTA);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.checkres);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (animCurrentPage < animMaxPage && animCurrentPage >= 0){
                    invalidate();
                    if (animState == ANIM_NULL)
                        return;
                    if (animState == ANIM_CHECK)
                        animCurrentPage++;
                    else if (animState == ANIM_UNCHECK)
                        animCurrentPage--;
                    this.sendEmptyMessageDelayed(0,animDuration/animMaxPage);
                    Log.e(Constants.TAG,"animCurrentPage = "+animCurrentPage);
                }else {
                    if (isCheck)
                        animCurrentPage = animMaxPage -1;
                    else {
                        animCurrentPage = -1;
                    }
                    invalidate();
                    animState = ANIM_NULL;
                }
            }
        };
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth/2,mHeight/2);
        canvas.drawCircle(0,0,240,mPaint);
        int sideLength = mBitmap.getHeight();
        Rect src = new Rect(sideLength * animCurrentPage, 0, sideLength * (animCurrentPage + 1), sideLength);
        Rect dst = new Rect(-200, -200, 200, 200);
        canvas.drawBitmap(mBitmap,src,dst,null);
    }

    public void check(){
        if (animState != ANIM_NULL || isCheck)
            return;
        animState = ANIM_CHECK;
        animCurrentPage = 0;
        mHandler.sendEmptyMessageDelayed(0,animDuration/animMaxPage);
        isCheck = true;
    }

    public void unCheck(){
        if (animState != ANIM_NULL ||(!isCheck))
            return;
        animState = ANIM_UNCHECK;
        animCurrentPage = animMaxPage - 1;
        mHandler.sendEmptyMessageDelayed(0,animDuration/animMaxPage);
        isCheck = false;
    }

    public void setAnimDuration(int animDuration){
        if (animDuration <0)
            return;
        this.animDuration = animDuration;
    }

    public void setBackgroundColor(int color){
        mPaint.setColor(color);
    }
}
