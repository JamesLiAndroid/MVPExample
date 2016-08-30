package com.example.lsy_android.mvpmodel.presenter.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.lsy_android.mvpmodel.model.OnWeatherListener;
import com.example.lsy_android.mvpmodel.model.WeatherModel;
import com.example.lsy_android.mvpmodel.model.entity.Weather;
import com.example.lsy_android.mvpmodel.model.impl.WeatherModelImpl;
import com.example.lsy_android.mvpmodel.presenter.WeatherPresenter;

/**
 * Created by lsy-android on 8/27/16 in zsl-tech.
 */
public class WeatherPresenterImpl implements OnWeatherListener, WeatherPresenter {

    private Handler handler;
    private Context context;

    private WeatherModel weatherModel = null;
    public WeatherPresenterImpl(Handler handler, Context context) {
        this.handler = handler;
        weatherModel = new WeatherModelImpl();
    }

    @Override
    public void onSuccess(Weather weather) {
        Message msg = handler.obtainMessage();
        msg.obj = weather;
        msg.what = 0x123;
        handler.sendMessage(msg);
    }

    @Override
    public void onFailure() {
        handler.sendEmptyMessage(0x000);
    }

    @Override
    public void getWeather(String cityName) {
        weatherModel.loadWeather(cityName, this);
    }
}
