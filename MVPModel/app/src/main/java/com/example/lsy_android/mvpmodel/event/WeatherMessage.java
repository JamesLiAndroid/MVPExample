package com.example.lsy_android.mvpmodel.event;

import com.example.lsy_android.mvpmodel.model.entity.WeatherInfoForecast;

/**
 * Created by jyj-lsy on 9/2/16 in zsl-tech.
 */
public class WeatherMessage {
    private int position;
    private WeatherInfoForecast forecast;

    public WeatherMessage() {

    }

    public WeatherMessage(int position, WeatherInfoForecast forecast) {
        this.position = position;
        this.forecast = forecast;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public WeatherInfoForecast getForecast() {
        return forecast;
    }

    public void setForecast(WeatherInfoForecast forecast) {
        this.forecast = forecast;
    }

    @Override
    public String toString() {
        return "WeatherMessage{" +
                "position=" + position +
                ", forecast=" + forecast +
                '}';
    }
}
