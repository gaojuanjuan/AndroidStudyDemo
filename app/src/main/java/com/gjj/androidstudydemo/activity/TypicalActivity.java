package com.gjj.androidstudydemo.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;

import com.gjj.androidstudydemo.R;
import com.gjj.androidstudydemo.adapter.RVAdapter;
import com.gjj.androidstudydemo.bean.RVBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TypicalActivity extends AppCompatActivity implements RVAdapter.RVClickListener {


    @BindView(R.id.rv)
    RecyclerView mRv;
    private ArrayList<RVBean> beans;
    private RVAdapter mRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typical);
        ButterKnife.bind(this);
        setTitle("典型应用");
        init();
    }

    /**
     * 初始化控件和数据
     */
    private void init() {
        mRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        beans = new ArrayList<>();
        beans.add(new RVBean("谈笑冯生", R.mipmap.head_image_01, "低调奢华有内涵"));
        beans.add(new RVBean("高鹏展翅", R.mipmap.head_image_02, "桌游传奇小王子"));
        beans.add(new RVBean("珉而好学", R.mipmap.head_image_03, "套路王"));
        beans.add(new RVBean("汰渍洗衣粉，加亮不加价", R.mipmap.head_image_04, "What are you 说啥捏？"));
        beans.add(new RVBean("尼古拉斯.赵政", R.mipmap.head_image_05, "亚洲舞王不解释"));

        beans.add(new RVBean("谈笑冯生", R.mipmap.head_image_06, "低调奢华有内涵"));
        beans.add(new RVBean("高鹏展翅", R.mipmap.head_image_07, "桌游传奇小王子"));
        beans.add(new RVBean("珉而好学", R.mipmap.head_image_08, "套路王"));
        beans.add(new RVBean("汰渍洗衣粉，加亮不加价", R.mipmap.head_image_09, "What are you 说啥捏？"));
        beans.add(new RVBean("尼古拉斯.赵政", R.mipmap.head_image_10, "亚洲舞王不解释"));

        beans.add(new RVBean("谈笑冯生", R.mipmap.head_image_11, "低调奢华有内涵"));
        beans.add(new RVBean("高鹏展翅", R.mipmap.head_image_12, "桌游传奇小王子"));
        beans.add(new RVBean("珉而好学", R.mipmap.head_image_13, "套路王"));
        beans.add(new RVBean("汰渍洗衣粉，加亮不加价", R.mipmap.head_image_14, "What are you 说啥捏？"));
        beans.add(new RVBean("尼古拉斯.赵政", R.mipmap.head_image_15, "亚洲舞王不解释"));

        beans.add(new RVBean("谈笑冯生", R.mipmap.head_image_16, "低调奢华有内涵"));
        beans.add(new RVBean("高鹏展翅", R.mipmap.head_image_17, "桌游传奇小王子"));
        beans.add(new RVBean("珉而好学", R.mipmap.head_image_18, "套路王"));
        beans.add(new RVBean("汰渍洗衣粉，加亮不加价", R.mipmap.head_image_19, "What are you 说啥捏？"));
        beans.add(new RVBean("尼古拉斯.赵政", R.mipmap.head_image_20, "亚洲舞王不解释"));

        mRVAdapter = new RVAdapter(beans,this);
        mRv.setAdapter(mRVAdapter);
        mRVAdapter.setListener(this);
    }

    /**
     * 条目的点击事件
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        RVBean bean = beans.get(position);

        Intent intent = new Intent(TypicalActivity.this, TypicalDetailActivity.class);
        intent.putExtra("resId",bean.getResId());
        intent.putExtra("name",bean.getName());
        intent.putExtra("desc",bean.getDesc());

        /**
         * 获取到点击条目的view
         */
        int firstVisiblePosition = ((LinearLayoutManager)mRv.getLayoutManager()).findFirstVisibleItemPosition();

        View itemView = mRv.getChildAt(position - firstVisiblePosition);
        View headImage = itemView.findViewById(R.id.head_image);
        View name = itemView.findViewById(R.id.name);
        View desc = itemView.findViewById(R.id.desc);
        //启动activity时，使用共享元素
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,
                Pair.create(headImage,"headImage"),
                Pair.create(name,"name"),
                Pair.create(desc,"desc")).toBundle());
    }
}
