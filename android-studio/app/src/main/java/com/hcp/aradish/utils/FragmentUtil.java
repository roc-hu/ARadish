package com.hcp.aradish.utils;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by hcp on 15/6/17.
 */
public class FragmentUtil {

    public static final String message_progress="Please wait...";
    public static final String TAG = FragmentUtil.class.getSimpleName();

    public static void replaceFragmentToLayout(final int containerId, final FragmentManager fragmentManager,
                                               final Fragment fragment, final String tag) {
        final FragmentTransaction ft = fragmentManager.beginTransaction();
        final Fragment previousFragment = fragmentManager.findFragmentByTag(tag);
        if (previousFragment != null) {
            ft.remove(previousFragment);
        }
        ft.add(containerId, fragment, tag);
        ft.commit();
    }

    public static void addFragmentToLayout(final int containerId, final FragmentManager fragmentManager,
                                           final Fragment fragment, final String tag) {
        final FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(containerId, fragment, tag);
        ft.commit();
    }

    public static void removeDialogFragment(final String tag,final FragmentManager fragmentManager) {
        if (fragmentManager == null) {
            return;
        }
        final FragmentTransaction ft = fragmentManager.beginTransaction();
        final Fragment prev = fragmentManager.findFragmentByTag(tag);
        if (prev != null && prev.isAdded()) {
            ft.remove(prev);
        }
        ft.commitAllowingStateLoss();
    }

    public static void showDialogFragment(final DialogFragment dialog,
                                          final String tag, final FragmentManager fragmentManager) {
        final FragmentTransaction ft = fragmentManager.beginTransaction();
        final Fragment prev = fragmentManager.findFragmentByTag(tag);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.add(dialog, tag);
        ft.commitAllowingStateLoss();
    }


    public static void showProgressDialog(FragmentManager manager, boolean cancelable) {
        showProgressDialog(manager, message_progress, cancelable);
    }
    public static void showProgressDialog(FragmentManager manager,String message, boolean cancelable) {
        final ProgressDialogFragment progressDialogFragment = ProgressDialogFragment.newInstance(message, cancelable);
        showDialogFragment(progressDialogFragment, ProgressDialogFragment.TAG,
                manager);
    }

    public static void clearProgressDialog(FragmentManager manager) {
        try {
            removeDialogFragment(ProgressDialogFragment.TAG, manager);
        } catch (Exception e) {
        }
    }

    public static void replaceFragment(FragmentManager manager, int parent,
                                       Fragment fragment) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(parent, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
