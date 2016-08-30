package com.example.lsy_android.mvpmodel.model;

import com.example.lsy_android.mvpmodel.model.entity.Weather;

/**
 * Created by jyj-lsy on 8/30/16 in zsl-tech.
 */
public interface OnCityListener {
    /**
     * 成功读取城市信息的回调
     * @param weather
     */
    void onCitySuccess(Weather weather);

    /**
     * 读取城市信息失败时的回调
     */
    void onCityFailure();
}
