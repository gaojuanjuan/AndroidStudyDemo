package com.gjj.androidstudydemo.view;

import android.content.Context;
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

import com.gjj.androidstudydemo.R;
import com.gjj.androidstudydemo.bean.MyPoint;

/**
 * Created by 高娟娟 on 2018/5/2.
 */

public class BookPageView extends View {
    private int defaultWidth;
    private int defaultHeight;
    private int viewHeight;
    private int viewWidth;

    private MyPoint a, b, c, d, e, f, g, h, i, j, k;
    private Paint pointPaint;
    private Paint bgPaint;
    private Paint pathCPaint;
    private Path pathC;
    private Bitmap mBitmap;
    private Canvas bitmapCanvas;
    private Paint pathAPaint;
    private Path pathA;
    private Paint pathBPaint;
    private Path pathB;

    public static final String STYLE_TOP_RIGHT = "STYLE_TOP_RIGHT";//f点在右上角
    public static final String STYLE_LOWER_RIGHT = "STYLE_LOWER_RIGHT";//f点在右下角

    public BookPageView(Context context) {
        super(context);
    }

    public BookPageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        defaultWidth = 600;
        defaultHeight = 1000;

        viewHeight = defaultHeight;
        viewWidth = defaultWidth;

//        a = new MyPoint(400, 800);
//        f = new MyPoint(viewWidth, viewHeight);
        a = new MyPoint();
        f = new MyPoint();

        g = new MyPoint();
        e = new MyPoint();
        h = new MyPoint();
        b = new MyPoint();
        c = new MyPoint();
        d = new MyPoint();
        i = new MyPoint();
        j = new MyPoint();
        k = new MyPoint();

        pointPaint = new Paint();
        pointPaint.setColor(Color.RED);
        pointPaint.setTextSize(25);

        bgPaint = new Paint();
        bgPaint.setColor(Color.WHITE);

        pathAPaint = new Paint();
        pathAPaint.setColor(Color.GREEN);
        pathAPaint.setAntiAlias(true);

        pathA = new Path();

        pathCPaint = new Paint();
        pathCPaint.setColor(Color.YELLOW);
        pathCPaint.setAntiAlias(true);
        pathCPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        pathC = new Path();

