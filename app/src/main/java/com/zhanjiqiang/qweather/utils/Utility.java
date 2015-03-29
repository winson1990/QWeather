package com.zhanjiqiang.qweather.utils;

import android.text.TextUtils;

import com.zhanjiqiang.qweather.db.QWeatherDB;
import com.zhanjiqiang.qweather.model.City;
import com.zhanjiqiang.qweather.model.County;
import com.zhanjiqiang.qweather.model.Province;

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
     * @param weatherDB
     * @param response
     * @return
     */
    public synchronized static boolean handlerProvinceResponse(QWeatherDB weatherDB, String response){
        if (!TextUtils.isEmpty(response)){
            String[] allProvince = response.split(",");
            if (allProvince != null && allProvince.length > 0){
                for (String p : allProvince){
                    String [] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceName(array[0]);
                    province.setProvinceCode(array[1]);
                    weatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级数据
     * @param weatherDB
     * @param response
     * @return
     */
    public synchronized static boolean handlerCityResponse(QWeatherDB weatherDB, String response,int provinceId){
        if (!TextUtils.isEmpty(response)){
            String[] allCity = response.split(",");
            if (allCity != null && allCity.length <0){
                for (String c : allCity){
                    String [] array = c.split("\\|");
                    City city = new City();
                    city.setCityName(array[0]);
                    city.setCityCode(array[1]);
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
     * @param weatherDB
     * @param response
     * @param cityId
     * @return
     */
    public synchronized static boolean handlerCountyResponse(QWeatherDB weatherDB, String response, int cityId){
        if (!TextUtils.isEmpty(response)){
            String[] allCounty = response.split(",");
            if (allCounty != null && allCounty.length > 0){
                for (String c : allCounty){
                    String [] array = c.split("\\|");
                    County county = new County();
                    county.setCountyName(array[0]);
                    county.setCountyCode(array[1]);
                    county.setCityId(cityId);
                    weatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return  false;
    }
}
