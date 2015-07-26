package com.hcp.aradish.view.optionbar;

import android.graphics.drawable.Drawable;
import android.view.View;

import java.io.Serializable;

/**
 * Created by hcp on 15/5/8.
 */
public class OptionBarItem implements Serializable {
    private String title;//展示标题
    private String value;//实际值
    private Object tag;

    /**
     * padding
     * left/right/top/botto.
     */
    private int mPaddingLeft, mPaddingRight, mPaddingTop, mPaddingBottom;

    /**
     * defalut font size
     */
    private float textSize;
    /**
     * defalut font Unit
     */
    private int unit;
    /**
     * 文本的颜色 设置它了 textColor,textPressedColor,textSelectedColor的设置将会失效
     */
    private int textColorXmlResId;
    /**
     * 默认文本颜色
     */
    private int textColor;

    /**
     * 按下文本的颜色
     */
    private int textPressedColor;
    /**
     * 选中文本的颜色
     */
    private int textSelectedColor;

    private int leftXmlResId;
    private int topXmlResId;
    private int rightXmlResId;
    private int bottomXmlResId;

    private Drawable left;
    private Drawable top;
    private Drawable right;
    private Drawable bottom;

    private Drawable leftPressed;
    private Drawable topPressed;
    private Drawable rightPressed;
    private Drawable bottomPressed;

    private Drawable leftSelected;
    private Drawable topSelected;
    private Drawable rightSelected;
    private Drawable bottomSelected;

    private View floatingLayer;

    /**
     * 构造函数
     *
     * @param title 显示内容
     * @param value 实际值
     */
    public OptionBarItem(String title, String value) {
        this.setTitle(title);
        this.setValue(value);
    }
    public void setTextSize(int unit,float textSize) {
        this.unit=unit;
        this.textSize = textSize;
    }
    public void setTextColor(int textColor,int textPressedColor,int textSelectedColor) {
        this.textColor = textColor;
        this.textPressedColor=textPressedColor;
        this.textSelectedColor=textSelectedColor;
    }
    public void setPadding(int left, int top, int right, int bottom) {
        mPaddingLeft = left;
        mPaddingBottom = bottom;
        mPaddingRight = right;
        mPaddingTop = top;
    }
    /**
     * 文本的颜色 设置它了 textColor,textPressedColor,textSelectedColor的设置将会失效
     * 例如：R.color.tv_activity_main_menu_selector
     */
    public int getTextColorXmlResId() {
        return textColorXmlResId;
    }
    /**
     * 文本的颜色 设置它了 textColor,textPressedColor,textSelectedColor的设置将会失效
     * 例如：R.color.tv_activity_main_menu_selector
     */
    public void setTextColorXmlResId(int textColorXmlResId) {
        this.textColorXmlResId = textColorXmlResId;
    }

    public int getBottomXmlResId() {
        return bottomXmlResId;
    }

    public void setBottomXmlResId(int bottomXmlResId) {
        this.bottomXmlResId = bottomXmlResId;
    }

    public int getRightXmlResId() {
        return rightXmlResId;
    }

    public void setRightXmlResId(int rightXmlResId) {
        this.rightXmlResId = rightXmlResId;
    }

    public int getTopXmlResId() {
        return topXmlResId;
    }

    public void setTopXmlResId(int topXmlResId) {
        this.topXmlResId = topXmlResId;
    }

    public int getLeftXmlResId() {
        return leftXmlResId;
    }

    public void setLeftXmlResId(int leftXmlResId) {
        this.leftXmlResId = leftXmlResId;
    }

    public int getPaddingLeft() {
        return mPaddingLeft;
    }

    public int getPaddingRight() {
        return mPaddingRight;
    }

    public int getPaddingTop() {
        return mPaddingTop;
    }

    public int getPaddingBottom() {
        return mPaddingBottom;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextPressedColor() {
        return textPressedColor;
    }

    public void setTextPressedColor(int textPressedColor) {
        this.textPressedColor = textPressedColor;
    }

    public int getTextSelectedColor() {
        return textSelectedColor;
    }

    public void setTextSelectedColor(int textSelectedColor) {
        this.textSelectedColor = textSelectedColor;
    }

    public View getFloatingLayer() {
        return floatingLayer;
    }

    public void setFloatingLayer(View floatingLayer) {
        this.floatingLayer = floatingLayer;
    }

    public String getTitle() {
        return title;
    }

    public Drawable getLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(Drawable leftPressed) {
        this.leftPressed = leftPressed;
    }

    public Drawable getBottomSelected() {
        return bottomSelected;
    }

    public void setBottomSelected(Drawable bottomSelected) {
        this.bottomSelected = bottomSelected;
    }

    public Drawable getRightSelected() {
        return rightSelected;
    }

    public void setRightSelected(Drawable rightSelected) {
        this.rightSelected = rightSelected;
    }

    public Drawable getTopSelected() {
        return topSelected;
    }

    public void setTopSelected(Drawable topSelected) {
        this.topSelected = topSelected;
    }

    public Drawable getLeftSelected() {
        return leftSelected;
    }

    public void setLeftSelected(Drawable leftSelected) {
        this.leftSelected = leftSelected;
    }

    public Drawable getBottomPressed() {
        return bottomPressed;
    }

    public void setBottomPressed(Drawable bottomPressed) {
        this.bottomPressed = bottomPressed;
    }

    public Drawable getRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(Drawable rightPressed) {
        this.rightPressed = rightPressed;
    }

    public Drawable getTopPressed() {
        return topPressed;
    }

    public void setTopPressed(Drawable topPressed) {
        this.topPressed = topPressed;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getRight() {
        return right;
    }

    public void setRight(Drawable right) {
        this.right = right;
    }

    public Drawable getBottom() {
        return bottom;
    }

    public void setBottom(Drawable bottom) {
        this.bottom = bottom;
    }

    public Drawable getTop() {
        return top;
    }

    public void setTop(Drawable top) {
        this.top = top;
    }

    public Drawable getLeft() {
        return left;
    }

    public void setLeft(Drawable left) {
        this.left = left;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}
