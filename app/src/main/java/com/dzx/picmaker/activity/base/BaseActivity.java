package com.dzx.picmaker.activity.base;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by dengzhaoxuan on 2017/4/26.
 * 所有Activity父类
 */

public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 初始化事件
     */
    protected abstract void initEvents();
    /**
     * 初始化数据源
     */
    protected abstract void initDatas();

    /**
     * 设置以自定义drawable为背景的soild的颜色
     * @param view
     * @param color
     */
    private void setShapeBackColor(View view, int color) {
        GradientDrawable drawable =(GradientDrawable)view.getBackground();
        drawable.setColor(color);
    }
}