        pathBPaint = new Paint();
        pathBPaint.setColor(getResources().getColor(R.color.blue_light));
        pathBPaint.setAntiAlias(true);
        pathBPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));

        pathB = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //为了看清楚点与View的位置关系绘制一个北京
        canvas.drawRect(0,0,viewWidth,viewHeight,bgPaint);


        mBitmap = Bitmap.createBitmap((int) viewWidth, (int) viewHeight, Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(mBitmap);
        if (a.x == -1 && a.y == -1){
            bitmapCanvas.drawPath(getPathDefault(),pathAPaint);
        }else {
            if (f.x==viewWidth && f.y == 0){
                bitmapCanvas.drawPath(getPathAFromTopRight(),pathAPaint);
            }else if (f.x == viewWidth && f.y == viewHeight){
                bitmapCanvas.drawPath(getPathAFromLowerRight(),pathAPaint);
            }
            bitmapCanvas.drawPath(getPathC(),pathCPaint);
            bitmapCanvas.drawPath(getPathB(),pathBPaint);
        }

        canvas.drawBitmap(mBitmap,0,0,null);
        //绘制个标识点
//        drawIdentificationPoint(canvas);

    }

    private Path getPathDefault() {
        pathA.reset();
        pathA.lineTo(0,viewHeight);
        pathA.lineTo(viewWidth,viewHeight);
        pathA.lineTo(viewWidth,0);
        pathA.close();
        return pathA;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = measureSize(defaultHeight, heightMeasureSpec);
        int width = measureSize(defaultWidth, widthMeasureSpec);
        setMeasuredDimension(width,height);
        viewWidth = width;
        viewHeight = height;
        a.x = -1;
        a.y = -1;
        f.x = width;
        f.y = height;
        calcPointsXY(a,f);
    }

    private int measureSize(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY){
            result = size;
        }else if (mode == MeasureSpec.AT_MOST){
            result = Math.min(result,size);
        }
        return result;
    }

    /**
     * 获得f点在右上角的pathA
     * @return
     */
    private Path getPathAFromTopRight() {
        pathA.reset();
        pathA.lineTo(c.x,c.y);
        pathA.quadTo(e.x,e.y,b.x,b.y);
        pathA.lineTo(a.x,a.y);
        pathA.lineTo(k.x,k.y);
        pathA.quadTo(h.x,h.y,j.x,j.y);
        pathA.lineTo(viewWidth,viewHeight);
        pathA.lineTo(0,viewHeight);
        pathA.close();
        return pathA;
    }

    private Path getPathB() {
        pathB.reset();
        pathB.lineTo(0,viewHeight);//移动到左下角
        pathB.lineTo(viewWidth,viewHeight);//移动到右下角
        pathB.lineTo(viewWidth,0);//移动到右上角
        pathB.close();//闭合区域
        return pathB;
    }

    private void drawIdentificationPoint(Canvas canvas) {
        canvas.drawText("a",a.x,a.y,pointPaint);
        canvas.drawText("f",f.x,f.y,pointPaint);
        canvas.drawText("g",g.x,g.y,pointPaint);

        canvas.drawText("e",e.x,e.y,pointPaint);
        canvas.drawText("h",h.x,h.y,pointPaint);

        canvas.drawText("c",c.x,c.y,pointPaint);
        canvas.drawText("j",j.x,j.y,pointPaint);

        canvas.drawText("b",b.x,b.y,pointPaint);
        canvas.drawText("k",k.x,k.y,pointPaint);

        canvas.drawText("d",d.x,d.y,pointPaint);
        canvas.drawText("i",i.x,i.y,pointPaint);
    }

    private Path getPathC() {
        pathC.reset();
        pathC.moveTo(i.x,i.y);
        pathC.lineTo(d.x,d.y);
        pathC.lineTo(b.x,b.y);
        pathC.lineTo(a.x,a.y);
        pathC.lineTo(k.x,k.y);
        pathC.close();
        return pathC;
    }

    /**
     * 获得f点在右下角的pathA
     * @return
     */
    private Path getPathAFromLowerRight() {
        pathA.reset();
        pathA.lineTo(0,viewHeight); //移动到左下角
        pathA.lineTo(c.x,c.y);          //移动到c点
        pathA.quadTo(e.x,e.y,b.x,b.y);  //从c到b画贝塞尔曲线，控制点为e
        pathA.lineTo(a.x,a.y);          //移动到a点
        pathA.lineTo(k.x,k.y);          //移动到k点
        pathA.quadTo(h.x,h.y,j.x,j.y);  //从k到j画贝塞尔曲线，控制点为h
        pathA.lineTo(viewWidth,0);  //移动到右上角
        pathA.close();
        return pathA;
    }

    /**
     * 根据已知的a和f点计算其余点的坐标
     *
     * @param a
     * @param f
     */
    private void calcPointsXY(MyPoint a, MyPoint f) {
        g.x = (a.x + f.x) / 2;
        g.y = (a.y + f.y) / 2;

        e.x = g.x - (f.y - g.y) * (f.y - g.y) / (f.x - g.x);
        e.y = f.y;

        h.x = f.x;
        h.y = g.y - (f.x - g.x) * (f.x - g.x) / (f.y - g.y);

        c.x = e.x - (f.x - e.x) / 2;
        c.y = f.y;

        j.x = f.x;
        j.y = h.y - (f.y - h.y) / 2;

        b = getIntersectionPoint(a,e,c,j);
        k = getIntersectionPoint(a,h,c,j);

        d.x = (c.x + 2 * e.x + b.x) / 4;
        d.y = (2 * e.y + c.y + b.y) / 4;

        i.x = (j.x + 2 * h.x + k.x) / 4;
        i.y = (2 * h.y + j.y + k.y) / 4;
    }

    /**
     * 计算两线段相交点坐标
     * @param lineOne_My_pointOne
     * @param lineOne_My_pointTwo
     * @param lineTwo_My_pointOne
     * @param lineTwo_My_pointTwo
     * @return 返回该点
     */
    private MyPoint getIntersectionPoint(MyPoint lineOne_My_pointOne, MyPoint lineOne_My_pointTwo, MyPoint lineTwo_My_pointOne, MyPoint lineTwo_My_pointTwo){
        float x1,y1,x2,y2,x3,y3,x4,y4;
        x1 = lineOne_My_pointOne.x;
        y1 = lineOne_My_pointOne.y;
        x2 = lineOne_My_pointTwo.x;
        y2 = lineOne_My_pointTwo.y;
        x3 = lineTwo_My_pointOne.x;
        y3 = lineTwo_My_pointOne.y;
        x4 = lineTwo_My_pointTwo.x;
        y4 = lineTwo_My_pointTwo.y;

        float pointX =((x1 - x2) * (x3 * y4 - x4 * y3) - (x3 - x4) * (x1 * y2 - x2 * y1))
                / ((x3 - x4) * (y1 - y2) - (x1 - x2) * (y3 - y4));
        float pointY =((y1 - y2) * (x3 * y4 - x4 * y3) - (x1 * y2 - x2 * y1) * (y3 - y4))
                / ((y1 - y2) * (x3 - x4) - (x1 - x2) * (y3 - y4));

        return  new MyPoint(pointX,pointY);
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public int getViewWidth() {
        return viewWidth;
    }

    /**
     * 设置触摸点
     * @param x
     * @param y
     * @param style
     */
    public void setTouchPoint(float x, float y, String style) {
        switch (style){
            case STYLE_LOWER_RIGHT:
                f.x = viewWidth;
                f.y = viewHeight;
                break;
            case STYLE_TOP_RIGHT:
                f.x = viewWidth;
                f.y = 0;
                break;
            default:
                    break;
        }
        MyPoint touchPoint = new MyPoint(x, y);
        if (calePointCX(touchPoint,f)>0){
            a.x = x;
            a.y = y;
            calcPointsXY(a,f);
        }else {
            calcPointsXY(a,f);
        }

        postInvalidate();
    }

    /**
     * 计算c点的x值
     * @param a
     * @param f
     * @return
     */
    private float calePointCX(MyPoint a, MyPoint f) {
        MyPoint g,e;
        g = new MyPoint();
        e = new MyPoint();
        g.x = (a.x + f.x) / 2;
        g.y = (a.y + f.y) / 2;

        e.x = g.x - (f.y - g.y) * (f.y - g.y) / (f.x - g.x);
        e.y = f.y;

        return e.x - (f.x - e.x) / 2;

    }


    public void setDefaultPath() {
        a.x = -1;
        a.y = -1;
        postInvalidate();
    }
}
