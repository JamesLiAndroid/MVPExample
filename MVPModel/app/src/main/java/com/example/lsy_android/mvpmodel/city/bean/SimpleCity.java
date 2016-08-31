package com.example.lsy_android.mvpmodel.city.bean;

/**
 * Created by jyj-lsy on 8/31/16 in zsl-tech.
 */
public class SimpleCity {
    private String cityName; // 城市名称 中文
    private String cityNamePin; // 城市名称 汉语拼音

    public SimpleCity() {

    }

    public SimpleCity(String cityName, String cityNamePin) {
        this.cityName = cityName;
        this.cityNamePin = cityNamePin;
    }

    public String getCityNamePin() {
        return cityNamePin;
    }

    public void setCityNamePin(String cityNamePin) {
        this.cityNamePin = cityNamePin;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
