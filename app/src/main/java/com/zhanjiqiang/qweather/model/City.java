package com.zhanjiqiang.qweather.model;

/**
 * @packageName:com.zhanjiqiang.qweather.model
 * @className:City
 * @author:彳亍
 * @:2015/3/29 0029 22:39
 * @describe:城市的实体类
 */
public class City {
    private int id;
    private String cityName;
    private String cityCode;
    private int provinceId;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
