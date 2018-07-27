package com.gjj.androidstudydemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class ViewGroup2 extends LinearLayout {
    private static final String TAG = ViewGroup2.class.getSimpleName();

    public ViewGroup2(Context context) {
        super(context);
    }

    public ViewGroup2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroup2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.i(TAG,"ViewGroup2's onTouchEvent returns super");
//        return super.onTouchEvent(event);
        Log.i(TAG,"ViewGroup2's onTouchEvent returns true");
        return true;

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG,"ViewGroup2's onInterceptTouchEvent returns super");
        return super.onInterceptTouchEvent(ev);
//        Log.i(TAG,"ViewGroup2's onInterceptTouchEvent returns false");
//        return false;
//        Log.i(TAG,"ViewGroup2's onInterceptTouchEvent returns true");
//        return true;


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG,"ViewGroup2's dispatchTouchEvent returns super");
        return super.dispatchTouchEvent(ev);
//        Log.i(TAG,"ViewGroup2's dispatchTouchEvent returns false");
//        return false;
//        Log.i(TAG,"ViewGroup2's dispatchTouchEvent returns true");
//        return true;

    }
}
