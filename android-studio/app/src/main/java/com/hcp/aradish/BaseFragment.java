package com.hcp.aradish;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.hcp.aradish.newwork.HttpRequest;

/**
 * Created by hcp on 15/6/17.
 */
public abstract class BaseFragment extends Fragment {

    protected FragmentActivity mContext;

    protected HttpRequest request;

    protected IFragmentManage iFragmentManage;

    public BaseFragment(){
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        if(!(mContext instanceof IFragmentManage)){
            throw new ClassCastException("Hosting Activity must implement IFragmentManage");
        }else{
            this.iFragmentManage = (IFragmentManage)getActivity();
        }
    }
//    public View findViewById(int id) {
//        return getView().findViewById(id);
//    }
    @Override
    public void onStart() {
        //告诉FragmentActivity，当前Fragment在栈顶
        iFragmentManage.setSelectedFragment(this);
        super.onStart();
    }

    /**
     * 查找页面view
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T findView(int id){
        return (T)getView().findViewById(id);
    }
    /**
     * 所有继承BackHandledFragment的子类都将在这个方法中实现物理Back键按下后的逻辑
     * FragmentActivity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
     * 如果没有Fragment消息时FragmentActivity自己才会消费该事件
     */
    public abstract boolean onBackPressedFragment();
}
