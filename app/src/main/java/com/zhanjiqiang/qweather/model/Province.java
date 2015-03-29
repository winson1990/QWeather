package com.zhanjiqiang.qweather.model;

/**
 * @packageName:com.zhanjiqiang.qweather.model
 * @className:Province
 * @author:彳亍
 * @:2015/3/29 0029 22:33
 * @describe:城市的实体类
 */
public class Province {
    private int provinceId;
    private String provinceName;
    private String provinceCode;

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
}
