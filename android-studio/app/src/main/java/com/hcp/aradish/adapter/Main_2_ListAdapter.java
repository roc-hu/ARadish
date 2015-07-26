package com.hcp.aradish.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcp.aradish.R;
import com.hcp.aradish.newwork.ImageLoader;

import java.util.List;

/**
 * Created by hcp on 15/7/2.
 */
public class Main_2_ListAdapter extends SimpleBaseAdapter<String> {

    public Main_2_ListAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public int getConvertViewResourceId() {
        return R.layout.listview_item;
    }

    @Override
    public View getItemView(int position, View convertView) {
        ImageView ivIcon =ViewHolder.get(convertView,R.id.iv_icon);

        if(!TextUtils.isEmpty(getItem(position))){
            ImageLoader.display(context,getItem(position), ivIcon);
        }else{
            ivIcon.setImageResource(R.mipmap.ic_launcher);
        }

        TextView tvTitle = ViewHolder.get(convertView, R.id.tv_title);
        tvTitle.setText("标题" + position);
        return convertView;
    }
}
