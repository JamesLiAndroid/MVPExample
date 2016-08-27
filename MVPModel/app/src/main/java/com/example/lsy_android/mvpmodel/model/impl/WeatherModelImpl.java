package com.example.lsy_android.mvpmodel.model.impl;

import com.example.lsy_android.mvpmodel.model.OnWeatherListener;
import com.example.lsy_android.mvpmodel.model.WeatherModel;
import com.example.lsy_android.mvpmodel.model.entity.Weather;
import com.google.gson.Gson;

/**
 * Created by lsy-android on 8/27/16 in zsl-tech.
 */
public class WeatherModelImpl implements WeatherModel {

    private Gson gson = new Gson();

    @Override
    public void loadWeather(String cityName, OnWeatherListener litener) {
        // TODO:从这里进行网络请求，请求数据
        /**
         * 返回虚拟数据
         * {
         "resultCode": "0",
         "resultDesc": "Success",
         "info": {
         "citycode": "101120201",
         "publishTime": "2016-08-17",
         "forecast": [
         {
         "hightemp": "30℃",
         "fengxiang": "北风",
         "lowtemp": "26℃",
         "fengli": "3-4级",
         "date": "2016-08-17",
         "type": "多云",
         "week": "星期三"
         }
         ],
         "currentTemp": "29.3",
         "cityname": "青岛"
         }
         }
         */
        String result = "{\n" +
                "    \"resultCode\": \"0\",\n" +
                "    \"resultDesc\": \"Success\",\n" +
                "    \"info\": {\n" +
                "        \"citycode\": \"101120201\",\n" +
                "        \"publishTime\": \"2016-08-17\",\n" +
                "        \"forecast\": [\n" +
                "            {\n" +
                "                \"hightemp\": \"30℃\",\n" +
                "                \"fengxiang\": \"北风\",\n" +
                "                \"lowtemp\": \"26℃\",\n" +
                "                \"fengli\": \"3-4级\",\n" +
                "                \"date\": \"2016-08-17\",\n" +
                "                \"type\": \"多云\",\n" +
                "                \"week\": \"星期三\"\n" +
                "            }  \n" +
                "      ],\n" +
                "        \"currentTemp\": \"29.3\",\n" +
                "        \"cityname\": \"青岛\"\n" +
                "    }\n" +
                "}";
        //将json字符串转换为weather对象
        Weather weather = gson.fromJson(result, Weather.class);
        //如果weather的resultCode==0证明查询数据成功
        if ("0".equals(weather.getResultCode())) {
            //调用listener的onSuccess（）方法，通知数据查询成功，更新数据。
            litener.onSuccess(weather);
        } else {
            //数据查询失败
            litener.onFailure();
        }
    }
}
