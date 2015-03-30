package com.zhanjiqiang.qweather.model;

/**
 * @packageName:com.zhanjiqiang.qweather.model
 * @className:Province
 * @author:彳亍
 * @:2015/3/29 0029 22:33
 * @describe:省的实体类
 */
public class Province {
    private int id;
    private String provinceName;
    private String provinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
