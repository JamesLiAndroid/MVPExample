package com.example.lsy_android.mvpmodel.view.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lsy_android.mvpmodel.R;
import com.example.lsy_android.mvpmodel.adapter.CardFragmentPagerAdapter;
import com.example.lsy_android.mvpmodel.city.view.CitySelectActivity;
import com.example.lsy_android.mvpmodel.event.WeatherMessage;
import com.example.lsy_android.mvpmodel.fragment.CardFragment;
import com.example.lsy_android.mvpmodel.fragment.WeatherFifthFragment;
import com.example.lsy_android.mvpmodel.fragment.WeatherForthFragment;
import com.example.lsy_android.mvpmodel.fragment.WeatherSecondFragment;
import com.example.lsy_android.mvpmodel.fragment.WeatherThirdFragment;
import com.example.lsy_android.mvpmodel.model.entity.Weather;
import com.example.lsy_android.mvpmodel.model.entity.WeatherInfo;
import com.example.lsy_android.mvpmodel.model.entity.WeatherInfoForecast;
import com.example.lsy_android.mvpmodel.presenter.WeatherPresenter;
import com.example.lsy_android.mvpmodel.presenter.impl.WeatherPresenterImpl;
import com.example.lsy_android.mvpmodel.util.ACache;
import com.example.lsy_android.mvpmodel.view.WeatherView;
import com.example.lsy_android.mvpmodel.views.ShadowTransformer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class WeatherActivityCompact  extends AppCompatActivity implements WeatherView {

    public static final int CHOOSE_CITY = 0x123;

    private WeatherPresenter presenter;
    private ACache aCache;
    private String city;

    private LinearLayout llMainWeather;
    private ViewPager mViewPager;
    private RelativeLayout rlProgressBar;
    ImageView ivWeather;
    TextView tvHighTemp;
    TextView tvLowTemp;
    TextView tvWeatherType;
    ImageView ivWind;
    TextView tvWindPower;
    TextView tvWindDirection;
    TextView tvCityName;
    Button chooseCity;

    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;

    private WeatherSecondFragment secondFragment = new WeatherSecondFragment();
    private WeatherThirdFragment thirdFragment = new WeatherThirdFragment();
    private WeatherForthFragment forthFragment = new WeatherForthFragment();
    private WeatherFifthFragment fifthFragment = new WeatherFifthFragment();
    private List<CardFragment> fragments = new ArrayList<>();

    private Handler handler = new UIHandler(WeatherActivityCompact.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_);

        llMainWeather = (LinearLayout) findViewById(R.id.ll_main_weather);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        rlProgressBar = (RelativeLayout) findViewById(R.id.rl_pb);

        ivWeather = (ImageView) findViewById(R.id.iv_weather);
        ivWind = (ImageView) findViewById(R.id.iv_wind);
        tvHighTemp = (TextView) findViewById(R.id.tv_high_temp);
        tvLowTemp = (TextView) findViewById(R.id.tv_low_temp);
        tvWeatherType = (TextView) findViewById(R.id.tv_weather_type);
        tvWindPower = (TextView) findViewById(R.id.tv_wind_power);
        tvWindDirection = (TextView) findViewById(R.id.tv_wind_direction);

        tvCityName = (TextView) findViewById(R.id.tv_city_name);
        chooseCity = (Button) findViewById(R.id.btn_choose_city);
        chooseCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 进入选择城市的页面
                Intent intent = new Intent(WeatherActivityCompact.this, CitySelectActivity.class);
                startActivityForResult(intent, CHOOSE_CITY);
            }
        });

        fragments.add(secondFragment);
        fragments.add(thirdFragment);
        fragments.add(forthFragment);
        fragments.add(fifthFragment);

        mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                dpToPixels(2, this), fragments);
        mViewPager.setAdapter(mFragmentCardAdapter);

        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);
        mViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);

        aCache = ACache.get(this);
        city = aCache.getAsString("city");
        if (city != null || !"".equals(city) || !"null".equals(city)) {
            tvCityName.setText("当前城市:" + city);
        } else {
            city = "济南";
            tvCityName.setText("当前城市:" + city);
        }

        presenter = new WeatherPresenterImpl(handler, this);
        presenter.getWeather(city);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
       // EventBus.getDefault().postSticky(new WeatherMessage("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_CITY && resultCode == RESULT_OK) {
            Toast.makeText(WeatherActivityCompact.this, "选择返回的城市为: " + data.getStringExtra("result"),
                    Toast.LENGTH_SHORT).show();
            // 需要重新请求天气信息，调用presenter的方法
            String cityName = data.getStringExtra("result");
            city = cityName;
            tvCityName.setText("当前城市:"+city);
            // 添加到缓存
            ACache.get(this).put("city", city);
            presenter.getWeather(city);
        }
    }

    @Override
    public void showLoading() {
        llMainWeather.setVisibility(View.GONE);
        mViewPager.setVisibility(View.GONE);
        rlProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoading() {
        rlProgressBar.setVisibility(View.GONE);
        llMainWeather.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        Toast.makeText(this, "天气查询失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setWeatherInfo(Weather weather) {
        WeatherInfo weatherInfo = weather.getInfo();
        List<WeatherInfoForecast> forecasts = weatherInfo.getForecast();
        WeatherMessage msg = new WeatherMessage();
        for (int i = 0; i < forecasts.size(); i++) {
            WeatherInfoForecast forecast = forecasts.get(i);
            msg.setForecast(forecast);
            msg.setPosition(i);
            EventBus.getDefault().postSticky(msg);
        }
    }

    @Subscribe(sticky = true)
    public void onWeatherMsgEarn(WeatherMessage event) {
        Log.d("TAG", "获取的数据为：" + event.toString());
        if (event.getPosition() == 0) {
            // 处理第一天的天气预报
            WeatherInfoForecast forecast = event.getForecast();
            tvHighTemp.setText(forecast.getHightemp());
            tvLowTemp.setText(forecast.getLowtemp());
            tvWeatherType.setText(forecast.getType());
            tvWindPower.setText(forecast.getFengli());
            tvWindDirection.setText(forecast.getFengxiang());
        }
    }

    /**
     * Handler自定义
     * */
    private static class UIHandler extends Handler {

        private WeakReference<WeatherActivityCompact> activityWeakReference = null;

        public UIHandler(WeatherActivityCompact activity) {
            activityWeakReference = new WeakReference<WeatherActivityCompact>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            WeatherActivityCompact activity = activityWeakReference.get();
            activity.dismissLoading();
            Weather weather = null;
            switch (msg.what) {
                case 0x123:
                    // 数据更新成功，进行刷新
                    weather = (Weather) msg.obj;
                    Log.d("TAG", "获取的天气信息为："+weather.toString());
                    activity.setWeatherInfo(weather);
                    break;
                case 0x000:
                    // 数据更新失败
                    activity.showError();
                    break;
            }
            super.handleMessage(msg);
        }
    }
}

