package com.gjj.androidstudydemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by gaojuanjuan on 2018/2/5.
 */

public class BgForPath extends View {
    private int screenWidth;
    private int screenHeight;
    private Path path;
    private Paint paint;

    public BgForPath(Context context) {
        this(context,null);
    }

    public BgForPath(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BgForPath(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        path = new Path();
        path.moveTo(100,100);
        path.quadTo(screenWidth - 300, 200, screenWidth - 100, screenHeight - 600);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path,paint);
    }
}
