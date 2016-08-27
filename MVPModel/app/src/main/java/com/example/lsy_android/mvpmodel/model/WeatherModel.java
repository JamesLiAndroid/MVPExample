package com.example.lsy_android.mvpmodel.model;

/**
 * Created by lsy-android on 8/27/16 in zsl-tech.
 */
public interface WeatherModel {
    /**
     * 访问环境云API，获取数据
     *
     * @param cityName 要查询城市名称
     * @param litener  得到天气数据后回调接口方法
     */
    void loadWeather(String cityName, OnWeatherListener litener);
}
