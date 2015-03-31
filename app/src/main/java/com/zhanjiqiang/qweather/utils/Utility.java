package com.zhanjiqiang.qweather.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.zhanjiqiang.qweather.db.QWeatherDB;
import com.zhanjiqiang.qweather.model.City;
import com.zhanjiqiang.qweather.model.County;
import com.zhanjiqiang.qweather.model.Province;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @packageName:com.zhanjiqiang.qweather.utils
 * @className:Utility
 * @author:彳亍
 * @:2015/3/30 0030 00:30
 * @describe:解析和处理数据的类
 */
public class Utility {
    /**
     *  解析和处理服务器返回的省级数据
     * @param weatherDB 数据库
     * @param response 返回的值
     * @return
     */
    public synchronized static boolean handlerProvinceResponse(QWeatherDB weatherDB, String response){
        if (!TextUtils.isEmpty(response)){
            String[] allProvince = response.split(",");
            if (allProvince != null && allProvince.length > 0){
                for (String p : allProvince){
                    String [] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceName(array[1]);
                    province.setProvinceCode(array[0]);
                    weatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级数据
     * @param weatherDB 数据库
     * @param response
     * @return
     */
    public static boolean handlerCityResponse(QWeatherDB weatherDB, String response,int provinceId){
        if (!TextUtils.isEmpty(response)){
            String[] allCity = response.split(",");
            if (allCity != null && allCity.length > 0){
                for (String c : allCity){
                    String [] array = c.split("\\|");
                    City city = new City();
                    city.setCityName(array[1]);
                    city.setCityCode(array[0]);
                    city.setProvinceId(provinceId);
                    weatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的县级数据
     * @param weatherDB 数据库
     * @param response
     * @param cityId
     * @return
     */
    public static boolean handlerCountyResponse(QWeatherDB weatherDB, String response, int cityId){
        if (!TextUtils.isEmpty(response)){
            String[] allCounty = response.split(",");
            if (allCounty != null && allCounty.length > 0){
                for (String c : allCounty){
                    String [] array = c.split("\\|");
                    County county = new County();
                    county.setCountyName(array[1]);
                    county.setCountyCode(array[0]);
                    county.setCityId(cityId);
                    weatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return  false;
    }

   /* *//**
     * 解析服务器返回的JSON数据,并将解析出的数据存储到本地.
     * @param context 上下文
     * @param response JSON数据
     */
    public static void handleWeatherResponse(Context context,String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String publishTime = weatherInfo.getString("ptime");
            String lowTemperature = weatherInfo.getString("temp2");
            String highTemperature = weatherInfo.getString("temp1");
            String weather = weatherInfo.getString("weather");
            saveWeatherInfo(UIUtils.getContext(), cityName, weatherCode, publishTime, lowTemperature, highTemperature, weather);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     *  将服务器返回的所有数据存储到SharedPreferences文件中
     * @param context   上下文
     * @param cityName  返回的城市
     * @param weatherCode  返回的城市代码
     * @param publishTime   返回的发布时间
     * @param lowTemperature    返回的最低温度
     * @param highTemperature   返回的最高温度
     * @param weather   返回的天气状态
     */
    public static void saveWeatherInfo(Context context, String cityName, String weatherCode, String publishTime, String lowTemperature,
                                        String highTemperature, String weather) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(UIUtils.getContext()).edit();
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code",weatherCode);
        editor.putString("publish_time",publishTime);
        editor.putString("low_temperature",lowTemperature);
        editor.putString("high_temperature",highTemperature);
        editor.putString("weather",weather);
        editor.putString("data", sdf.format(new Date()));
        editor.commit();
    }
}
