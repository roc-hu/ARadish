package com.hcp.aradish.view.optionbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by hcp on 15/5/8.
 * 底部菜单样式和筛选样式
 */
public class OptionBar extends HorizontalScrollView {

    /**
     * 数据对象
     */
    private ArrayList<OptionBarItem> mItemBeans;

    private int mSelectedTabIndex = 0;
    private Runnable mTabSelector;

    private OnTabClickListener mClickListener;
    private final LinearLayout mTabLayout;
    private int mMaxTabWidth;

    /**
     * 构造函数
     *
     * @param context
     */
    public OptionBar(Context context) {
        this(context, null);
    }

    /**
     * 构造函数
     *
     * @param context
     * @param attrs
     */
    public OptionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setHorizontalScrollBarEnabled(false);
        mTabLayout = new LinearLayout(context);
        addView(mTabLayout, new ViewGroup.LayoutParams(WRAP_CONTENT, MATCH_PARENT));
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;
        setFillViewport(lockedExpanded);

        final int childCount = mTabLayout.getChildCount();
        if (childCount > 1 && (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST)) {
            if (childCount > 2) {
                mMaxTabWidth = (int) (MeasureSpec.getSize(widthMeasureSpec) * 0.4f);
            } else {
                mMaxTabWidth = MeasureSpec.getSize(widthMeasureSpec) / 2;
            }
        } else {
            mMaxTabWidth = -1;
        }

        final int oldWidth = getMeasuredWidth();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int newWidth = getMeasuredWidth();

