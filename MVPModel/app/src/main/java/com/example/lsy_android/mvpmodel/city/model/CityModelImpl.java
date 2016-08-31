package com.example.lsy_android.mvpmodel.city.model;


import com.example.lsy_android.mvpmodel.city.bean.SimpleCity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jyj-lsy on 8/31/16 in zsl-tech.
 */
public class CityModelImpl implements CityModel{
    @Override
    public List<SimpleCity> getAllCities() {
        // 构造假数据返回
        List<SimpleCity> cities = new ArrayList<>();
        SimpleCity city = null;
        for (int i = 0; i < 15; i++) {
            city = new SimpleCity();
            city.setCityName("城市。。。。"+i);
            cities.add(city);
        }
        return cities;
    }

    @Override
    public List<SimpleCity> getSearchedCities(String name) {
        // 构造假数据返回
        List<SimpleCity> cities = new ArrayList<>();
        SimpleCity city = null;
        for (int i = 0; i < 15; i++) {
            city = new SimpleCity();
            city.setCityName("城市:"+name+":第。。。。"+i+"个");
            cities.add(city);
        }
        return cities;
    }
}

