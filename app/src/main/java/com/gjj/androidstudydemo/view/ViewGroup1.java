package com.gjj.androidstudydemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class ViewGroup1 extends LinearLayout {
    private static final String TAG = ViewGroup1.class.getSimpleName();

    public ViewGroup1(Context context) {
        super(context);
    }

    public ViewGroup1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroup1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG,"ViewGroup1's onTouchEvent returns super");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG,"ViewGroup1's onInterceptTouchEvent returns super");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG,"ViewGroup1's dispatchTouchEvent returns super");
        return super.dispatchTouchEvent(ev);
    }
}
