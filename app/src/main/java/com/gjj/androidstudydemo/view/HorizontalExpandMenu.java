package com.gjj.androidstudydemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.gjj.androidstudydemo.R;
import com.gjj.androidstudydemo.utils.DpUtil;

/**
 * Created by gaojuanjuan on 2018/3/6.
 */

public class HorizontalExpandMenu extends RelativeLayout {
    private Context mContext;
    private AttributeSet mAttrs;

    private int defaultWidth;   //默认宽度
    private int defaultHeight;  //默认高度
    private int viewWidth;
    private int viewHeight;

    private int menuBackColor;  //菜单栏背景色
    private float menuStrokeSize;//菜单栏边框线的size
    private int menuStrokeColor;//菜单栏边框线的color
    private float menuCornerRaidus;//菜单栏圆角半径

    //绘制菜单按钮需要的属性
    private int buttonIconDegrees;      //按钮icon符号竖线的旋转角度
    private float buttonIconSize;       //按钮icon符号的大小
    private float buttonIcomStrokeWidth;//按钮icon符号的粗细
    private int buttonIconColor;        //按钮icon符号的颜色
    private Paint buttonIconPaint;
    private Path path;

    private Point leftButtonCenter;     //左按钮的中点
    private int leftButtonLeft;         //左按钮矩形区域的left值
    private int leftButtonRight;        //左按钮矩形区域的right值

    private Point rightButtonCenter;    //右按钮的中点
    private int rightButtonLeft;        //右按钮矩形区域的left值
    private int rightButtonRight;       //右按钮矩形区域的right值

    private int buttonStyle;            //按钮类型

    private int buttonRadius;           //按钮矩形区域内圆半径,自定义HorizontalExpandMenu高度的一半
    private int buttonTop;              //按钮矩形区域的top值
    private int buttonBottom;           //按钮矩形区域的bottom值



    public HorizontalExpandMenu(Context context) {
        super(context);
        this.mContext = context;
        init();
    }



