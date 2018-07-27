package com.gjj.androidstudydemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

public class TextView1 extends TextView {
    private static final String TAG = TextView1.class.getSimpleName();

    public TextView1(Context context) {
        super(context);
    }

    public TextView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TextView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG,"View's onTouchEvent returns super");
        return super.onTouchEvent(event);
//        Log.i(TAG,"View's onTouchEvent returns false");
//        return false;
//        Log.i(TAG,"View's onTouchEvent returns true");
//        return true;


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG,"View's dispatchTouchEvent returns super");

        return super.dispatchTouchEvent(ev);
    }
}
