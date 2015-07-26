package com.hcp.aradish.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcp.aradish.R;
import com.hcp.aradish.newwork.ImageLoader;

import java.util.ArrayList;

/**
 * Created by hcp on 15/6/17.
 */
public class DemoAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<String> datas;

    public DemoAdapter(Context mContext,ArrayList<String> mDatas){
        context=mContext;
        datas=mDatas;
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public String getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item, parent, false);
        }
        ImageView ivIcon = ViewHolder.get(convertView, R.id.iv_icon);
        if (!TextUtils.isEmpty(datas.get(position))) {
            ImageLoader.display(context, getItem(position), ivIcon);
        } else {
            ivIcon.setImageResource(R.mipmap.ic_launcher);
        }
        TextView tvTitle =ViewHolder.get(convertView,R.id.tv_title);
        tvTitle.setText("标题" + position);
        return convertView;
    }
}
