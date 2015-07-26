package com.hcp.aradish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcp.aradish.R;
import com.hcp.aradish.newwork.ImageLoader;

import java.util.ArrayList;

/**
 * Created by hcp on 15/6/17.
 */
public class RecyclerViewAdpater extends RecyclerView.Adapter<RecyclerViewAdpater.ViewHolder>{

    private ArrayList<String> datas;
    private Context context;

    public RecyclerViewAdpater(Context mContext, ArrayList<String> mDatas) {
        context=mContext;
        datas = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, null);
        v.setOnClickListener(itemOnClickListener);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.mTvTitle.setText(datas.get(position));
        holder.itemView.setTag(datas.get(position));

        if(!TextUtils.isEmpty(datas.get(position))){
            ImageLoader.display(context, datas.get(position), holder.mIvIcon);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
    public void add(String item, int position) {
        datas.add(position, item);
        notifyItemInserted(position);
    }
    public void remove(String item) {
        int position = datas.indexOf(item);
        datas.remove(position);
        notifyItemRemoved(position);
    }
    private View.OnClickListener itemOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null&&v.getTag()!=null) {
                String data=v.getTag().toString();
                //注意这里使用getTag方法获取数据
                mOnItemClickListener.onItemClick(v,data);
            }
        }
    };

    // Provide a reference to the type of views that you are using
    // (custom viewholder)
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvTitle;
        public ImageView mIvIcon;
        public ViewHolder(View v) {
            super(v);
            mTvTitle=(TextView)v.findViewById(R.id.tv_title);
            mIvIcon=(ImageView)v.findViewById(R.id.iv_icon);
        }
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view,String data);
    }
}
