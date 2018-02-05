package com.gjj.androidstudydemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.gjj.androidstudydemo.R;


/**
 * Created by gaojuanjuan on 2018/1/28.
 */

public class ComposeShaderView extends View {
    private Paint mPaint;

    private int bmpWidth = 600;
    private int bmpHeight = 600;
    private int gap = 5;
    private int reflectionHeight = bmpHeight/2;
    private Bitmap mBitmap;


    public ComposeShaderView(Context context) {
        this(context,null);
    }

    public ComposeShaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ComposeShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.aa);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Bitmap result = mBitmap.createScaledBitmap(mBitmap, bmpWidth, bmpHeight, false);
        canvas.drawBitmap(result,0,0,null);
        canvas.save();
        canvas.translate(0,bmpHeight+gap);
        Matrix m = new Matrix();
        m.postScale(-1f,1f);
        m.postRotate(-180);
        Bitmap texture = Bitmap.createBitmap(result,0,0,result.getWidth(),result.getHeight(),m,false);
        //创建BitmapShader和LinearShader。
        BitmapShader bitmapShader = new BitmapShader(texture, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        LinearGradient linearGradient = new LinearGradient(0,0,0,reflectionHeight,Color.BLACK,Color.TRANSPARENT, Shader.TileMode.CLAMP);
        ComposeShader composeShader = new ComposeShader(bitmapShader,linearGradient,new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setShader(composeShader);
        //以混合模式绘制矩形区域，可以获得倒影效果。
        canvas.drawRect(0,0,bmpWidth,reflectionHeight,mPaint);

        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
