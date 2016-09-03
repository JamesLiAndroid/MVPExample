package com.example.lsy_android.mvpmodel.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lsy-android on 9/3/16 in zsl-tech.
 */
public interface NetworkService {
    /*@Headers("Content-Type:application/json")
    @POST("api/getWeatherForecastByCityname")
    Call<Weather> getWeather(@Body String entity);*/

    @GET("api/getWeatherForecast")
    Call<com.example.lsy_android.mvpmodel.model.entity.Weather> getWeather(@Query("ak") String accessId, @Query("cityname") String cityname);
}
