package com.gjj.androidstudydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gjj.androidstudydemo.R;
import com.gjj.androidstudydemo.adapter.CommonListAdapter;
import com.gjj.androidstudydemo.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.listview)
    ListView listview;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        ButterKnife.bind(this);
        setTitle("自定义View");
        mIntent = new Intent(CustomViewActivity.this, CustomViewDetailActivity.class);
        String[] customViewStrings = getResources().getStringArray(R.array.custom_view);

        listview.setAdapter(new CommonListAdapter(this,customViewStrings));
        listview.setOnItemClickListener(this);

    }


    private void startCustomActivity(String flag) {
        mIntent.putExtra(Constants.CUSTOMVIEW_FLAG, flag);
        startActivity(mIntent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                startCustomActivity(Constants.BOOK_PAGE);
                break;
            case 1:
                startCustomActivity(Constants.WAVE_PROGRESS);
                break;
            case 2:
                startCustomActivity(Constants.CIRCLE_BAR);
                break;
            case 3:
                startActivity(new Intent(CustomViewActivity.this, SquareActivity.class));
                break;
            case 4:
                startCustomActivity(Constants.CUSTOM_VIEW1);
                break;
            case 5:
                startCustomActivity(Constants.CHECKVIEW);
                break;
            case 6:
                startCustomActivity(Constants.RADAR);
                break;
            case 7:
                startCustomActivity(Constants.BEZIER3);
                break;
            case 8:
                startCustomActivity(Constants.OP_VIEW);
                break;
            case 9:
                startCustomActivity(Constants.PATHMEASURE_SEARCHVIEW);
                break;
            case 10:
                startCustomActivity(Constants.MATRIX_CAMERA1);
                break;
            case 11:
                startCustomActivity(Constants.PIECHART);
                break;
            case 12:
                startCustomActivity(Constants.FIVERING);
                break;
            case 13:
                startCustomActivity(Constants.GRADIENT_SEEKBAR);
                break;
            case 14:
                startCustomActivity(Constants.TWINKLING_TV);
                break;
            case 15:
                startCustomActivity(Constants.COLOR_SELECTOR);
                break;
            case 16:
                startCustomActivity(Constants.CIRCLE_GRADIENT_SEEKBAR);
                break;
            case 17:
                startCustomActivity(Constants.LINECHART);
                break;
            case 18:
                startCustomActivity(Constants.BITMAP_SHADER);
                break;


        }
    }
}
