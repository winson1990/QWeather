package com.zhanjiqiang.qweather.utils;

import android.app.Application;
import android.content.Context;

/**
 * @packageName:com.zhanjiqiang.qweather.utils
 * @className:BaseApplication
 * @author:彳亍
 * @:2015/3/29 0029 22:06
 * @describe:应用程序入口
 */
public class BaseApplication extends Application{
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
    public static Context getContext(){
        return mContext;
    }
}
