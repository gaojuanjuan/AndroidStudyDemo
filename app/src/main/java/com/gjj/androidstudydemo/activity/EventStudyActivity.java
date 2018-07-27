package com.gjj.androidstudydemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.gjj.androidstudydemo.R;

public class EventStudyActivity extends AppCompatActivity {

    private String TAG = EventStudyActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_study);
        setTitle("Android事件分发");
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG,"Activity's onTouchEvent returns super");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG,"Activity's dispatchTouchEvent returns super");
        return super.dispatchTouchEvent(ev);
    }
}
