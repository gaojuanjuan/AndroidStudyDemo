package com.gjj.androidstudydemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gjj.androidstudydemo.R;
import com.gjj.androidstudydemo.view.TransPathView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomSVG1Activity extends AppCompatActivity {

    @BindView(R.id.trans_path_view)
    TransPathView mTransPathView;
    @BindView(R.id.btn1)
    Button mBtn1;
    @BindView(R.id.btn2)
    Button mBtn2;
    @BindView(R.id.btn3)
    Button mBtn3;
    @BindView(R.id.btn4)
    Button mBtn4;
    @BindView(R.id.btn5)
    Button mBtn5;
    @BindView(R.id.btn6)
    Button mBtn6;
    @BindView(R.id.btn7)
    Button mBtn7;
    @BindView(R.id.btn8)
    Button mBtn8;
    @BindView(R.id.btn9)
    Button mBtn9;
    @BindView(R.id.btn0)
    Button mBtn0;

    public static final String PATH_0 = "M19,48 L34,34 L52,53 L52,97 L19,118 Z M38,30 L52,15 L107,15 L121,30 L103,48 L56,48 Z M108,52 L126,34 L141,49 L141,118 L107,97 Z M107,139 L141,123 L141,191 L126,206 L107,188 Z M56,192 L102,192 L121,211 L107,225 L53,225 L38,211 Z M19,123 L52,139 L52,188 L33,206 L19,192 Z M28,120 L28,120 L28,120 L28,120 L28,120 L28,120 Z";
    public static final String PATH_1 = "M19,48 L34,34 L52,53 L52,97 L19,118 Z M38,30 L38,30 L38,30 L38,30 L38,30 L38,30 Z M108,52 L108,52 L108,52 L108,52 L108,52 Z M107,139 L107,139 L107,139 L107,139 L107,139 Z M56,192 L56,192 L56,192 L56,192 L56,192 L56,192 Z M19,123 L52,139 L52,188 L33,206 L19,192 Z M28,120 L28,120 L28,120 L28,120 L28,120 L28,120 Z";
    public static final String PATH_2 = "M19,48 L19,48 L19,48 L19,48 L19,48 Z M38,30 L52,15 L107,15 L121,30 L103,48 L56,48 Z M108,52 L126,34 L141,49 L141,118 L107,97 Z M107,139 L107,139 L107,139 L107,139 L107,139 Z M56,192 L102,192 L121,211 L107,225 L53,225 L38,211 Z M19,123 L52,139 L52,188 L33,206 L19,192 Z M28,120 L56,102 L103,102 L131,120 L104,134 L56,134 Z";
    public static final String PATH_3 = "M19,48 L19,48 L19,48 L19,48 L19,48 Z M38,30 L52,15 L107,15 L121,30 L103,48 L56,48 Z M108,52 L126,34 L141,49 L141,118 L107,97 Z M107,139 L141,123 L141,191 L126,206 L107,188 Z M56,192 L102,192 L121,211 L107,225 L53,225 L38,211 Z M19,123 L19,123 L19,123 L19,123 L19,123 Z M28,120 L56,102 L103,102 L131,120 L104,134 L56,134 Z";
    public static final String PATH_4 = "M19,48 L34,34 L52,53 L52,97 L19,118 Z M38,30 L38,30 L38,30 L38,30 L38,30 L38,30 Z M108,52 L126,34 L141,49 L141,118 L107,97 Z M107,139 L141,123 L141,191 L126,206 L107,188 Z M56,192 L56,192 L56,192 L56,192 L56,192 L56,192 Z M19,123 L19,123 L19,123 L19,123 L19,123 Z M28,120 L56,102 L103,102 L131,120 L104,134 L56,134 Z";
    public static final String PATH_5 = "M19,48 L34,34 L52,53 L52,97 L19,118 Z M38,30 L52,15 L107,15 L121,30 L103,48 L56,48 Z M108,52 L108,52 L108,52 L108,52 L108,52 Z M107,139 L141,123 L141,191 L126,206 L107,188 Z M56,192 L102,192 L121,211 L107,225 L53,225 L38,211 Z M19,123 L19,123 L19,123 L19,123 L19,123 Z M28,120 L56,102 L103,102 L131,120 L104,134 L56,134 Z";
    public static final String PATH_6 = "M19,48 L34,34 L52,53 L52,97 L19,118 Z M38,30 L52,15 L107,15 L121,30 L103,48 L56,48 Z M108,52 L108,52 L108,52 L108,52 L108,52 Z M107,139 L141,123 L141,191 L126,206 L107,188 Z M56,192 L102,192 L121,211 L107,225 L53,225 L38,211 Z M19,123 L52,139 L52,188 L33,206 L19,192 Z M28,120 L56,102 L103,102 L131,120 L104,134 L56,134 Z";
    public static final String PATH_7 = "M19,48 L19,48 L19,48 L19,48 L19,48 Z M38,30 L52,15 L107,15 L121,30 L103,48 L56,48 Z M108,52 L126,34 L141,49 L141,118 L107,97 Z M107,139 L141,123 L141,191 L126,206 L107,188 Z M56,192 L56,192 L56,192 L56,192 L56,192 L56,192 Z M19,123 L19,123 L19,123 L19,123 L19,123 Z M28,120 L28,120 L28,120 L28,120 L28,120 L28,120 Z";
    public static final String PATH_8 = "M19,48 L34,34 L52,53 L52,97 L19,118 Z M38,30 L52,15 L107,15 L121,30 L103,48 L56,48 Z M108,52 L126,34 L141,49 L141,118 L107,97 Z M107,139 L141,123 L141,191 L126,206 L107,188 Z M56,192 L102,192 L121,211 L107,225 L53,225 L38,211 Z M19,123 L52,139 L52,188 L33,206 L19,192 Z M28,120 L56,102 L103,102 L131,120 L104,134 L56,134 Z";
    public static final String PATH_9 = "M19,48 L34,34 L52,53 L52,97 L19,118 Z M38,30 L52,15 L107,15 L121,30 L103,48 L56,48 Z M108,52 L126,34 L141,49 L141,118 L107,97 Z M107,139 L141,123 L141,191 L126,206 L107,188 Z M56,192 L102,192 L121,211 L107,225 L53,225 L38,211 Z M19,123 L19,123 L19,123 L19,123 L19,123 Z M28,120 L56,102 L103,102 L131,120 L104,134 L56,134 Z";

    String[] arr = new String[]{PATH_0, PATH_1, PATH_2, PATH_3, PATH_4, PATH_5, PATH_6, PATH_7, PATH_8, PATH_9};

    String currPath = PATH_0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_svg1);
        ButterKnife.bind(this);
        setTitle("自定义SVG1");

        mTransPathView.setViewPort(160,240);
        mTransPathView.setPaths(currPath,PATH_0);
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn0})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
            case R.id.btn2:
            case R.id.btn3:
            case R.id.btn4:
            case R.id.btn5:
            case R.id.btn6:
            case R.id.btn7:
            case R.id.btn8:
            case R.id.btn9:
            case R.id.btn0:
                int num2 = Integer.parseInt(((Button)view).getText().toString().trim());
                mTransPathView.setPaths(currPath, arr[num2]);
                mTransPathView.startTransWithRotate(360);
                currPath = arr[num2];
                break;
        }
    }
}
