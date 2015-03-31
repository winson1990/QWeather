package com.zhanjiqiang.qweather.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhanjiqiang.qweather.R;
import com.zhanjiqiang.qweather.utils.HttpCallbackListener;
import com.zhanjiqiang.qweather.utils.HttpUtils;
import com.zhanjiqiang.qweather.utils.UIUtils;
import com.zhanjiqiang.qweather.utils.Utility;

/**
 * @packageName:com.zhanjiqiang.qweather.activity
 * @className:WeatherActivity
 * @author:彳亍
 * @:2015/3/30 0030 23:15
 * @describe:天气界面的活动
 */
public class WeatherActivity extends Activity {
    private LinearLayout weatherInfoLayout;
    /**
     * 显示城市名称
     */
    private TextView cityName;
    /**
     * 显示发布时间
     */
    private TextView publishTime;
    /**
     * 显示当前日期
     */
    private TextView data;
    /**
     * 显示天气状态
     */
    private TextView weatherDespText;
    /**
     * 显示最低温度
     */
    private TextView lowTemperature;
    /**
     * 显示最高温度
     */
    private TextView highTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    /**
     * View初始化
     */
    public void initView() {
        setContentView(R.layout.weather_layout);
        weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
        cityName = (TextView) findViewById(R.id.weather_city_name);
        publishTime = (TextView) findViewById(R.id.weather_publish_data);
        data = (TextView) findViewById(R.id.weather_data);
        weatherDespText = (TextView) findViewById(R.id.weather_status);
        lowTemperature = (TextView) findViewById(R.id.weather_low_temperature);
        highTemperature = (TextView) findViewById(R.id.weather_high_temperature);
        String countyCode = getIntent().getStringExtra("county_code");
        if (!TextUtils.isEmpty(countyCode)){
            publishTime.setText("同步中...");
            weatherInfoLayout.setVisibility(View.INVISIBLE);
            cityName.setVisibility(View.INVISIBLE);
            queryWeatherCode(countyCode);
        }else {
            showWeather();
        }
    }

    /**
     * 从SharedPreferences文件中读取存数的天气信息,并显示在界面上.
     */
    public void showWeather() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        cityName.setText(preferences.getString("city_name",""));
        publishTime.setText("今天"+preferences.getString("publish_time","")+"发布");
        data.setText(preferences.getString("data",""));
        weatherDespText.setText(preferences.getString("weather",""));
        lowTemperature.setText(preferences.getString("low_temperature",""));
        highTemperature.setText(preferences.getString("high_temperature",""));
        weatherInfoLayout.setVisibility(View.VISIBLE);
        cityName.setVisibility(View.VISIBLE);
    }

    /**
     * 查询县级代号所对应的天气代号
     * @param countyCode 县级代号
     */
    private void queryWeatherCode(String countyCode) {
        String address = "http://www.weather.com.cn/data/list3/city"+countyCode+".xml";
        queryFomServer(address, "countyCode");
    }

    /**
     *  查询天气代号所对应的天气
     * @param weatherCode
     */
    private void queryWeatherInfo(String weatherCode){
        String address = "http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
        queryFomServer(address, "weatherCode");
    }
    private void queryFomServer(final String address, final String type) {
        HttpUtils.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinsh(final String response) {
                if ("countyCode".equals(type)){
                    if (!TextUtils.isEmpty(response)){
                        //从服务器返回的数据中解析天气代码
                        String [] array = response.split("\\|");
                        if (array != null && array.length == 2){
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                }else if ("weatherCode".equals(type)){
                    //处理服务器返回的天气信息
                    Utility.handleWeatherResponse(WeatherActivity.this,response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishTime.setText("同步失败");
                        Toast.makeText(UIUtils.getContext(),"同步失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
