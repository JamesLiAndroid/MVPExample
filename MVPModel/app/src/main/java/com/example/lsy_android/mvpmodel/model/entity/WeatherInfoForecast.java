package com.example.lsy_android.mvpmodel.model.entity;

public class WeatherInfoForecast {
    private String date;
    private String week;
    private String fengli;
    private String fengxiang;
    private String lowtemp;
    private String type;
    private String hightemp;

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return this.week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getFengli() {
        return this.fengli;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public String getFengxiang() {
        return this.fengxiang;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public String getLowtemp() {
        return this.lowtemp;
    }

    public void setLowtemp(String lowtemp) {
        this.lowtemp = lowtemp;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHightemp() {
        return this.hightemp;
    }

    public void setHightemp(String hightemp) {
        this.hightemp = hightemp;
    }
}
