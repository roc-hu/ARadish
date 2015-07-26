package com.hcp.aradish.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcp.aradish.R;
import com.hcp.aradish.bean.ListView1Bean;
import com.hcp.aradish.newwork.ImageLoader;

import java.util.List;

/**
 * Created by hcp on 15/6/17.
 */
public class ListView1Adapter extends BaseAdapter {

    private Context context;
    private List<ListView1Bean> datas;

    public ListView1Adapter(Context mContext, List<ListView1Bean> mDatas) {
        context = mContext;
        datas = mDatas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.image_adapter_item, parent, false);
//        }
        convertView=inflate(context,datas.get(position),convertView,parent);

        switch (datas.get(position).xmlResourcesId){
            case R.layout.image_adapter_item:
                ImageView imageView = findView(convertView, R.id.imageView);
//                imageView.setImageResource(R.drawable.kale);
                ImageLoader.display(context,datas.get(position).title,imageView);
                break;
            case R.layout.text_adapter_item:
                TextView textView=findView(convertView,R.id.textView);
                textView.setText(datas.get(position).title);
                break;
        }
        return convertView;
    }

    private static <T extends View> T findView(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    private static SparseArray<View> convertViews = new SparseArray<View>();

    private static View inflate(Context context, ListView1Bean adapterBean, View convertView, ViewGroup parent) {
        if (convertViews.get(adapterBean.xmlResourcesId) == null) {
            if(convertView==null)
                convertView = LayoutInflater.from(context).inflate(adapterBean.xmlResourcesId, parent, false);
            convertViews.put(adapterBean.xmlResourcesId, convertView);
        }
        return convertViews.get(adapterBean.xmlResourcesId);
    }
}
