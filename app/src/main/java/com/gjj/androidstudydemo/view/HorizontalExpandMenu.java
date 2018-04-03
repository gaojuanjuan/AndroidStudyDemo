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
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
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
    private float buttonIconDegrees;      //按钮icon符号竖线的旋转角度
    private float buttonIconSize;       //按钮icon符号的大小
    private float buttonIcomStrokeWidth;//按钮icon符号的粗细
    private int buttonIconColor;        //按钮icon符号的颜色
    private Paint buttonIconPaint;
    private Path path;

    private Point leftButtonCenter;     //左按钮的中点
    private int leftButtonLeft;         //左按钮矩形区域的left值
    private int leftButtonRight;        //左按钮矩形区域的right值

    private Point rightButtonCenter;    //右按钮的中点
    private float rightButtonLeft;        //右按钮矩形区域的left值
    private float rightButtonRight;       //右按钮矩形区域的right值

    private int buttonStyle;            //按钮类型

    private int buttonRadius;           //按钮矩形区域内圆半径,自定义HorizontalExpandMenu高度的一半
    private float buttonTop;              //按钮矩形区域的top值
    private float buttonBottom;           //按钮矩形区域的bottom值
    private int expandAnimTime;

    //设置加号和减号的点击动画
    private boolean isExpand;
    private ExpandMenuAnim anim;
    private float downX;
    private float downY;

    //设置菜单的展开与收起动画
    private float maxBackPathWidth;//绘制view区域的最大宽度
    private float backPathWidth;  //绘制子view的区域宽度
    private int menuLeft;       //menu区域left值
    private int menuRight;      //menu区域right值

    private boolean isFirstLayout;//是否第一次测量位置，主要用于初始化menuLeft和menuRight的值
    private boolean isAnimEnd;
    private View childView;


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
        buttonIconDegrees = 90;//菜单初始状态为展开，所以旋转角度为90度，符号是-
        buttonIconSize = typedArray.getDimension(R.styleable.HorizontalExpandMenu_button_icon_size,
                DpUtil.dp2px(mContext,8));
        buttonIcomStrokeWidth = typedArray.getDimension(R.styleable.HorizontalExpandMenu_button_icon_stroke_width,
                DpUtil.dp2px(mContext,2));
        buttonIconColor = typedArray.getColor(R.styleable.HorizontalExpandMenu_button_icon_color, Color.GRAY);

        //设置按钮动画与点击事件
        //添加动画
        expandAnimTime = typedArray.getInteger(R.styleable.HorizontalExpandMenu_expand_time, 400);
        isExpand = true;
        anim = new ExpandMenuAnim();


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

        //设置菜单的展开和收起动画
        isFirstLayout = true;

        //测量菜单栏子View的位置
        isAnimEnd = false;
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimEnd = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
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

        //设置菜单展开与收起动画
        maxBackPathWidth = viewWidth - buttonRadius * 2;
        backPathWidth = maxBackPathWidth;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //如果子View数量为0时，onLayout后getLeft和getRight才能获取相应数值，
        // menuLeft和menuRight保存初始的left和right
        if (isFirstLayout){
            menuLeft = getLeft();
            menuRight = getRight();
            isFirstLayout = false;
        }
        if (getChildCount()>0){
            childView = getChildAt(0);
            if (isExpand){
                if (buttonStyle == ButtonStyle.Right){
                    childView.layout(leftButtonCenter.x,(int) buttonTop,(int) rightButtonLeft,(int) buttonBottom);
                }else {
                    childView.layout((int)leftButtonRight,(int)buttonTop,rightButtonCenter.x,(int)buttonBottom);
                }
                //限制子View在菜单内，LayoutParam类型和当前ViewGroup一致
                LayoutParams layoutParams = new LayoutParams(viewWidth, viewHeight);
                layoutParams.setMargins(0,0,buttonRadius *3,0);
                childView.setLayoutParams(layoutParams);
            }else {
                childView.setVisibility(GONE);
            }
        }
        if (getChildCount() > 1){//限制直接子view的数量只能为1
            throw  new IllegalStateException("HorizontalExpandMenu can host only on direct child");
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;//当menu的宽度改变时重新给viewWidth赋值
        if (isAnimEnd){//防止出现动画结束后菜单栏位置大小测量错误的bug
            if (buttonStyle == ButtonStyle.Right){
                if (!isExpand){
                    layout((int)(menuRight - buttonRadius * 2 - backPathWidth),getTop(),menuRight,getBottom());
                }
            }else {
                if (!isExpand){
                    layout(menuLeft,getTop(),(int)(menuLeft + buttonRadius * 2 + backPathWidth),getBottom());
                }
            }
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


    private class ExpandMenuAnim extends Animation{
        public ExpandMenuAnim(){

        }
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            int left = menuRight - buttonRadius * 2;//按钮在右边，菜单收起时按钮区域left值
            int right = menuLeft + buttonRadius * 2;//按钮在左边，菜单收起时按钮区域right值

            if (childView != null){
                childView.setVisibility(GONE);
            }
            if (isExpand){//展开菜单
                backPathWidth = maxBackPathWidth * interpolatedTime;
                buttonIconDegrees = 90 * interpolatedTime;

                if (childView != null&&backPathWidth == maxBackPathWidth){
                    childView.setVisibility(VISIBLE);
                }
            }else {//收起菜单
                backPathWidth = maxBackPathWidth - maxBackPathWidth * interpolatedTime;
                buttonIconDegrees = 90 - 90 * interpolatedTime;
            }
            if (buttonStyle == ButtonStyle.Right){
                layout((int) (left - backPathWidth),getTop(),menuRight,getBottom());//会调用onLayout重新测量子view的位置
            }else {
                layout(menuLeft,getTop(),(int)(right + backPathWidth),getBottom());
            }
            postInvalidate();
        }
    }

    //处理加号和减号按钮的点击事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                //动画结束时按钮才生效
                if (backPathWidth == maxBackPathWidth || backPathWidth == 0){
                    switch (buttonStyle){
                        case ButtonStyle.Left:
                            if (x == downX && y == downY && y >= buttonTop
                                    && y <= buttonBottom && x >= leftButtonLeft && x <= leftButtonRight){
                                expandMenu(expandAnimTime);
                            }
                            break;
                        case ButtonStyle.Right:
                            if (x == downX && y == downY && y >= buttonTop
                                    && y <= buttonBottom && x >= rightButtonLeft && x <= rightButtonRight){
                                expandMenu(expandAnimTime);
                            }
                            break;
                    }
                }

                break;
        }
        return true;
    }

    /**
     * 展开收起菜单,点击加号或减号时调用
     * @param expandAnimTime 动画时间
     */
    private void expandMenu(int expandAnimTime) {
        anim.setDuration(expandAnimTime);
        isExpand = isExpand ? false : true;
        this.startAnimation(anim);
        isAnimEnd = false;
    }
}
