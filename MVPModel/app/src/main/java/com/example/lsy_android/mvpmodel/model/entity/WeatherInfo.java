package com.example.lsy_android.mvpmodel.model.entity;

import java.util.List;

public class WeatherInfo {
    private String publishTime;
    private String citycode;
    private String cityname;
    private List<WeatherInfoForecast> forecast;
    private String currentTemp;

    public String getPublishTime() {
        return this.publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getCitycode() {
        return this.citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getCityname() {
        return this.cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public List<WeatherInfoForecast> getForecast() {
        return this.forecast;
    }

    public void setForecast(List<WeatherInfoForecast> forecast) {
        this.forecast = forecast;
    }

    public String getCurrentTemp() {
        return this.currentTemp;
    }

    public void setCurrentTemp(String currentTemp) {
        this.currentTemp = currentTemp;
    }
}
