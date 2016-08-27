package com.example.lsy_android.mvpmodel.view;

import com.example.lsy_android.mvpmodel.model.entity.Weather;

/**
 * Created by lsy-android on 8/27/16 in zsl-tech.
 */
public interface WeatherView {

    /**
     * 显示进度条
     */
    void showLoading();

    /**
     * 隐藏进度条
     */
    void dismissLoading();

    /**
     * 显示更新失败的信息
     */
    void showError();

    /**
     * 更新Weather信息
     * @param weather
     */
    void setWeatherInfo(Weather weather);
}
