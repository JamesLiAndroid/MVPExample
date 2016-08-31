package com.example.lsy_android.mvpmodel.city.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lsy_android.mvpmodel.R;
import com.example.lsy_android.mvpmodel.city.adapter.CityAdapter;
import com.example.lsy_android.mvpmodel.city.bean.SimpleCity;
import com.example.lsy_android.mvpmodel.city.presenter.CityPresenter;
import com.example.lsy_android.mvpmodel.city.presenter.CityPresenterImpl;

import java.util.List;

public class CityActivity extends Activity implements CityView {

    private CityPresenter cityPresenter;

    private EditText etCityName;
    private ProgressBar progressBar;
    private ListView listView;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        initViews();

        cityPresenter = new CityPresenterImpl(this);
    }

    private void initViews() {
        etCityName = (EditText) findViewById(R.id.et_city_name);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listView);
        btnSearch = (Button) findViewById(R.id.btn_submit);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = etCityName.getText().toString().trim();
                if ("".equals(cityName)) {
                    cityPresenter.validateCityData();
                } else {
                    cityPresenter.searchCityData(cityName);
                }
            }
        });
    }

    @Override
    public void setProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void moveProgressDialog() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void readCitiesError() {
        Toast.makeText(CityActivity.this, "读取城市数据失败！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setAllCities(List<SimpleCity> cities) {
        CityAdapter adapter = new CityAdapter(CityActivity.this, cities);
        listView.setAdapter(adapter);
    }
}