    public HorizontalExpandMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mAttrs = attrs;
        init();
    }

    /**
     * 菜单按钮所在位置，默认为右边
     */
    public class ButtonStyle{
        public static final int Right = 0;
        public static final int Left = 1;
    }
    private void init() {
        //绘制长圆角矩形需要用到的属性
        TypedArray typedArray = mContext.obtainStyledAttributes(mAttrs, R.styleable.HorizontalExpandMenu);

        defaultWidth = (int) DpUtil.dp2px(mContext,200);
        defaultHeight = (int) DpUtil.dp2px(mContext,40);
        menuBackColor = typedArray.getColor(R.styleable.HorizontalExpandMenu_back_color, Color.WHITE);
        menuStrokeSize = typedArray.getDimension(R.styleable.HorizontalExpandMenu_stroke_size,1);
        menuStrokeColor = typedArray.getColor(R.styleable.HorizontalExpandMenu_stroke_color,Color.GRAY);
        menuCornerRaidus = typedArray.getDimension(R.styleable.HorizontalExpandMenu_corner_radius,
                DpUtil.dp2px(mContext,20));

        //绘制菜单按钮加号需要用到的属性
        buttonStyle = typedArray.getInteger(R.styleable.HorizontalExpandMenu_button_style, ButtonStyle.Right);
        buttonIconDegrees = 0;
        buttonIconSize = typedArray.getDimension(R.styleable.HorizontalExpandMenu_button_icon_size,
                DpUtil.dp2px(mContext,8));
        buttonIcomStrokeWidth = typedArray.getDimension(R.styleable.HorizontalExpandMenu_button_icon_stroke_width,
                DpUtil.dp2px(mContext,2));
        buttonIconColor = typedArray.getColor(R.styleable.HorizontalExpandMenu_button_icon_color, Color.GRAY);

        typedArray.recycle();

        //初始化绘制菜单的画笔
        buttonIconPaint = new Paint();
        buttonIconPaint.setColor(buttonIconColor);
        buttonIconPaint.setStyle(Paint.Style.STROKE);
        buttonIconPaint.setStrokeWidth(buttonIcomStrokeWidth);
        buttonIconPaint.setAntiAlias(true);

        path = new Path();
        leftButtonCenter = new Point();
        rightButtonCenter = new Point();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = measureSize(defaultHeight, heightMeasureSpec);
        int width = measureSize(defaultWidth, widthMeasureSpec);
        viewHeight = height;
        viewWidth = width;
        setMeasuredDimension(viewWidth,viewHeight);

        //计算菜单按钮的半径
        buttonRadius = viewHeight /2;
        layoutRootButton();
        //布局代码中如果没有设置background属性，则在此处添加一个北京
        if (getBackground() == null){
            setMenuBackground();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        layoutRootButton();
        if (buttonStyle == ButtonStyle.Right){
            drawRightIcon(canvas);
        }else{
            drawLeftIcon(canvas);
        }
        super.onDraw(canvas);//注意父方法在最后调用，以免icon被遮盖

    }

    /**
     * 绘制左边的按钮
     * @param canvas
     */
    private void drawLeftIcon(Canvas canvas) {
        path.reset();
        path.moveTo(leftButtonCenter.x - buttonIconSize,leftButtonCenter.y);
        path.lineTo(leftButtonCenter.x + buttonIconSize,leftButtonCenter.y);
        canvas.drawPath(path,buttonIconPaint);//画横线

        canvas.save();
        canvas.rotate(-buttonIconDegrees,leftButtonCenter.x,leftButtonCenter.y);

        path.reset();
        path.moveTo(leftButtonCenter.x,leftButtonCenter.y - buttonIconSize);
        path.lineTo(leftButtonCenter.x,leftButtonCenter.y + buttonIconSize);
        canvas.drawPath(path,buttonIconPaint);//画竖线
        canvas.restore();
    }

    /**
     * 绘制右边的按钮
     * @param canvas
     */
    private void drawRightIcon(Canvas canvas) {
        path.reset();
        path.moveTo(rightButtonCenter.x - buttonIconSize,rightButtonCenter.y);
        path.lineTo(rightButtonCenter.x + buttonIconSize,rightButtonCenter.y);
        canvas.drawPath(path,buttonIconPaint);//绘制横线

        canvas.save();
        canvas.rotate(buttonIconDegrees,rightButtonCenter.x,rightButtonCenter.y);
        path.reset();
        path.moveTo(rightButtonCenter.x,rightButtonCenter.y- buttonIconSize);
        path.lineTo(rightButtonCenter.x,rightButtonCenter.y+ buttonIconSize);
        canvas.drawPath(path,buttonIconPaint);
        canvas.restore();
    }

    //测量按钮的中点和矩形的位置
    private void layoutRootButton() {
        buttonTop = 0;
        buttonBottom = viewHeight;

        rightButtonCenter.x = viewWidth - buttonRadius;
        rightButtonCenter.y = viewHeight /2;
        rightButtonLeft = rightButtonCenter.x - buttonRadius;
        rightButtonRight = rightButtonCenter.x+ buttonRadius;

        leftButtonCenter.x = buttonRadius;
        leftButtonCenter.y = viewHeight / 2;
        leftButtonLeft = leftButtonCenter.x - buttonRadius;
        leftButtonRight = leftButtonCenter.x + buttonRadius;
    }

    private int measureSize(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }else if (specMode == MeasureSpec.AT_MOST){
            result = Math.min(result,specSize);
        }
        return result;
    }

    /**
     * 设置菜单北京，如果要显示阴影，需要在onLayout之前调用
     */
    private void setMenuBackground(){
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(menuBackColor);
        gd.setStroke((int) menuStrokeSize,menuStrokeColor);
        gd.setCornerRadius(menuCornerRaidus);
        setBackground(gd);
    }
}
