package com.zhanjiqiang.qweather.model;

/**
 * @packageName:com.zhanjiqiang.qweather.model
 * @className:County
 * @author:彳亍
 * @:2015/3/29 0029 22:41
 * @describe:TODO
 */
public class County {
    private int countyId;
    private String countyName;
    private String countyCode;

    public int getCountyId() {
        return countyId;
    }

    public void setCountyId(int countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }
}
