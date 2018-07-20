package com.gjj.androidstudydemo.utils;

import android.content.Context;
import android.util.TypedValue;

public class DisplayUtil {
    public static float dp2px(Context context,float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, context.getResources().getDisplayMetrics());
    }

    public static float sp2px(Context context,float spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spValue, context.getResources().getDisplayMetrics());
    }
}
