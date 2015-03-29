package com.zhanjiqiang.qweather.model;

/**
 * @packageName:com.zhanjiqiang.qweather.model
 * @className:City
 * @author:彳亍
 * @:2015/3/29 0029 22:39
 * @describe:TODO
 */
public class City {
    private int cityId;
    private String cityName;
    private String cityCode;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
