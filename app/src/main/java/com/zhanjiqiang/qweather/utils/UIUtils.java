package com.zhanjiqiang.qweather.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * @packageName:com.zhanjiqiang.qweather.utils
 * @className:UIUtils
 * @author:彳亍
 * @:2015/3/29 0029 22:56
 * @describe:全局工具类
 */
public class UIUtils {
    /**
     * 获取上下文
     * @return Context
     */
    public static Context getContext(){
        return BaseApplication.getContext();
    }

    /**
     * 获取资源
     * @return resources
     */
    public static Resources getResources(){
        return BaseApplication.getContext().getResources();
    }
}
