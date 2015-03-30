package com.zhanjiqiang.qweather.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhanjiqiang.qweather.R;
import com.zhanjiqiang.qweather.db.QWeatherDB;
import com.zhanjiqiang.qweather.model.City;
import com.zhanjiqiang.qweather.model.County;
import com.zhanjiqiang.qweather.model.Province;
import com.zhanjiqiang.qweather.utils.HttpCallbackListener;
import com.zhanjiqiang.qweather.utils.HttpUtils;
import com.zhanjiqiang.qweather.utils.UIUtils;
import com.zhanjiqiang.qweather.utils.Utility;
import java.util.ArrayList;
import java.util.List;

/**
 * @packageName:com.zhanjiqiang.qweather.activity
 * @className:ChooseAreaActivity
 * @author:彳亍
 * @:2015/3/30 0030 01:06
 * @describe: 省县市数据的活动
 */
public class ChooseAreaActivity extends Activity {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;

    private TextView title;
    private ListView content;
    private ProgressDialog dialog;
    private ArrayAdapter<String> adapter;
    private QWeatherDB weatherDB;
    private List<String> dataList = new ArrayList<String>();

    /**
     * 省列表
     */
    private List<Province> provinceList;
    /**
     * 市列表
     */
    private List<City> citysList;
    /**
     * 县列表
     */
    private List<County> countyList;
    /**
     * 选中的省份
     */
    private Province selectProvince;
    /**
     * 选中的城市
     */
    private City selectCity;

    /**
     * 当前选中的
     */
    private int currentLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_area);
        content = (ListView) findViewById(R.id.area_content);
        title = (TextView) findViewById(R.id.area_title);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataList);
        content.setAdapter(adapter);
        weatherDB = QWeatherDB.getInstance(this);
        content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE){
                    selectProvince = provinceList.get(position);
                    queryCity();
                }else if (currentLevel == LEVEL_CITY){
                    selectCity = citysList.get(position);
                    queryCounty();
                }
            }
        });
        queryProvince();
    }

    /**
     * 查询所有的省,优先从数据库查,查询不到再从服务器查
     */
    private void queryProvince(){
        provinceList = weatherDB.loadProvince();
        if (provinceList.size() > 0){

            dataList.clear();
            for (Province province : provinceList){
                dataList.add(province.getProvinceName());
            }
                adapter.notifyDataSetChanged();
                content.setSelection(0);
                title.setText("中国");
                currentLevel = LEVEL_PROVINCE;
        }else {
            queryFromServer(null,"province");
        }
    }
    /**
     * 查询所有的城市,优先从数据库查,查询不到再从服务器查
     */
    private void queryCity(){
        citysList = weatherDB.loadCity(selectProvince.getId());
        if (citysList.size() > 0){
            dataList.clear();
            for (City city : citysList){
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            content.setSelection(0);
            title.setText(selectProvince.getProvinceName());
            currentLevel = LEVEL_CITY;
        }else {
            queryFromServer(selectProvince.getProvinceCode(),"city");
        }
    }
    /**
     * 查询所有的县,优先从数据库查,查询不到再从服务器查
     */
    public void queryCounty(){
        countyList = weatherDB.loadCounty(selectCity.getId());
        if (countyList.size() > 0){
            dataList.clear();
            for (County county : countyList){
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            content.setSelection(0);
            title.setText(selectCity.getCityName());
            currentLevel = LEVEL_COUNTY;
        }else{
            queryFromServer(selectCity.getCityCode(),"county");
        }
    }

    private void queryFromServer(final String code,final String type) {
        String address;
        if (!TextUtils.isEmpty(code)){
            address = "http://www.weather.com.cn/data/list3/city"+code+".xml";
            Log.d("ChooseAreaActivity",address);
        }else{
            address = "http://www.weather.com.cn/data/list3/city.xml";
            Log.d("ChooseAreaActivity",address);
        }
        showProgressDialog();
        HttpUtils.sendHttpRequest(address,new HttpCallbackListener() {
            @Override
            public void onFinsh(String response) {
                boolean result = false;
                if ("province".equals(type)){
                    result = Utility.handlerProvinceResponse(weatherDB,response);
                }else if ("city".equals(type)){
                    result = Utility.handlerCityResponse(weatherDB,response,selectProvince.getId());
                }else if ("county".equals(type)){
                    result = Utility.handlerCountyResponse(weatherDB,response,selectCity.getId());
                }
                if (result){
                    //回到主线程
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)){
                                queryProvince();
                            }else if ("city".equals(type)){
                                queryCity();
                            }else if ("county".equals(type)){
                                queryCounty();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //TODO
                        Toast.makeText(UIUtils.getContext(),"加载失败,请重试!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void closeProgressDialog() {
        if (dialog != null){
            dialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if (dialog == null){
            dialog = new ProgressDialog(this);
            dialog.setMessage("正在加载...");
            dialog.setCanceledOnTouchOutside(false);
        }
        dialog.show();
    }

    @Override
    public void onBackPressed() {
       if (currentLevel == LEVEL_COUNTY){
           queryCity();
       }else if (currentLevel == LEVEL_CITY){
           queryProvince();
       }else {
           finish();
       }
    }
}
