package com.gjj.androidstudydemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gjj.androidstudydemo.R;
import com.gjj.androidstudydemo.bean.SquareBean;

import java.util.List;

/**
 * Created by 高娟娟 on 2018/3/28.
 */

public class SquareAdapter extends RecyclerView.Adapter<SquareAdapter.MySquareViewHolder>{

    private Context mContext;
    private List<SquareBean> mList;
    private ReduceAndAddClickListener mReduceAndAddClickListener;
    public SquareAdapter(Context context,List<SquareBean> beans) {
        mContext = context;
        mList = beans;
    }

    public interface ReduceAndAddClickListener{
        void clickAdd(int postion);
        void clickReduce(int postion);
    }

    public void setReduceAndAddClickListener(ReduceAndAddClickListener reduceAndAddClickListener) {
        mReduceAndAddClickListener = reduceAndAddClickListener;
    }

    @NonNull
    @Override
    public MySquareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_square, parent, false);
        return new MySquareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MySquareViewHolder holder, final int position) {
        holder.tvIndex.setText(position+"");
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mReduceAndAddClickListener != null){
                    mReduceAndAddClickListener.clickAdd(position);
                }
            }
        });
        holder.btnReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mReduceAndAddClickListener != null){
                    mReduceAndAddClickListener.clickReduce(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MySquareViewHolder extends RecyclerView.ViewHolder{


        public Button btnReduce;
        public Button btnAdd;
        public TextView tvIndex;
        public MySquareViewHolder(View itemView) {
            super(itemView);
            btnReduce = (Button)itemView.findViewById(R.id.btn_reduce);
            btnAdd = (Button)itemView.findViewById(R.id.btn_add);
            tvIndex = (TextView)itemView.findViewById(R.id.tv_index);
        }
    }
}
