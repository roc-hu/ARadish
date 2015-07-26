package com.hcp.aradish.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hcp.aradish.BaseFragment;
import com.hcp.aradish.R;
import com.hcp.aradish.adapter.RecyclerViewAdpater;
import com.hcp.aradish.constants.Constant;
import com.hcp.aradish.data.Data;
import com.hcp.aradish.newwork.APIHelper;
import com.hcp.aradish.newwork.HttpRequest;
import com.hcp.aradish.utils.FragmentUtil;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class Main_3_RecyclerView_Fragment extends BaseFragment implements
        RecyclerViewAdpater.OnRecyclerViewItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private final String TAG = getClass().getSimpleName();

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRv;
    private RecyclerViewAdpater mAdapter;
    private ArrayList<String> mDatas=new ArrayList<String>();

    public Main_3_RecyclerView_Fragment() {
        super();
    }

    @Override
    public boolean onBackPressedFragment() {
        return true;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main3_recyclerview, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }
    private void initView(){
        mRefreshLayout=findView(R.id.swipe_container);
        mRefreshLayout.setOnRefreshListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                    android.R.color.holo_orange_light, android.R.color.holo_red_light);
        }else{
            mRefreshLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                    android.R.color.holo_orange_light, android.R.color.holo_red_light);
        }

        mRv =findView(R.id.recyclerView);
        mRv.setItemAnimator(new DefaultItemAnimator());
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //GridLayoutManager layoutManager=new GridLayoutManager(mContext,2);
        //创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        // 设置布局管理器
        mRv.setLayoutManager(layoutManager);

        mAdapter=new RecyclerViewAdpater(mContext,mDatas);
        mAdapter.setOnItemClickListener(this);
        mRv.setAdapter(mAdapter);

    }
    private void initData(){
        mContext.setTitle("Main_3_RecyclerView_Fragment");
        onRefresh();
    }

    /**
     * RecyclerViewAdpater.OnRecyclerViewItemClickListener
     * @param view
     * @param data
     */
    @Override
    public void onItemClick(View view, String data) {
        mAdapter.remove(data);
        Toast.makeText(mContext, "OnItemClick:" + data, Toast.LENGTH_SHORT).show();
    }

    /**
     * 下拉刷新
     * SwipeRefreshLayout.OnRefreshListener
     */
    @Override
    public void onRefresh() {
        FragmentUtil.showProgressDialog(mContext.getSupportFragmentManager(), false);
        request= APIHelper.getInstance().get(mContext, Constant.HTTP_BAIDU);
        request.setHttpRequestListener(new HttpRequest.HttpRequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                mRefreshLayout.setRefreshing(false);
                FragmentUtil.clearProgressDialog(mContext.getSupportFragmentManager());
                mDatas.clear();
                for (int i=0;i< Data.Urls.length;i++){
                    mDatas.add( Data.Urls[i]);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(HttpRequest.HttpError error) {
                mRefreshLayout.setRefreshing(false);
                FragmentUtil.clearProgressDialog(mContext.getSupportFragmentManager());
                Toast.makeText(mContext, "HttpError:" + error, Toast.LENGTH_LONG).show();
            }
        });
        request.start();
    }
}
