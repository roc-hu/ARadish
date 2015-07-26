package com.hcp.aradish.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hcp.aradish.BaseActivity;
import com.hcp.aradish.BaseFragment;
import com.hcp.aradish.R;
import com.hcp.aradish.bean.ListView1Bean;
import com.hcp.aradish.utils.ALog;
import com.hcp.aradish.utils.AppInfoUtils;
import com.hcp.aradish.utils.BitmapUtils;
import com.hcp.aradish.utils.DensityUtils;
import com.hcp.aradish.utils.SharedPrefsUtils;
import com.hcp.aradish.view.optionbar.OptionBar;
import com.hcp.aradish.view.optionbar.OptionBarItem;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


public class Main_Activity extends BaseActivity {

    private OptionBar mOptionBar;

    private Main_1_Fragment fragment1;
    private Main_2_ListView_Fragment fragment2;
    private Main_3_RecyclerView_Fragment fragment3;
    private Main_4_Fragment fragment4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

        EventBus.getDefault().register(mContext);

        DensityUtils.println();
        AppInfoUtils.println();
        test();
    }

    private void initView() {
        mOptionBar = findView(R.id.obar_menu);
        mOptionBar.setPadding(0, 10, 0, 10);
        mOptionBar.setOnTabClickListener(onTabClickListener);
    }

    String[] menus = {"菜单一", "菜单二", "菜单三", "菜单四"};
    Drawable icon;

    private void initData() {
        int w_h = DensityUtils.getScreenWidth() / 4;
        icon = BitmapUtils.zoomDrawable(getResources().getDrawable(R.mipmap.ic_launcher), w_h, w_h);

        ArrayList<OptionBarItem> optionBarItems = new ArrayList<OptionBarItem>();
        for (String menu : menus) {
            OptionBarItem optionBarItem = new OptionBarItem(menu, menu);
            optionBarItem.setTextColorXmlResId(R.color.tv_activity_main_menu_selector);
            optionBarItem.setTop(icon);
            optionBarItems.add(optionBarItem);
        }
        mOptionBar.setItemBeans(optionBarItems);

        fragment1 = new Main_1_Fragment();
        addFragment(fragment1);
    }

    private OptionBar.OnTabClickListener onTabClickListener = new OptionBar.OnTabClickListener() {
        @Override
        public void onItemClick(int position, Object itemBean) {
            switch (position) {
                case 0:
                    if (fragment1 == null)
                        fragment1 = new Main_1_Fragment();
                    replaceFragment(fragment1);
                    break;
                case 1:
                    if (fragment2 == null)
                        fragment2 = new Main_2_ListView_Fragment();
                    replaceFragment(fragment2);
                    break;
                case 2:
                    if (fragment3 == null)
                        fragment3 = new Main_3_RecyclerView_Fragment();
                    replaceFragment(fragment3);
                    break;
                case 3:
                    if (fragment4 == null)
                        fragment4 = new Main_4_Fragment();
                    replaceFragment(fragment4);
                    break;
                default:
                    Toast.makeText(mContext, "position:" + position, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(mContext);
        super.onDestroy();
    }
    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(mContext, "再次点击退出应用!", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    //==EventBus================================
    Handler mHandler = new Handler();

    /**
     * EventBus.getDefault()
     *
     * @param event
     */
    public void onEvent(String event) {
        Toast.makeText(mContext, "Main_Activity[onEvent]-->" + event, Toast.LENGTH_SHORT).show();
    }

    public void onEventMainThread(String event) {
        Toast.makeText(mContext, "Main_Activity[onEventMainThread]-->" + event, Toast.LENGTH_SHORT).show();
    }

    public void onEventBackgroundThread(final String event) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, "Main_Activity[onEventBackgroundThread]-->" + event, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onEventAsync(final String event) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, "Main_Activity[onEventAsync]-->" + event, Toast.LENGTH_SHORT).show();
            }
        });
    }
    //==End================================

    private void test() {
        SharedPrefsUtils.put("test1", "a");
        SharedPrefsUtils.put("test2", 798);
        SharedPrefsUtils.put("test3", true);

        ListView1Bean listView1Bean = new ListView1Bean();
        listView1Bean.title = "哈哈hkhk";
        listView1Bean.xmlResourcesId = 1232;
        boolean bo = SharedPrefsUtils.put("test4", listView1Bean);

        String test1 = SharedPrefsUtils.get("test1");
        int test2 = SharedPrefsUtils.get("test2", 0);
        boolean test3 = SharedPrefsUtils.get("test3", false);

        ListView1Bean bean = SharedPrefsUtils.get("test4", ListView1Bean.class);

        Log.e("hcp", test1 + "\t" + test2 + "\t" + test3 + "\t");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addFragment(BaseFragment fragment) {
        if (fragment != null) {
            String tag = fragment.getClass().getSimpleName();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.container, fragment, tag);
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commitAllowingStateLoss();
            ALog.i("addFragment:" + tag);
        }
    }

    private void replaceFragment(BaseFragment fragment) {
        if (fragment != null) {
            String tag = fragment.getClass().getSimpleName();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment, tag);
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commitAllowingStateLoss();
            ALog.i("replaceFragment:" + tag);
        }
    }

    private void removeFragment(BaseFragment fragment) {
        if (fragment != null) {
            String tag = fragment.getClass().getSimpleName();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commitAllowingStateLoss();
            ALog.i("removeFragment:" + tag);
        }
    }
}
