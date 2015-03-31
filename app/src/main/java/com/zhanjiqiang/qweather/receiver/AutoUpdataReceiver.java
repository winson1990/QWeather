package com.zhanjiqiang.qweather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zhanjiqiang.qweather.service.AutoUpdataService;
import com.zhanjiqiang.qweather.utils.UIUtils;

/**
 * @packageName:com.zhanjiqiang.qweather.receiver
 * @className:AutoUpdataReceiver
 * @author:彳亍
 * @:2015/3/31 0031 16:29
 * @describe:更新天气的广播接受者
 */
public class AutoUpdataReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(UIUtils.getContext(), AutoUpdataService.class);
        context.startService(i);
    }
}
