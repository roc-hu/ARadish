package com.hcp.aradish.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class Main_4_Fragment extends BaseFragment {

    private final String TAG = getClass().getSimpleName();

    private RecyclerView mRv;
    private RecyclerViewAdpater mAdapter;
    private ArrayList<String> mDatas = new ArrayList<String>();

    public Main_4_Fragment() {
        super();
    }

    @Override
    public boolean onBackPressedFragment() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main4, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initView();
        initData();
    }

    private void initView() {
        mRv = findView(R.id.recyclerView);
        mRv.setItemAnimator(new DefaultItemAnimator());

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        GridLayoutManager layoutManager=new GridLayoutManager(mContext,2);
        //创建一个线性布局管理器
//        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        // 设置布局管理器
        mRv.setLayoutManager(layoutManager);

        mAdapter = new RecyclerViewAdpater(mContext, mDatas);
        mAdapter.setOnItemClickListener(onRecyclerViewItemClickListener);
        mRv.setAdapter(mAdapter);

    }

    private void initData() {
        mContext.setTitle("Main_4_RecyclerView_Fragment");
        request = APIHelper.getInstance().get(mContext, Constant.HTTP_BAIDU);
        request.setHttpRequestListener(new HttpRequest.HttpRequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                textView.setText(Html.fromHtml(response,imageGetter, null));
//                Toast.makeText(mContext, response, Toast.LENGTH_LONG).show();
                for (int i = 0; i < Data.Urls.length; i++) {
                    mDatas.add(Data.Urls[i]);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(HttpRequest.HttpError error) {
                Toast.makeText(mContext, "HttpError:" + error, Toast.LENGTH_LONG).show();
            }
        });
        request.start();
    }

    private RecyclerViewAdpater.OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = new RecyclerViewAdpater.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, String data) {
            mAdapter.remove(data);
            Toast.makeText(mContext, "OnItemClick:" + data, Toast.LENGTH_SHORT).show();
        }
    };
}

