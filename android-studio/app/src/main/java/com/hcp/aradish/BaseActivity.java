package com.hcp.aradish;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hcp.aradish.utils.ALog;

/**
 * Created by hcp on 15/7/10.
 */
public class BaseActivity extends AppCompatActivity implements IFragmentManage {

    protected FragmentActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }


    /**
     * 查找页面view
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    public BaseFragment mBaseFragment;

    @Override
    public void setSelectedFragment(BaseFragment selectedBaseFragment) {
        this.mBaseFragment = selectedBaseFragment;
    }

}
