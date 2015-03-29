package com.zhanjiqiang.qweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhanjiqiang.qweather.model.City;
import com.zhanjiqiang.qweather.model.County;
import com.zhanjiqiang.qweather.model.Province;
import com.zhanjiqiang.qweather.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @packageName:com.zhanjiqiang.qweather.db
 * @className:QWeatherDB
 * @author:彳亍
 * @:2015/3/29 0029 22:51
 * @describe:封装数据库常用操作的类
 */
public class QWeatherDB {
    /**
     * 数据库名称
     */
    public static final String DB_NAME = "q_weather";
    /**
     * 数据库版本
     */
    public static final int VERSION = 1;
    private static QWeatherDB mQWeatherDB;
    private SQLiteDatabase mDatabase;
    private QWeatherDB(Context context){
        QWeatherOpenHelper mHelper = new QWeatherOpenHelper(UIUtils.getContext(),DB_NAME,null,VERSION);
        mDatabase = mHelper.getWritableDatabase();
    }

    /**
     * 获得QWeatherDB的实例
     * @param context
     * @return
     */
    public synchronized static QWeatherDB getInstance(Context context){
        if (mQWeatherDB == null){
            mQWeatherDB = new QWeatherDB(UIUtils.getContext());
        }
        return mQWeatherDB;
    }

    /**
     * 将省份实例存储到数据库
     * @param province
     */
    public void saveProvince(Province province){
        if (province != null){
            ContentValues values = new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            mDatabase.insert("Province",null,values);
        }
    }

    /**
     * 从数据库读取全国所有省份的信息
     * @return
     */
    public List<Province> loadProvince(){
        List<Province> provinceList = new ArrayList<Province>();
        Cursor cursor = mDatabase.query("Province",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                Province province = new Province();
                province.setProvinceId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                provinceList.add(province);
            }while (cursor.moveToNext());
        }
        if (cursor != null){
            cursor.close();
        }
        return provinceList;
    }
    /**
     * 将城市实例存储到数据库
     * @param city
     */
    public void saveCity(City city){
        if (city != null){
            ContentValues values = new ContentValues();
            values.put("city_name",city.getCityName());
            values.put("city_code",city.getCityCode());
            mDatabase.insert("City",null,values);
        }
    }

    /**
     * 从省份中获取所有城市的信息
     * @return
     */
    public List<City> loadCity(int provinceId){
        List<City> cityList = new ArrayList<City>();
        Cursor cursor = mDatabase.query("City",null,"province_id = ?",new String[]{String.valueOf(provinceId)},null,null,null);
        if (cursor.moveToFirst()){
            do {
                City city = new City();
                city.setCityId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                cityList.add(city);
            }while (cursor.moveToNext());
        }
        if (cursor != null){
            cursor.close();
        }
        return cityList;
    }

    /**
     * 将县的实例存储到数据库
     * @param county
     */
    public void saveCounty(County county){
        if (county != null){
            ContentValues values = new ContentValues();
            values.put("county_name",county.getCountyName());
            values.put("county_code", county.getCountyCode());
            mDatabase.insert("County",null,values);
        }
    }

    /**
     * 读取数据库中城市下县的信息
     * @param cityId
     * @return
     */
    public List<County> loadCounty(int cityId){
        List<County> countyList = new ArrayList<County>();
        Cursor cursor = mDatabase.query("County",null,"city_id = ?",new String[]{String.valueOf(cityId)},null,null,null);
        if (cursor.moveToFirst()){
            do {
                County county = new County();
                county.setCountyId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                countyList.add(county);
            }while (cursor.moveToNext());
        }
        if (cursor != null){
            cursor.close();
        }
        return countyList;
    }
}
