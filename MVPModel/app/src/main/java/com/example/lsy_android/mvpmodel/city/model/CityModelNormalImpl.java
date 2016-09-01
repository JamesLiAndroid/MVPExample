package com.example.lsy_android.mvpmodel.city.model;

import com.example.lsy_android.mvpmodel.application.MyApplication;
import com.example.lsy_android.mvpmodel.city.bean.City;
import com.example.lsy_android.mvpmodel.city.utils.DBUtils;

import java.util.ArrayList;

/**
 * Created by jyj-lsy on 9/1/16 in zsl-tech.
 */
public class CityModelNormalImpl implements CityModelNormal{

    @Override
    public ArrayList<City> getAllCities() {
        return DBUtils.getInstance().getCityList(MyApplication.getInstance());
    }

    @Override
    public ArrayList<City> getSearchedCities(String name) {
        return DBUtils.getInstance().getResultCityList(MyApplication.getInstance(), name);
    }

    @Override
    public City getLocationCity() {
        // TODO:后续需要更改，修改变成从定位获取
        City city = new City("济南","jinan");
        return city;
    }

    @Override
    public ArrayList<City> getHotCityList() {
        // TODO:测试方法
        ArrayList<City> cityHot = new ArrayList<>();
        City city = new City("济南", "2");
        cityHot.add(city);
        city = new City("北京", "2");
        cityHot.add(city);
        city = new City("大连", "2");
        cityHot.add(city);
        city = new City("上海", "2");
        cityHot.add(city);
        city = new City("深圳", "2");
        cityHot.add(city);
        city = new City("淄博", "2");
        cityHot.add(city);
        city = new City("临沂", "2");
        cityHot.add(city);
        city = new City("大庆", "2");
        cityHot.add(city);
        city = new City("青岛", "2");
        cityHot.add(city);
        city = new City("重庆", "2");
        cityHot.add(city);
        return cityHot;
    }

    @Override
    public ArrayList<City> getRecentCityList() {
        return DBUtils.getInstance().recentCityInit(MyApplication.getInstance());
    }
}
