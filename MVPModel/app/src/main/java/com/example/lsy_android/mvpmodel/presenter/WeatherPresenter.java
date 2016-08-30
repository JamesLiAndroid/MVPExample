package com.example.lsy_android.mvpmodel.presenter;

/**
 *
 * Created by lsy-android on 8/27/16 in zsl-tech.
 */
public interface WeatherPresenter {

    /**
     * 根据城市名称获取天气信息
     * @param cityName
     */
    void getWeather(String cityName);
}
