package com.gjj.androidstudydemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjj.androidstudydemo.R;
import com.gjj.androidstudydemo.bean.RVBean;

import java.util.List;

/**
 * Created by gaojuanjuan on 2018/2/6.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> implements View.OnClickListener {
    private List<RVBean> beans;
    private Context mContext;

    private RVClickListener mListener;

    @Override
    public void onClick(View v) {
        if (mListener != null){
            int position = (int) v.getTag();
            mListener.onItemClick(position);
        }
    }

    public void setListener(RVClickListener listener) {
        mListener = listener;
    }

    public interface RVClickListener{
        void onItemClick(int position);
    }
    public RVAdapter(List<RVBean> beans, Context context) {
        this.beans = beans;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RVBean bean = beans.get(position);
        holder.head.setImageResource(bean.getResId());
        holder.name.setText(bean.getName());
        holder.desc.setText(bean.getDesc());
        holder.root.setTag(position);
        holder.root.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView head;
        TextView name;
        TextView desc;
        View root;
        public MyViewHolder(View itemView) {
            super(itemView);
            head = ((ImageView) itemView.findViewById(R.id.head_image));
            name = ((TextView) itemView.findViewById(R.id.name));
            desc = ((TextView) itemView.findViewById(R.id.desc));
            root = itemView.findViewById(R.id.root);
        }
    }
}
