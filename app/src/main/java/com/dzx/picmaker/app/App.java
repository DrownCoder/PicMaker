package com.dzx.picmaker.app;

import android.app.Application;

/**
 * Created by dengzhaoxuan on 2017/4/27.
 */

public class App extends Application{
    protected static App       mInstance;
    public App(){
        mInstance = this;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }
    public static App getApp() {
        if (mInstance != null && mInstance instanceof App) {
            return (App) mInstance;
        } else {
            mInstance = new App();
            mInstance.onCreate();
            return (App) mInstance;
        }
    }
    //获取应用的data/data/....Cache目录
    public String getCacheDirPath() {
        return getCacheDir().getAbsolutePath();
    }
}
