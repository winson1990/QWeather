package com.zhanjiqiang.qweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @packageName:com.zhanjiqiang.qweather.db
 * @className:QWeatherOpenHelper
 * @author:彳亍
 * @:2015/3/29 0029 22:18
 * @describe:建表类
 */
public class QWeatherOpenHelper extends SQLiteOpenHelper{
    /**
     * 省份数据库的建表语言
     */
    public static final String CREATE_PROVINCE = "create table Province("
            +"id integer primary key autoincrement, "
            +"province_name text, "
            +"province_code text)";

    /**
     * 城市数据库的建表语言
     */
    public static final String CREATE_CITY = "create table City("
            +"id integer primary key autoincrement, "
            +"city_name text, "
            +"city_code text, "
            +"province_id integer)";
    /**
     *  县数据库的建表语言
     */
    public static final String CREATE_COUNTY = "create table County("
            +"id integer primary key autoincrement, "
            +"county_name text, "
            +"county_code text, "
            +"city_id integer)";

    public QWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建省
        db.execSQL(CREATE_PROVINCE);
        //创建城市
        db.execSQL(CREATE_CITY);
        //创建县
        db.execSQL(CREATE_COUNTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
