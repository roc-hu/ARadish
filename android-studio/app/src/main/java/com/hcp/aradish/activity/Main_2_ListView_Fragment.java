package com.hcp.aradish.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hcp.aradish.BaseFragment;
import com.hcp.aradish.R;
import com.hcp.aradish.adapter.Main_2_ListAdapter;
import com.hcp.aradish.constants.Constant;
import com.hcp.aradish.data.Data;
import com.hcp.aradish.newwork.APIHelper;
import com.hcp.aradish.newwork.HttpRequest;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class Main_2_ListView_Fragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        AdapterView.OnItemClickListener,
        AbsListView.OnScrollListener {

    private final String TAG = getClass().getSimpleName();

    private SwipeRefreshLayout mRefreshLayout;
    private ListView mLv;
    private View vMore;
    private Main_2_ListAdapter mAdapter;
    private ArrayList<String> mDatas = new ArrayList<String>();

    public Main_2_ListView_Fragment() {
        super();
    }
    @Override
    public boolean onBackPressedFragment() {
        return true;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main2_listview, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        mRefreshLayout = findView(R.id.swipe_container);
        mRefreshLayout.setOnRefreshListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                    android.R.color.holo_orange_light, android.R.color.holo_red_light);
        } else {
            mRefreshLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                    android.R.color.holo_orange_light, android.R.color.holo_red_light);
        }

        mLv = findView(R.id.listView);
        mLv.setOnItemClickListener(this);
        mLv.setOnScrollListener(this);

        //没有数据
        mLv.setEmptyView(findView(R.id.empty));
        //更多样式
        if (vMore == null) {
            vMore = View.inflate(getActivity(), R.layout.view_listview_more, null);
        }
        vMore.setVisibility(View.GONE);
        mLv.addFooterView(vMore);

        mAdapter = new Main_2_ListAdapter(mContext, mDatas);
        mLv.setAdapter(mAdapter);
    }

    private void initData() {
        mContext.setTitle("Main_2_ListView_Fragment");
//        onRefresh();
    }

    /**
     * 下拉刷新
     * SwipeRefreshLayout.OnRefreshListener
     */
    @Override
    public void onRefresh() {
        if (request != null) {
            request.cancel();
        }
        vMore.setVisibility(View.GONE);
//        FragmentUtil.showProgressDialog(mContext.getSupportFragmentManager(),false);
        request = APIHelper.getInstance().get(mContext, Constant.HTTP_BAIDU);
        request.setHttpRequestListener(new HttpRequest.HttpRequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                mRefreshLayout.setRefreshing(false);
                mDatas.clear();
                for (int i = 0; i < Data.Urls.length; i++) {
                    mDatas.add(Data.Urls[i]);
                }
                mAdapter.replaceAll(mDatas);
//                FragmentUtil.clearProgressDialog(mContext.getSupportFragmentManager());
//                String url="http://attach.bbs.miui.com/forum/201309/09/221555fq9mnlzlw2m0qjzb.jpg";
//                Bitmap bitmap= Glide.with(Main_2_ListView_Fragment.this).load(url)
            }

            @Override
            public void onError(HttpRequest.HttpError error) {
                mRefreshLayout.setRefreshing(false);
//                FragmentUtil.clearProgressDialog(mContext.getSupportFragmentManager());
                if (HttpRequest.HttpError.CANCEl != error) {
                    Toast.makeText(mContext, "HttpError:" + error, Toast.LENGTH_LONG).show();
                }
            }
        });
        request.start();
    }

    /**
     * 加载更多
     */
    private void onMore() {
        if (request != null) {
            request.cancel();
        }
        vMore.setVisibility(View.VISIBLE);
        vMore.findViewById(R.id.pb_more).setVisibility(View.VISIBLE);
        ((TextView)vMore.findViewById(R.id.tv_more)).setText("加载更多中...");
        request = APIHelper.getInstance().get(mContext, Constant.HTTP_BAIDU);
        request.setHttpRequestListener(new HttpRequest.HttpRequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                if(mDatas.size()>100){
                    vMore.findViewById(R.id.pb_more).setVisibility(View.GONE);
                    ((TextView)vMore.findViewById(R.id.tv_more)).setText("没有更多内容了!");
                }else{
                    vMore.setVisibility(View.GONE);
                    if (Data.Urls.length > 5) {
                        for (int i = 0; i < 5; i++) {
                            mDatas.add(Data.Urls[i]);
                        }
                    }
                    mAdapter.replaceAll(mDatas);
                }
            }

            @Override
            public void onError(HttpRequest.HttpError error) {
                vMore.setVisibility(View.GONE);
                if (HttpRequest.HttpError.CANCEl != error) {
                    Toast.makeText(mContext, "HttpError:" + error, Toast.LENGTH_LONG).show();
                }
            }
        });
        request.start();
    }

    /**
     * AdapterView.OnItemClickListener
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(mContext, "OnItemClick:" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //首页菜单
        mContext.findViewById(R.id.obar_menu).setVisibility(View.GONE);
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 当不滚动时
//                Log.v("已经停止：SCROLL_STATE_IDLE");
                // 判断滚动到底部
                if (!mRefreshLayout.isRefreshing() && view.getLastVisiblePosition() == (view.getCount() - 1)) {
                    onMore();
                } else {
                    //首页菜单
                    mContext.findViewById(R.id.obar_menu).setVisibility(View.VISIBLE);
//                   vPlayer.setVisibility(View.VISIBLE);
                }
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
//                Log.v("开始滚动：SCROLL_STATE_FLING");
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//            	Log.v("正在滚动：SCROLL_STATE_TOUCH_SCROLL");
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        View firstView = view.getChildAt(firstVisibleItem);
        // firstVisibleItem是第0位 并且
        // firstView==null说明列表为空，或者top==0说明已经到达列表顶部, 需要刷新
        if (firstVisibleItem == 0 && (firstView == null || firstView.getTop() == 0)) {
            mRefreshLayout.setEnabled(true);
        }else{
            mRefreshLayout.setEnabled(false);
        }
    }
}
