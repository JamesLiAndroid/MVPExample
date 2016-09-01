package com.example.lsy_android.mvpmodel.city.model;

import com.example.lsy_android.mvpmodel.city.bean.City;

import java.util.ArrayList;

/**
 * Created by jyj-lsy on 9/1/16 in zsl-tech.
 */
public interface CityModelNormal {
    /**
     * 获取全部城市
     */
    ArrayList<City> getAllCities();

    /**
     * 搜索城市并返回结果,结果可能是具体的一个城市，
     * 也可能是模糊查询的列表
     * @param name
     */
    ArrayList<City> getSearchedCities(String name);

    /**
     * 获取定位城市
     * @return
     */
    City getLocationCity();

    /**
     * 获取热门城市列表
     * @return
     */
    ArrayList<City> getHotCityList();

    /**
     * 获取最近访问的城市数据
     * @return
     */
    ArrayList<City> getRecentCityList();
}
