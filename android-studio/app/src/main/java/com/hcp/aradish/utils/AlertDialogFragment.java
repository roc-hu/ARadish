package com.hcp.aradish.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by hcp on 15/6/17.
 */
public class AlertDialogFragment extends DialogFragment {

    private static final String KEY_MESSAGE = "key_message";
    public static final String TAG = AlertDialogFragment.class.getSimpleName();

    private DialogInterface.OnClickListener okListener;
    private DialogInterface.OnClickListener cancelListener;
    private int positiveButtonRes = android.R.string.ok;

    public void setOkListener(DialogInterface.OnClickListener okListener) {
        this.okListener = okListener;
    }

    public void setCancelListener(DialogInterface.OnClickListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public void setPositiveButtonRes(int positiveButtonRes) {
        this.positiveButtonRes = positiveButtonRes;
    }

    public static AlertDialogFragment newInstance(final Context context,
                                                  final int resMessage) {
        return newInstance(context.getString(resMessage));
    }

    public static AlertDialogFragment newInstance(final String message,
                                                  boolean cancelable) {
        AlertDialogFragment pd = newInstance(message);
        pd.setCancelable(cancelable);
        return pd;
    }

    public static AlertDialogFragment newInstance(final String message) {
        final AlertDialogFragment progressDialogFragment = new AlertDialogFragment();
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
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getArguments().getString(KEY_MESSAGE))
                .setPositiveButton(positiveButtonRes,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                if (okListener != null) {
                                    okListener.onClick(dialog, id);
                                }
                            }
                        }
                );
        if (cancelListener != null) {
            builder.setNegativeButton(android.R.string.cancel, cancelListener);
        }
        return builder.create();
    }
}
