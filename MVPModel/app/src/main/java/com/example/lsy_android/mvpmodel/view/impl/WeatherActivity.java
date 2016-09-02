package com.example.lsy_android.mvpmodel.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lsy_android.mvpmodel.R;
import com.example.lsy_android.mvpmodel.adapter.ListItemAdapter;
import com.example.lsy_android.mvpmodel.city.view.CitySelectActivity;
import com.example.lsy_android.mvpmodel.model.entity.Weather;
import com.example.lsy_android.mvpmodel.model.entity.WeatherInfo;
import com.example.lsy_android.mvpmodel.model.entity.WeatherInfoForecast;
import com.example.lsy_android.mvpmodel.presenter.WeatherPresenter;
import com.example.lsy_android.mvpmodel.presenter.impl.WeatherPresenterImpl;
import com.example.lsy_android.mvpmodel.view.WeatherView;

import java.lang.ref.WeakReference;
import java.util.List;

public class WeatherActivity extends AppCompatActivity implements WeatherView, View.OnClickListener {

    public static final int CHOOSE_CITY = 0x123;

    private WeatherPresenter presenter;

    private AutoCompleteTextView actCityName;
    private Button btnSubmit;
    private Button btnChooseCity;
    private Button btnTransCard;
    private ProgressBar progressBar;
    private ListView listView;


    private ListItemAdapter adapter = null;
    private String[] values = new String[9];

    private Handler handler = new UIHandler(WeatherActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

       // EventBus.getDefault().register(this);

        bindView();
        presenter = new WeatherPresenterImpl(handler, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // EventBus.getDefault().unregister(this);
    }

    /**
     * 绑定UI组件的方法
     */
    private void bindView() {
        actCityName = (AutoCompleteTextView) findViewById(R.id.atv_city_name);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnChooseCity = (Button) findViewById(R.id.btn_choose_city);
        btnTransCard = (Button) findViewById(R.id.btn_trans_card);
        listView = (ListView) findViewById(R.id.listView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //初始化为不可见
        progressBar.setVisibility(View.INVISIBLE);
        //设置按钮监听
        btnSubmit.setOnClickListener(this);
        btnChooseCity.setOnClickListener(this);
        btnTransCard.setOnClickListener(this);
        //设置自动填充文本框内容
        String[] citys = getResources().getStringArray(R.array.city_names);
        ArrayAdapter cityNamesAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, citys);
        actCityName.setAdapter(cityNamesAdapter);
        //初始化listView
        adapter = new ListItemAdapter(this, values);
        listView.setAdapter(adapter);
    }


    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        btnSubmit.setEnabled(false);
    }

    @Override
    public void dismissLoading() {
        progressBar.setVisibility(View.GONE);
        btnSubmit.setEnabled(true);
    }

    @Override
    public void showError() {
        Toast.makeText(this, "天气查询失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setWeatherInfo(Weather weather) {

        WeatherInfo weatherInfo = weather.getInfo();
        List<WeatherInfoForecast> forecasts = weatherInfo.getForecast();
        WeatherInfoForecast forecast = forecasts.get(0);
        //{"地点", "发布日期", "预测日期", "当前温度", "最高温度", "最低温度", "风力", "风向", "大气状况"}
        values[0] = weatherInfo.getCityname();
        values[1] = weatherInfo.getPublishTime();
        values[2] = weatherInfo.getCurrentTemp();
        values[3] = forecast.getDate();
        values[4] = forecast.getHightemp();
        values[5] = forecast.getLowtemp();
        values[6] = forecast.getFengli();
        values[7] = forecast.getFengxiang();
        values[8] = forecast.getType();

        //通知数据更新
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                if (!TextUtils.isEmpty(actCityName.getText())) {
                    String cityName = actCityName.getText().toString();
                    showLoading();
                    presenter.getWeather(cityName);
                } else {
                    Toast.makeText(this, "城市名称不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case  R.id.btn_choose_city:
                // 进入选择城市的页面
                Intent intent = new Intent(WeatherActivity.this, CitySelectActivity.class);
                startActivityForResult(intent, CHOOSE_CITY);
                break;
            case R.id.btn_trans_card:
                // 进入切换Card的列表
                Intent intentNew = new Intent(WeatherActivity.this, WeatherActivityCompact.class);
                startActivity(intentNew);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_CITY && resultCode == RESULT_OK) {
            Toast.makeText(WeatherActivity.this, "选择返回的城市为: " + data.getStringExtra("result"),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Handler自定义
     * */
    private static class UIHandler extends Handler {

        private WeakReference<WeatherActivity> activityWeakReference = null;

        public UIHandler(WeatherActivity activity) {
             activityWeakReference = new WeakReference<WeatherActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            WeatherActivity activity = activityWeakReference.get();
            activity.dismissLoading();
            Weather weather = null;
            switch (msg.what) {
                case 0x123:
                    // 数据更新成功，进行刷新
                    weather = (Weather) msg.obj;
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
