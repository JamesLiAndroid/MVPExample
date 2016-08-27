package com.example.lsy_android.mvpmodel.model.entity;

public class Weather {
    private String resultCode;
    private String resultDesc;
    private WeatherInfo info;

    public String getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return this.resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public WeatherInfo getInfo() {
        return this.info;
    }

    public void setInfo(WeatherInfo info) {
        this.info = info;
    }
}
