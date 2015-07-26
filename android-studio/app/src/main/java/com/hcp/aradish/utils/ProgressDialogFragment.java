package com.hcp.aradish.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by hcp on 15/6/17.
 */
public class ProgressDialogFragment extends DialogFragment {

    private static final String KEY_MESSAGE = "key_message";
    public static final String TAG = ProgressDialogFragment.class.getSimpleName();

    public static ProgressDialogFragment newInstance(final Context context,
                                                     final int resMessage) {
        return newInstance(context.getString(resMessage));
    }

    public static ProgressDialogFragment newInstance(final String message,
                                                     boolean cancelable) {
        ProgressDialogFragment pd = newInstance(message);
        pd.setCancelable(cancelable);
        return pd;
    }

    public static ProgressDialogFragment newInstance(final String message) {
        final ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
        progressDialogFragment.setShowsDialog(true);
        final Bundle b = new Bundle();
        b.putString(KEY_MESSAGE, message);
        progressDialogFragment.setArguments(b);
        return progressDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        return getDialog();
    }

    @Override
    public Dialog getDialog() {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage(getArguments().getString(KEY_MESSAGE));
        return pd;
    }
}