        if (lockedExpanded && oldWidth != newWidth) {
            // Recenter the tab display if we're at a new (scrollable) size.
            setCurrentItem(mSelectedTabIndex);
        }

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mTabSelector != null) {
            // Re-post the selector we saved
            post(mTabSelector);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
    }

    private void animateToTab(final int position) {
        final View tabView = mTabLayout.getChildAt(position);
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
        mTabSelector = new Runnable() {
            @Override
            public void run() {
                final int scrollPos = tabView.getLeft() - (getWidth() - tabView.getWidth()) / 2;
                smoothScrollTo(scrollPos, 0);
                mTabSelector = null;
            }
        };
        post(mTabSelector);
    }

    public void setOnTabClickListener(OnTabClickListener mOnTabClickListener) {
        this.mClickListener = mOnTabClickListener;
    }

    public void setCurrentItem(int index) {
        mSelectedTabIndex = index;
        final int tabCount = mTabLayout.getChildCount();
        for (int i = 0; i < tabCount; i++) {

            final boolean isSelected = (i == index);

            if (mTabLayout.getChildAt(i) instanceof TabLayout) {
                final TabLayout tabChild = (TabLayout) mTabLayout.getChildAt(i);
                tabChild.setSelected(isSelected);
                tabChild.getTextView().setSelected(isSelected);
                if (tabChild.getFloatingLayer() != null) {
                    tabChild.getFloatingLayer().setSelected(isSelected);
                }
            }
            if (isSelected) {
                animateToTab(index);//滚动
            }
        }
    }

    /**
     * 添加底部导航数据
     *
     * @param mItemBeans
     */
    public void setItemBeans(ArrayList<OptionBarItem> mItemBeans) {
        this.mItemBeans = mItemBeans;
        initView();
        setCurrentItem(mSelectedTabIndex);
    }

    /**
     * 初始化View
     */
    private void initView() {
        mTabLayout.removeAllViews();
        int count = mItemBeans.size();
        for (int i = 0; i < count; i++) {
            // 添加底部导航样式
            final TabLayout tabLayout = new TabLayout(getContext(), mItemBeans.get(i));
            tabLayout.mIndex = i;
            tabLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCurrentItem(tabLayout.getIndex());
                    if (mClickListener != null) {
                        mClickListener.onItemClick(tabLayout.getIndex(), mItemBeans.get(tabLayout.getIndex()));
                    }
                }
            });
            mTabLayout.addView(tabLayout);
        }
        requestLayout();
    }

    /**
     * 得到选中item的索引
     *
     * @return
     */
    public int getSelectedTabIndex() {
        return mSelectedTabIndex;
    }

    public interface OnTabClickListener {

        void onItemClick(int position, Object itemBean);

    }

    /**
     * TabItemView容器
     */
    private class TabLayout extends RelativeLayout {
        private int mIndex;
        private OptionBarItem mItem;
        private TextView mTextView;

        public TabLayout(Context context, OptionBarItem optionBarItem) {
            super(context);
            mItem = optionBarItem;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT, 1);
            params.gravity = Gravity.CENTER;
            setLayoutParams(params);

            mTextView = getTvItemView();
            addView(mTextView);
            if (mItem != null && mItem.getFloatingLayer() != null) {
                addView(mItem.getFloatingLayer());
            }
        }

        private TextView getTvItemView() {
            TextView textView = new TextView(getContext());
            textView.setFocusable(true);
            textView.setSingleLine(true);
            textView.setGravity(Gravity.CENTER);
            if (mItem != null) {
                textView.setText(mItem.getTitle());
                if(mItem.getTextSize()==0){
                    mItem.setTextSize(12);
                    mItem.setUnit(TypedValue.COMPLEX_UNIT_SP);
                }
                textView.setTextSize(mItem.getUnit(), mItem.getTextSize());
                textView.setPadding(
                        mItem.getPaddingLeft(), mItem.getPaddingTop(),
                        mItem.getPaddingRight(), mItem.getPaddingBottom());
                if (mItem.getTextColorXmlResId() != 0) {//如果有资源id以资源id优先
                    textView.setTextColor(getResources().getColorStateList(mItem.getTextColorXmlResId()));
                } else {
                    textView.setTextColor(
                            createColorStateList(mItem.getTextColor(), mItem.getTextPressedColor(), mItem.getTextSelectedColor()));
                }
                if(mItem.getTopXmlResId()!=0||mItem.getBottomXmlResId()!=0
                        ||mItem.getLeftXmlResId()!=0||mItem.getRightXmlResId()!=0){//如果有资源id以资源id优先
                    textView.setCompoundDrawablesWithIntrinsicBounds(mItem.getLeftXmlResId(),mItem.getTopXmlResId(),
                            mItem.getRightXmlResId(),mItem.getBottomXmlResId());
                }else{
                    textView.setCompoundDrawablesWithIntrinsicBounds(
                            createDrawableSelector(mItem.getLeft(), mItem.getLeftPressed(), mItem.getLeftSelected()),
                            createDrawableSelector(mItem.getTop(), mItem.getTopPressed(), mItem.getTopSelected()),
                            createDrawableSelector(mItem.getRight(), mItem.getRightPressed(), mItem.getRightSelected()),
                            createDrawableSelector(mItem.getBottom(), mItem.getBottomPressed(), mItem.getBottomSelected()));
                }
            }
            RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            tvParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            tvParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            textView.setLayoutParams(tvParams);
            return textView;
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            // Re-measure if we went beyond our maximum size.
            if (mMaxTabWidth > 0 && getMeasuredWidth() > mMaxTabWidth) {
//                super.onMeasure(MeasureSpec.makeMeasureSpec(mMaxTabWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
                super.onMeasure(MeasureSpec.makeMeasureSpec(mMaxTabWidth, MeasureSpec.UNSPECIFIED), heightMeasureSpec);
            }
        }

        public int getIndex() {
            return mIndex;
        }

        public OptionBarItem getItem() {
            return mItem;
        }

        public TextView getTextView() {
            return mTextView;
        }

        public View getFloatingLayer() {
            return mItem.getFloatingLayer();
        }
    }

    /**
     * 对TextView设置不同状态时其文字颜色
     *
     * @param normal
     * @param pressed
     * @param focused
     * @return
     */
    public ColorStateList createColorStateList(int normal, int pressed, int focused) {
        int[] colors = new int[]{pressed, focused, focused, normal, focused, focused, normal, normal};
        int[][] states = new int[8][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_enabled, android.R.attr.state_selected};
        states[3] = new int[]{android.R.attr.state_enabled};
        states[4] = new int[]{android.R.attr.state_focused};
        states[5] = new int[]{android.R.attr.state_selected};
        states[6] = new int[]{android.R.attr.state_window_focused};
        states[7] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    /**
     * 设置Selector
     *
     * @param normal
     * @param pressed
     * @param focused
     * @return
     */
    public static StateListDrawable createDrawableSelector(Drawable normal, Drawable pressed, Drawable focused) {
        StateListDrawable bg = new StateListDrawable();
        bg.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        bg.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, focused);
        bg.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_selected}, focused);
        bg.addState(new int[]{android.R.attr.state_enabled}, normal);
        bg.addState(new int[]{android.R.attr.state_focused}, focused);
        bg.addState(new int[]{android.R.attr.state_selected}, focused);
        bg.addState(new int[]{android.R.attr.state_window_focused}, normal);
        bg.addState(new int[]{}, normal);
        return bg;
    }
}
