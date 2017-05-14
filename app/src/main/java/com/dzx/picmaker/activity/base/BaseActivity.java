package com.dzx.picmaker.activity.base;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dzx.picmaker.R;
import com.dzx.picmaker.widget.CustomDialog;

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


    private CustomDialog dialog;

    public void TopView() {
        //透明状态栏
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            Window window = getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(getResources().getColor(R.color.black));

            ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
                ViewCompat.setFitsSystemWindows(mChildView, true);
            }
        }
    }

    public void setLeftBack() {
        ImageView iv_id_leftbtn = (ImageView) findViewById(R.id.title_left_btn);
        iv_id_leftbtn.setImageResource(R.drawable.left);
        iv_id_leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setTitle(String title) {
        ((TextView) findViewById(R.id.title_middle)).setText(title);
    }

    public String getCustomTitle(){
        return ((TextView)findViewById(R.id.title_middle)).getText().toString();
    }

    public void setRightIcon(int imgId) {
        ((ImageView) findViewById(R.id.title_right_btn)).setImageResource(imgId);
    }

    public void setRightClickedListener(View.OnClickListener onClickListener) {
        ((TextView) findViewById(R.id.title_right)).setOnClickListener(onClickListener);
    }

    public void setRightText(String str) {
        ((TextView) findViewById(R.id.title_right)).setText(str);
    }

    public void showProgressDialog(Context context, String str) {
        ProgressBar bar = new ProgressBar(context);
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setTitle(str)
                .setContentView(bar);
        dialog = builder.create();
        dialog.show();
    }

    public void dismissProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public void ToastMsg(Context context,String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
