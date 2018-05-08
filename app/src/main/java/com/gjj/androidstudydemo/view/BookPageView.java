package com.gjj.androidstudydemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

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

    private String style;
    public static final String STYLE_LEFT = "STYLE_LEFT";//点击左边区域
    public static final String STYLE_RIGHT = "STYLE_RIGHT";//点击右边区域
    public static final String STYLE_MODDLE = "STYLE_MODDLE";//点击中间区域
    public static final String STYLE_TOP_RIGHT = "STYLE_TOP_RIGHT";//f点在右上角
    public static final String STYLE_LOWER_RIGHT = "STYLE_LOWER_RIGHT";//f点在右下角

    public Scroller mScroller;
    private Paint textPaint;
    private Paint pathCContentPaint;

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
//        pathBPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));

        pathB = new Path();

        mScroller = new Scroller(context,new LinearInterpolator());//以常量速率滑动即可

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setSubpixelText(true);
        textPaint.setTextSize(40);

        pathCContentPaint = new Paint();
        pathCContentPaint.setColor(Color.YELLOW);
        pathCContentPaint.setAntiAlias(true);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            int x = mScroller.getCurrX();
            int y = mScroller.getCurrY();

            if (style.equals(STYLE_TOP_RIGHT)){
                setTouchPoint(x,y,STYLE_TOP_RIGHT);
            }else {
                setTouchPoint(x,y,STYLE_LOWER_RIGHT);
            }
            if (mScroller.getFinalX() == x && mScroller.getFinalY() == y){
                setDefaultPath();//重置默认界面
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //为了看清楚点与View的位置关系绘制一个北京
        canvas.drawRect(0,0,viewWidth,viewHeight,bgPaint);


        mBitmap = Bitmap.createBitmap((int) viewWidth, (int) viewHeight, Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(mBitmap);
        if (a.x == -1 && a.y == -1){
            drawPathAContent(bitmapCanvas,getPathDefault(),pathAPaint);
        }else {
            if (f.x==viewWidth && f.y == 0){
                drawPathAContent(bitmapCanvas,getPathAFromTopRight(),pathAPaint);

                bitmapCanvas.drawPath(getPathC(),pathCPaint);
                drawPathCContent(bitmapCanvas,getPathAFromTopRight(),pathCContentPaint);

                drawPathBContent(bitmapCanvas,getPathAFromTopRight(),pathBPaint);
            }else if (f.x == viewWidth && f.y == viewHeight){
                drawPathAContent(bitmapCanvas,getPathAFromLowerRight(),pathAPaint);

                bitmapCanvas.drawPath(getPathC(),pathCPaint);
                drawPathCContent(bitmapCanvas,getPathAFromLowerRight(),pathCContentPaint);

                drawPathBContent(bitmapCanvas,getPathAFromLowerRight(),pathBPaint);
                
            }
        }

        canvas.drawBitmap(mBitmap,0,0,null);
        //绘制个标识点
//        drawIdentificationPoint(canvas);

    }

    /**
     * 绘制C区域内容
     * @param canvas
     * @param pathA
     * @param pathPaint
     */
    private void drawPathCContent(Canvas canvas, Path pathA, Paint pathPaint) {
        Bitmap contentBitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.RGB_565);
        Canvas contentCanvas = new Canvas(contentBitmap);
        //下面开始绘制区域内的内容...
        contentCanvas.drawPath(getPathB(),pathPaint);//绘制一个背景，path用B的就行
        contentCanvas.drawText("这是在C区域的内容...CCCC", viewWidth-350, viewHeight-100, textPaint);

        //结束绘制区域内的内容...

        canvas.save();
        canvas.clipPath(pathA);
//        canvas.clipPath(getPathC(), Region.Op.REVERSE_DIFFERENCE);//裁剪出C区域不同于A区域的部分
        canvas.clipPath(getPathC(), Region.Op.UNION);//裁剪出A和C区域的全集
        canvas.clipPath(getPathC(), Region.Op.INTERSECT);//取与C区域的交集

        float eh = (float) Math.hypot(f.x - e.x,h.y - f.y);
        float sin0 = (f.x - e.x) / eh;
        float cos0 = (h.y - f.y) / eh;
        //设置翻转和旋转矩阵
        float[] mMatrixArray = { 0, 0, 0, 0, 0, 0, 0, 0, 1.0f };
        mMatrixArray[0] = -(1-2 * sin0 * sin0);
        mMatrixArray[1] = 2 * sin0 * cos0;
        mMatrixArray[3] = 2 * sin0 * cos0;
        mMatrixArray[4] = 1 - 2 * sin0 * sin0;

        Matrix mMatrix = new Matrix();
        mMatrix.reset();
        mMatrix.setValues(mMatrixArray);//翻转和旋转
        mMatrix.preTranslate(-e.x, -e.y);//沿当前XY轴负方向位移得到 矩形A₃B₃C₃D₃
        mMatrix.postTranslate(e.x, e.y);//沿原XY轴方向位移得到 矩形A4 B4 C4 D4

        canvas.drawBitmap(contentBitmap, mMatrix, null);
        canvas.restore();

    }

    private void drawPathBContent(Canvas canvas, Path pathA, Paint pathPaint) {
        Bitmap contentBitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.RGB_565);
        Canvas contentCanvas = new Canvas(contentBitmap);

        //下面开始绘制区域内的内容...
        contentCanvas.drawPath(getPathB(),pathPaint);
        contentCanvas.drawText("这是在B区域的内容...BBBB", viewWidth-350, viewHeight-100, textPaint);

        //结束绘制区域内的内容...

        canvas.save();
        canvas.clipPath(pathA);//裁剪出A区域
        canvas.clipPath(getPathC(),Region.Op.UNION);//裁剪出A和C区域的全集
        canvas.clipPath(getPathB(), Region.Op.REVERSE_DIFFERENCE);//裁剪出B区域中不同于与AC区域的部分
        canvas.drawBitmap(contentBitmap, 0, 0, null);
        canvas.restore();

    }

    /**
     * 绘制区域A的内容
     * @param canvas
     * @param pathA
     * @param pathPaint
     */
    private void drawPathAContent(Canvas canvas, Path pathA, Paint pathPaint) {
        Bitmap contentBitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.RGB_565);
        Canvas contentCanvas = new Canvas(contentBitmap);
       //开始绘制区域内的内容
        contentCanvas.drawPath(pathA,pathPaint);
        contentCanvas.drawText("这是在A区域的内容...AAAA",viewWidth-350,viewHeight-100,textPaint);
        //结束绘制区域内的内容...
        canvas.save();
        canvas.clipPath(pathA, Region.Op.INTERSECT);//对绘制内容进行裁剪，取和A区域的交集
        canvas.drawBitmap(contentBitmap,0,0,null);
        canvas.restore();
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
        MyPoint touchPoint = new MyPoint();
        a.x = x;
        a.y = y;
        this.style = style;
        switch (style){
            case STYLE_LOWER_RIGHT:
                f.x = viewWidth;
                f.y = viewHeight;
                calcPointsXY(a,f);
                touchPoint = new MyPoint(x,y);
                if (calePointCX(touchPoint,f)<0){//如果c点坐标小于0则重新测量a点坐标
                    calcPointAByTouchPoint();
                    calcPointsXY(a,f);
                }
                postInvalidate();
                break;
            case STYLE_TOP_RIGHT:
                f.x = viewWidth;
                f.y = 0;
                calcPointsXY(a,f);
                touchPoint = new MyPoint(x,y);
                if (calePointCX(touchPoint,f)<0){//如果c点坐标小于0则重新测量a点坐标
                    calcPointAByTouchPoint();
                    calcPointsXY(a,f);
                }
                postInvalidate();
                break;
            case STYLE_LEFT:
            case STYLE_RIGHT:
                a.y = viewHeight-1;
                f.x = viewWidth;
                f.y = viewHeight;
                calcPointsXY(a,f);
                postInvalidate();
                break;
            default:
                    break;
        }


    }

    /**
     * 如果c点x坐标小于0,根据触摸点重新测量a点坐标
     */
    private void calcPointAByTouchPoint(){
        float w0 = viewWidth - c.x;

        float w1 = Math.abs(f.x - a.x);
        float w2 = viewWidth * w1 / w0;
        a.x = Math.abs(f.x - w2);

        float h1 = Math.abs(f.y - a.y);
        float h2 = w2 * h1 / w1;
        a.y = Math.abs(f.y - h2);
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

    public void startCancelAnim() {
        int dx,dy;
        if (style.equals(STYLE_TOP_RIGHT)){
            dx = ((int) (viewWidth - 1 - a.x));
            dy = ((int) (1-a.y));
        }else {
            dx = ((int) (viewWidth - 1 - a.x));
            dy = ((int) (viewHeight - 1 - a.y));
        }
        mScroller.startScroll((int) a.x,(int) a.y,dx,dy,400);
    }
}
