package com.zhanjiqiang.qweather.utils;

/**
 * @packageName:com.zhanjiqiang.qweather.utils
 * @className:HttpCallbackListener
 * @author:彳亍
 * @:2015/3/30 0030 00:17
 * @describe:访问网络返回的状态
 */
public interface HttpCallbackListener {
    void onFinsh(String response);
    void onError(Exception e);
}
