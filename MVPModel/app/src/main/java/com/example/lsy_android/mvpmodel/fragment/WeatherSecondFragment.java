package com.example.lsy_android.mvpmodel.fragment;

import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lsy_android.mvpmodel.R;
import com.example.lsy_android.mvpmodel.event.WeatherMessage;
import com.example.lsy_android.mvpmodel.model.entity.WeatherInfoForecast;
import com.example.lsy_android.mvpmodel.views.CardAdapter;

import org.greenrobot.eventbus.Subscribe;

/**
 * 第二天的天气预报
 * Created by lsy-android on 9/4/16 in zsl-tech.
 */
public class WeatherSecondFragment extends BaseFragment {
    ImageView ivWeather;
    TextView tvHighTemp;
    TextView tvLowTemp;
    TextView tvWeatherType;
    ImageView ivWind;
    TextView tvWindPower;
    TextView tvWindDirection;

    private CardView mCardView;

    public CardView getCardView() {
        return mCardView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_adapter;
    }

    @Override
    protected void initView() {
        mCardView = findView(R.id.cardView);
        ivWeather = findView(R.id.iv_weather);
        ivWind = findView(R.id.iv_wind);
        tvHighTemp = findView(R.id.tv_high_temp);
        tvLowTemp = findView(R.id.tv_low_temp);
        tvWeatherType = findView(R.id.tv_weather_type);
        tvWindPower = findView(R.id.tv_wind_power);
        tvWindDirection = findView(R.id.tv_wind_direction);
        //tvContentGet = findView(R.id.tv_content);
        mCardView.setMaxCardElevation(mCardView.getCardElevation()
                * CardAdapter.MAX_ELEVATION_FACTOR);
    }

    @Override
    protected void initData() {

    }

    @Subscribe(sticky = true)
    public void onWeatherMsgEarn(WeatherMessage event) {
        Log.d("TAG", "获取的数据为：" + event.toString());
        if (event.getPosition() == 2) {
            // 处理第二天的天气预报
            WeatherInfoForecast forecast = event.getForecast();
            tvHighTemp.setText(forecast.getHightemp());
            tvLowTemp.setText(forecast.getLowtemp());
            tvWeatherType.setText(forecast.getType());
            tvWindPower.setText(forecast.getFengli());
            tvWindDirection.setText(forecast.getFengxiang());
        }
    }
}
