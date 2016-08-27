package com.example.lsy_android.mvpmodel.model;

import com.example.lsy_android.mvpmodel.model.entity.Weather;

/**
 * Created by lsy-android on 8/27/16 in zsl-tech.
 */
public interface OnWeatherListener {
    /**
     * 成功时的回调
     * @param weather
     */
    void onSuccess(Weather weather);

    /**
     * 失败时的回调
     */
    void onFailure();
}
