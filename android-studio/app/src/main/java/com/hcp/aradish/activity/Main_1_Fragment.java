package com.hcp.aradish.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hcp.aradish.BaseFragment;
import com.hcp.aradish.R;
import com.hcp.aradish.adapter.ListView1Adapter;
import com.hcp.aradish.bean.ListView1Bean;
import com.hcp.aradish.data.Data;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * A placeholder fragment containing a simple view.
 */
public class Main_1_Fragment extends BaseFragment {

    private final String TAG = getClass().getSimpleName();

    private ListView listView;
    private ListView1Adapter adapter;
    private List<ListView1Bean> datas = new ArrayList<ListView1Bean>();

    public Main_1_Fragment() {
        super();
    }

    @Override
    public boolean onBackPressedFragment() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main1, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        TextView textView = findView(R.id.tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post("胡昌鹏");
            }
        });

        Button btn = findView(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDialog();
//                showVinTipsDialog();
//                EventBus.getDefault().post(1);
                Intent intent = new Intent(mContext, Main_Detail_Activity.class);
                mContext.startActivity(intent);
            }
        });
        listView = findView(R.id.listView);
        adapter = new ListView1Adapter(mContext, datas);
        listView.setAdapter(adapter);
//        listView.setEmptyView();
    }


    private void initData() {
        mContext.setTitle("Main_1_Fragment");
        for (int i = 0; i < Data.Urls.length; i++) {
            ListView1Bean bean = new ListView1Bean();
            if (i == 0 || i == 2 || i == 4) {
                bean.xmlResourcesId = R.layout.text_adapter_item;
            } else {
                bean.xmlResourcesId = R.layout.image_adapter_item;
            }
            bean.title = Data.Urls[i];
            datas.add(bean);
        }
        adapter.notifyDataSetChanged();
    }

    private void showDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle("ARadish Dialog");
        dialog.setMessage("This My ARadish!");
        dialog.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showVinTipsDialog() {
        Dialog dialog = new Dialog(mContext, R.style.dialog);
        TextView textView = new TextView(mContext);
        textView.setText("Dialog!");
        textView.setBackgroundResource(R.drawable.bg_fillet);
        dialog.setContentView(textView);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);

        WindowManager windowManager = mContext.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 0.8);
        dialog.getWindow().setAttributes(lp);
    }
}
