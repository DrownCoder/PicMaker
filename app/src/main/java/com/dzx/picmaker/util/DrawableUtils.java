package com.dzx.picmaker.util;

import android.graphics.drawable.GradientDrawable;
import android.view.View;

/**
 * Created by Xuan on 2017/5/14.
 */

public class DrawableUtils {
    /**
     * 设置以自定义drawable为背景的soild的颜色
     * @param view
     * @param color
     */
    public static void setShapeBackColor(View view, int color) {
        GradientDrawable drawable =(GradientDrawable)view.getBackground();
        drawable.setColor(color);
    }
}
