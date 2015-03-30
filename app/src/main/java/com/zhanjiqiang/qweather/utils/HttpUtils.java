package com.zhanjiqiang.qweather.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @packageName:com.zhanjiqiang.qweather.utils
 * @className:HttpUtils
 * @author:彳亍
 * @:2015/3/30 0030 00:12
 * @describe:网络连接工具类
 */
public class HttpUtils {
    public static void sendHttpRequest(final String address, final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream stream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine())!=null){
                        response.append(line);
                    }
                    if (listener != null){
                        listener.onFinsh(response.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (listener != null){
                        listener.onError(e);
                    }
                }finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
