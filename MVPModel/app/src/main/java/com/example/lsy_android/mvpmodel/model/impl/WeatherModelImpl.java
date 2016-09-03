package com.example.lsy_android.mvpmodel.model.impl;

import android.util.Log;

import com.example.lsy_android.mvpmodel.model.OnWeatherListener;
import com.example.lsy_android.mvpmodel.model.WeatherModel;
import com.example.lsy_android.mvpmodel.model.entity.Weather;
import com.example.lsy_android.mvpmodel.network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lsy-android on 8/27/16 in zsl-tech.
 */
public class WeatherModelImpl implements WeatherModel {

    @Override
    public void loadWeather(String cityName, final OnWeatherListener litener) {

        // 进行网络请求，请求数据
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://service.envicloud.cn:8082/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetworkService service = retrofit.create(NetworkService.class);

        Call<Weather> weatherCall = service.getWeather("BHN5QW5KCM9PZDE0NZIYODM5MDG0MDE=", cityName);
        Log.d("TAG", "开始网络请求！");

        weatherCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Log.i("TAG", "response=" + response.body().toString());
                //将json字符串转换为weather对象
                com.example.lsy_android.mvpmodel.model.entity.Weather weather = response.body();
                //如果weather的resultCode==0证明查询数据成功
                if ("0".equals(weather.getResultCode())) {
                    //调用listener的onSuccess（）方法，通知数据查询成功，更新数据。
                    litener.onSuccess(weather);
                } else {
                    //数据查询失败
                    litener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e("TAG", "onFailure=" + t.getMessage());
            }
        });
    }
}
