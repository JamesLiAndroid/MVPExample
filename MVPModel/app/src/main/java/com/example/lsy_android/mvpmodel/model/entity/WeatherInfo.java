
package com.example.lsy_android.mvpmodel.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WeatherInfo {

    @SerializedName("citycode")
    @Expose
    private String citycode;
    @SerializedName("publishTime")
    @Expose
    private String publishTime;
    @SerializedName("forecast")
    @Expose
    private List<WeatherInfoForecast> forecast = new ArrayList<WeatherInfoForecast>();
    @SerializedName("currentTemp")
    @Expose
    private String currentTemp;
    @SerializedName("cityname")
    @Expose
    private String cityname;

    /**
     * 
     * @return
     *     The citycode
     */
    public String getCitycode() {
        return citycode;
    }

    /**
     * 
     * @param citycode
     *     The citycode
     */
    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    /**
     * 
     * @return
     *     The publishTime
     */
    public String getPublishTime() {
        return publishTime;
    }

    /**
     * 
     * @param publishTime
     *     The publishTime
     */
    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    /**
     * 
     * @return
     *     The forecast
     */
    public List<WeatherInfoForecast> getForecast() {
        return forecast;
    }

    /**
     * 
     * @param forecast
     *     The forecast
     */
    public void setForecast(List<WeatherInfoForecast> forecast) {
        this.forecast = forecast;
    }

    /**
     * 
     * @return
     *     The currentTemp
     */
    public String getCurrentTemp() {
        return currentTemp;
    }

    /**
     * 
     * @param currentTemp
     *     The currentTemp
     */
    public void setCurrentTemp(String currentTemp) {
        this.currentTemp = currentTemp;
    }

    /**
     * 
     * @return
     *     The cityname
     */
    public String getCityname() {
        return cityname;
    }

    /**
     * 
     * @param cityname
     *     The cityname
     */
    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

}
