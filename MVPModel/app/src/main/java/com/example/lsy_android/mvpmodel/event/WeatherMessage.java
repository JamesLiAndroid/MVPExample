package com.example.lsy_android.mvpmodel.event;

/**
 * Created by jyj-lsy on 9/2/16 in zsl-tech.
 */
public class WeatherMessage {
    private String weatherInfo;

    public WeatherMessage(String weatherInfo) {
        this.weatherInfo = weatherInfo;
    }

    public String getWeatherInfo() {
        return weatherInfo;
    }
}
