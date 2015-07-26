package com.hcp.aradish;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 * Fragment 返回按钮回调
 * Created by hcp on 15/7/10.
 */
public interface IFragmentManage {
    /**
     * 返回按钮回调
     * @param selectedFragment
     */
    abstract void setSelectedFragment(BaseFragment selectedFragment);

}
