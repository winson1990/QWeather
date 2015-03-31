package com.zhanjiqiang.qweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.zhanjiqiang.qweather.receiver.AutoUpdataReceiver;
import com.zhanjiqiang.qweather.utils.HttpCallbackListener;
import com.zhanjiqiang.qweather.utils.HttpUtils;
import com.zhanjiqiang.qweather.utils.UIUtils;
import com.zhanjiqiang.qweather.utils.Utility;

/**
 * @packageName:com.zhanjiqiang.qweather.service
 * @className:AutoUpdataService
 * @author:彳亍
 * @:2015/3/31 0031 16:13
 * @describe:后台自动更新
 */
public class AutoUpdataService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                updataWeather();
            }
        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //每隔4小时自动更新
        int anHour = 4 * 60 * 60 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(UIUtils.getContext(),AutoUpdataReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,i,0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 更新天气信息
     */
    private void updataWeather() {
        //Log.d("AutoUpdataService","更新了天气");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherCode = preferences.getString("weather_code","");
        String address = "http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
        HttpUtils.sendHttpRequest(address,new HttpCallbackListener() {
            @Override
            public void onFinsh(String response) {
                Utility.handleWeatherResponse(AutoUpdataService.this,response);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
